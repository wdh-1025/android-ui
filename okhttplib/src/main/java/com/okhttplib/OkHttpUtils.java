package com.okhttplib;

import com.okhttplib.builder.GetBuilder;
import com.okhttplib.builder.HeadBuilder;
import com.okhttplib.builder.OtherRequestBuilder;
import com.okhttplib.builder.PostFileBuilder;
import com.okhttplib.builder.PostFormBuilder;
import com.okhttplib.builder.PostStringBuilder;
import com.okhttplib.cache.BasicCache;
import com.okhttplib.cache.CacheConfig;
import com.okhttplib.callback.Callback;
import com.okhttplib.request.RequestCall;
import com.okhttplib.utils.Platform;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;
    /**
     * 可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程
     */
    private ExecutorService executorService;
    /**
     * 缓存配置
     */
    private CacheConfig mCacheConfig;
    /**
     * 缓存对象
     */
    private BasicCache mBasicCache;

    public OkHttpUtils(OkHttpClient okHttpClient, CacheConfig cacheConfig) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
        mPlatform = Platform.get();
        //线程池
        if (executorService == null) {
            executorService = Executors.newCachedThreadPool();
        }
        //缓存配置
        if (cacheConfig == null) {
            mCacheConfig = new CacheConfig();
        } else {
            mCacheConfig = cacheConfig;
        }
        //初始化缓存对象
        mBasicCache = new BasicCache(mCacheConfig.getCache_path(), mCacheConfig.getDisk_size());
    }


    public static OkHttpUtils initClient(OkHttpClient okHttpClient, CacheConfig cacheConfig) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient, cacheConfig);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null, null);
    }


    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback, final boolean cache) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();
        /**
         * ------------------------------------------------
         * 缓存结束
         * 可缓存线程池，一般用于执行生命周期比较短的异步任务
         * ------------------------------------------------
         */
        if (cache) {//是否需要取出缓存
            if (executorService == null) {
                executorService = Executors.newCachedThreadPool();
            }
            Runnable syncRunnable = new Runnable() {
                @Override
                public void run() {
                    //取出缓存数据
                    ResponseBody responseBody = mBasicCache.getCache(requestCall.getRequest());
                    if (responseBody == null) {
                        return;
                    }
                    try {
                        sendSuccessResultCallback(new ResponseResult(true, responseBody.string()), finalCallback, id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.execute(syncRunnable);
        } else {
            //如果不需要的话看看SD卡有没有相关的缓存文件，删掉他，其实这一步要不要都没关系
        }
        /**
         * ------------------------------------------------
         * 缓存结束
         * ------------------------------------------------
         */
        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(new ResponseResult(false, o), finalCallback, id);
                    //如果需要缓存则缓存到本地
                    if (cache) {
                        mBasicCache.addCache((String) o, response);
                    }
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id) {
        if (callback == null) return;

        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final ResponseResult result, final Callback callback, final int id) {
        if (callback == null) return;
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(result, id);
                callback.onAfter(id);
            }
        });
    }

    public void cancelTag(Object tag) {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}

