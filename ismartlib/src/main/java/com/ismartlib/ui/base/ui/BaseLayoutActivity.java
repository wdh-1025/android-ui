package com.ismartlib.ui.base.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ismartlib.R;


public abstract class BaseLayoutActivity extends BaseActivity {

    //标题布局
    protected AppBarLayout appbar;
    //标题栏
    protected Toolbar toolbar;
    //****************************************//

    //标题栏
    protected RelativeLayout navigation;
    //标题栏左侧布局
    protected RelativeLayout layout_left;
    //标题栏左侧图标
    protected ImageView toolbar_left_image;
    //页面标题
    protected TextView toolbarTitle;
    //标题栏右侧布局1
    protected RelativeLayout layout_right1;
    //标题栏右侧图标1
    protected ImageView toolbar_right_image1;
    //标题栏右侧布局
    protected RelativeLayout layout_right2;
    //标题栏右侧图标
    protected ImageView toolbar_right_image2;
    //标题栏右侧文字
    protected TextView toolbar_right_text2;

    //内容布局，由子类指定布局
    protected FrameLayout mContentView;
    //空内容的占位布局
    protected ViewStub emptyLayoutStub;
    //空页面时的布局
    protected FrameLayout emptyLayout;
    //空页面时的显示文字描述
    protected TextView mNodataText;
    //空页面时的显示图片
    protected ImageView mNodataImage;
    //进入页面时的加载中进度条
    protected ProgressBar mProgressbar;

    private boolean isEmptyLayoutInited = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_lib);
        setStatusBar(R.id.layout);
        initBaseView();
    }

    protected void setMyContentView(int layoutId) {
        mContentView.addView(LayoutInflater.from(this).inflate(layoutId, null));
    }

    protected void setMyContentView(View layoutView) {
        mContentView.addView(layoutView);
    }

    private void initEmptyLayout() {
        if (!isEmptyLayoutInited) {
            emptyLayout = (FrameLayout) emptyLayoutStub.inflate();
            mNodataImage = (ImageView) emptyLayout.findViewById(R.id.nodata_image);
            mNodataText = (TextView) emptyLayout.findViewById(R.id.nodata_text);
            mProgressbar = (ProgressBar) emptyLayout.findViewById(R.id.mProgressbar);
            isEmptyLayoutInited = true;
            setEmptyLayoutClickListener();
        }
    }

    protected void setEmptyLayoutClickListener() {

    }

    protected void showLoading() {
        initEmptyLayout();
        emptyLayout.setVisibility(View.VISIBLE);
        mNodataImage.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.VISIBLE);
        mNodataText.setVisibility(View.GONE);
    }

    protected void showNoNetWork() {
        initEmptyLayout();
        emptyLayout.setVisibility(View.VISIBLE);
        mNodataImage.setImageResource(R.drawable.default_error_image);
        mNodataImage.setVisibility(View.VISIBLE);
        mNodataText.setVisibility(View.VISIBLE);
        mNodataText.setText(getString(R.string.errorInfo));
        mProgressbar.setVisibility(View.GONE);
    }


    protected void showNoData() {
        initEmptyLayout();
        emptyLayout.setVisibility(View.VISIBLE);
        mNodataImage.setImageResource(R.drawable.base_nocontent_img);//
        mNodataText.setVisibility(View.VISIBLE);
        mNodataText.setText(getString(R.string.nocontent));
        mNodataImage.setVisibility(View.VISIBLE);
        mProgressbar.setVisibility(View.GONE);
    }

    protected void hideEmptyLayout() {
        initEmptyLayout();
        emptyLayout.setVisibility(View.GONE);
        mNodataText.setVisibility(View.GONE);
    }

    private void initBaseView() {
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        navigation = (RelativeLayout) findViewById(R.id.rl_navigation);
        layout_left = (RelativeLayout) findViewById(R.id.layout_left);
        toolbar_left_image = (ImageView) findViewById(R.id.toolbar_left_image);

        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);

        layout_right1 = (RelativeLayout) findViewById(R.id.layout_right1);
        toolbar_right_image1 = (ImageView) findViewById(R.id.toolbar_right_image1);
        layout_right2 = (RelativeLayout) findViewById(R.id.layout_right2);
        toolbar_right_image2 = (ImageView) findViewById(R.id.toolbar_right_image2);
        toolbar_right_text2 = (TextView) findViewById(R.id.toolbar_right_text2);

        mContentView = (FrameLayout) findViewById(R.id.mContentView);
        emptyLayoutStub = (ViewStub) findViewById(R.id.emptyLayoutStub);
    }


    protected void initTitle(int leftIcon, String title, int rightIcon, String rightText) {
        toolbar_left_image.setImageResource(leftIcon);
        toolbarTitle.setText(title);
        if (rightIcon == 0 && rightText.equals("")) {
            return;
        }
        if (rightIcon != 0) {
            toolbar_right_image2.setImageResource(rightIcon);
            toolbar_right_text2.setVisibility(View.GONE);
        } else {
            toolbar_right_image2.setVisibility(View.GONE);
            toolbar_right_text2.setText(rightText);
            toolbar_right_text2.setVisibility(View.VISIBLE);
        }
    }

    public void setTitleRight1(int rightIcon) {
        layout_right1.setVisibility(View.VISIBLE);
        toolbar_right_image1.setImageResource(rightIcon);
    }

    /**
     * 点击重新加载
     *
     * @param reloadCallBack
     */
    protected void setReloadCallBack(final ReloadCallBack reloadCallBack) {
        if (reloadCallBack != null) {
            emptyLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reloadCallBack.Reload();
                }
            });
        }
    }

    /**
     * 点击重新加载回调
     */
    public interface ReloadCallBack {
        void Reload();
    }

}
