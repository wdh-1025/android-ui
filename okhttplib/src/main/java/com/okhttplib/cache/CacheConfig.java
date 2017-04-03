package com.okhttplib.cache;

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
 * Created by  Administrator  on 2017/4/3.
 * Email:924686754@qq.com
 * 缓存策略配置
 */
public class CacheConfig {
    /**
     * 缓存空间大小，默认5MB
     */
    private long disk_size = 5 * (1024 * 1024);
    /**
     * 缓存目录
     */
    private String cache_path;


    public long getDisk_size() {
        return disk_size;
    }

    public CacheConfig setDisk_size(long disk_size) {
        this.disk_size = disk_size;
        return this;
    }


    public String getCache_path() {
        return cache_path;
    }

    public CacheConfig setCache_path(String cache_path) {
        this.cache_path = cache_path;
        return this;
    }
}
