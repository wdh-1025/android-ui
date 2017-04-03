package com.okhttplib.cache;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

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
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * Created by root on 2017/4/3  10:36.
 */
public class BasicCache {

    private DiskLruCache diskCache;
    private String fileName = "cache";

    public BasicCache(String filePath, long maxDiskSize) {
        try {
            diskCache = DiskLruCache.open(new File(filePath, fileName), 1, 1, maxDiskSize);
        } catch (IOException exc) {
            diskCache = null;
        }
    }

    /**
     * url转MD5
     *
     * @param url
     * @return
     */
    private String urlToMD5(HttpUrl url) {
        return MD5.getMD5(url.toString());
    }

    /**
     * 添加缓存数据
     *
     * @param response
     */
    public void addCache(String date, Response response) {
        if (diskCache == null) {
            return;
        }
        Buffer buffer = new Buffer();
        try {
            buffer.write(date.getBytes());
            byte[] rawResponse = buffer.readByteArray();
            DiskLruCache.Editor editor = diskCache.edit(urlToMD5(response.request().url()));
            editor.set(0, new String(rawResponse, Charset.defaultCharset()));
            editor.commit();
            buffer.clone();
        } catch (IOException exc) {
            buffer.clone();
        }
    }

    /**
     * 获取缓存数据
     *
     * @param request
     * @return
     */
    public ResponseBody getCache(Request request) {
        if (diskCache == null) {
            return null;
        }
        String cacheKey = urlToMD5(request.url());
        try {
            DiskLruCache.Snapshot cacheSnapshot = diskCache.get(cacheKey);
            if (cacheSnapshot != null) {
                return ResponseBody.create(null, cacheSnapshot.getString(0).getBytes());
            } else {
                return null;
            }
        } catch (IOException exc) {
            return null;
        }
    }

    /**
     * 删除缓存
     *
     * @param request
     */
    public void deleteCache(Request request) {
        if (diskCache == null) {
            return;
        }
        try {
            String cacheKey = urlToMD5(request.url());
            DiskLruCache.Snapshot cacheSnapshot = diskCache.get(cacheKey);
            if (cacheSnapshot != null) {
                diskCache.remove(cacheKey);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}