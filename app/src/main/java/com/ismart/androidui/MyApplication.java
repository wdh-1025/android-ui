package com.ismart.androidui;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.ismartlib.framework.error.CustomActivityOnCrash;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.okhttplib.OkHttpUtils;
import com.okhttplib.cache.CacheConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃永无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * Created by  Administrator  on 2016/11/15.
 * Email:924686754@qq.com
 */
public class MyApplication extends Application {
    private static Context mContext;
    public static final String APP_SDCARD_DIR = Environment.getExternalStorageDirectory().getPath()
            + "/ismart-android-ui/";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //app奔溃收集
        CustomActivityOnCrash.install(this, true);
        configUniversalImageLoader();
        //okhttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20000, TimeUnit.MILLISECONDS)
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                .build();
        //配置缓存
        CacheConfig cacheConfig = new CacheConfig();
        cacheConfig.setCache_path(APP_SDCARD_DIR).setDisk_size(5 * (1024 * 1024));
        OkHttpUtils.initClient(okHttpClient, cacheConfig);
    }

    private void configUniversalImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true) //
                .cacheOnDisk(true) //
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration//
                .Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheExtraOptions(480, 800).threadPoolSize(5)
                .threadPriority(4).diskCacheSize(5 * 1024 * 1024)//
//                .writeDebugLogs()
                .diskCache(new UnlimitedDiskCache(new File(Environment.getExternalStorageDirectory().getPath()
                        + "/ismart-lipu/" + "UILCache")))// 自定义缓存路径
                .build();//
        ImageLoader.getInstance().init(config);
    }

    private void initGreenDao() {
    }


    public static Context getContext() {
        return mContext;
    }

}
