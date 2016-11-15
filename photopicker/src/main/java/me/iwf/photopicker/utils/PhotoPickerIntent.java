package me.iwf.photopicker.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import me.iwf.photopicker.PhotoPickerActivity;

/**
 * Created by donglua on 15/7/2.
 */
@SuppressLint("ParcelCreator")
public class PhotoPickerIntent extends Intent {
    private Context mContext;

    private PhotoPickerIntent() {
    }

    private PhotoPickerIntent(Intent o) {
        super(o);
    }

    private PhotoPickerIntent(String action) {
        super(action);
    }

    private PhotoPickerIntent(String action, Uri uri) {
        super(action, uri);
    }

    private PhotoPickerIntent(Context packageContext, Class<?> cls) {
        super(packageContext, cls);
    }

    public PhotoPickerIntent(Context packageContext) {
        super(packageContext, PhotoPickerActivity.class);
        mContext = packageContext;
    }

    public void setPhotoCount(int photoCount) {
        this.putExtra(PhotoPickerActivity.EXTRA_MAX_COUNT, photoCount);
    }

    /**
     * 是否显示拍照按钮
     *
     * @param showCamera
     */
    public void setShowCamera(boolean showCamera) {
        this.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, showCamera);
    }

    /**
     * 是否显示gif图片
     * @param showGif
     */
    public void setShowGif(boolean showGif) {
        this.putExtra(PhotoPickerActivity.EXTRA_SHOW_GIF, showGif);
    }

    /**
     * 回调，返回选择的图片bitmap和图片路径
     *
     * @param context
     */
    public void setImageSelect(PhotoPickerActivity.CallbackHead context) {
       // this.putExtra(PhotoPickerActivity.EXTRA_MAX_COUNT, 1);
        PhotoPickerActivity.setImageCallback(context);
    }

    /**
     * 是否需要裁剪
     *
     * @param b
     */
    public void setTailoring(boolean b) {
        this.putExtra(PhotoPickerActivity.TAILORING, b);
    }
}
