package com.ismartlib.ui.photoview;

import android.content.Context;
import android.content.Intent;

import com.ismartlib.ui.photoview.activity.PhotoViewActivity;
import com.ismartlib.ui.photoview.utils.ImageAttribute;
import com.ismartlib.ui.photoview.utils.ImageDetailFragment;

import java.util.ArrayList;

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
 * Created by  Administrator  on 2017/2/7.
 * Email:924686754@qq.com
 */
public class PhotoViewIntent extends Intent {
    private Context mContext;


    public PhotoViewIntent(Context context) {
        super(context, PhotoViewActivity.class);
        mContext = context;
    }

    /**
     *  设置显示的图片url
     * @param urls
     * @return
     */
    public PhotoViewIntent setImageUrls(ArrayList<String> urls) {
        putStringArrayListExtra("image_urls", urls);
        return this;
    }

    /**
     * 设置图片下标，直接定位到第几张图片，0开始
     * @param imageIndex
     * @return
     */
    public PhotoViewIntent setImageIndex(int imageIndex) {
        putExtra("image_index", imageIndex);
        return this;
    }

    /**
     * 隐藏下标指示器
     */
    public PhotoViewIntent setHiddenIndicator(boolean hidden) {
        putExtra("hidden_indicator", hidden);
        return this;
    }

    /**
     * 获取图片的属性，用于使用者自己使用自己的图片加载库去加载图片
     * @param imageAttribute
     * @return
     */
    public PhotoViewIntent getImageAttribute(ImageAttribute imageAttribute) {
        ImageDetailFragment.setImageAttribute(imageAttribute);
        return this;
    }
}
