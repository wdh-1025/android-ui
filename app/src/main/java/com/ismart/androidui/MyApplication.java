package com.ismart.androidui;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;

import com.ismartlib.framework.error.CustomActivityOnCrash;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;

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
    @Override
    public void onCreate() {
        super.onCreate();
        //app奔溃收集
        CustomActivityOnCrash.install(this, true);
        configUniversalImageLoader();
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

}
