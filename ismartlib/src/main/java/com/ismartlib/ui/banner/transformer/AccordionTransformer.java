package com.ismartlib.ui.banner.transformer;

import android.view.View;

/**
 * 动画：卡片式抽离
 */
public class AccordionTransformer extends BaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        view.setPivotX(position < 0 ? 0 : view.getWidth());
        view.setScaleX(position < 0 ? 1f + position : 1f - position);
    }

}
