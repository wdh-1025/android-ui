package com.ismart.androidui.test;

import android.os.Bundle;

import com.ismart.androidui.R;
import com.ismartlib.framework.swipebacklayout.app.SwipeBackActivity;
import com.ismartlib.ui.widget.SlidingLinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewActivity extends SwipeBackActivity {

    @Bind(R.id.layout_sliding)
    SlidingLinearLayout layoutSliding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);
        layoutSliding.setDuration(1000);
    }

    @OnClick(R.id.btn_top)
    public void TOP() {
        layoutSliding.ScrollToY(layoutSliding.getCurrY(), 200);
    }

    @OnClick(R.id.btn_bottom)
    public void BOTTOM() {
        layoutSliding.ScrollToY(layoutSliding.getCurrY(), -200);
    }

    @OnClick(R.id.btn_left)
    public void LEFT() {
        layoutSliding.ScrollToX(layoutSliding.getCurrX(), 200);
    }

    @OnClick(R.id.btn_right)
    public void RIGHT() {
        layoutSliding.ScrollToX(layoutSliding.getCurrX(), -200);
    }


}
