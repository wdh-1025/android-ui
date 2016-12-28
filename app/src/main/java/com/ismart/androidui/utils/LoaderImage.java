package com.ismart.androidui.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by root on 2015/11/14.
 * 加载图片
 */
public class LoaderImage {
    //加载/图片
    public static void onLoadingImage(String imageUrl, final ImageView imageView, final int onLoadingImageId) {
        ImageLoader.getInstance().displayImage(imageUrl, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                if (0 != onLoadingImageId) {
                    imageView.setImageResource(onLoadingImageId);
                }
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                if (view != null && bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        });
    }


}
