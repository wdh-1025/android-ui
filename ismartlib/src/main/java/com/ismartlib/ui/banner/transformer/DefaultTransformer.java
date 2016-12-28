package com.ismartlib.ui.banner.transformer;

import android.view.View;

/**
 * 动画：默认样式
 */
public class DefaultTransformer extends BaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
    }

    @Override
    public boolean isPagingEnabled() {
        return true;
    }

}
