package com.ismartlib.ui.base.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ismartlib.R;


public abstract class BaseFragment extends Fragment {

    protected View fragContentView;
    protected FrameLayout mContentView;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup p = (ViewGroup) fragContentView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return fragContentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        fragContentView = inflater.inflate(R.layout.base_fragment_lib, null);
        initView();
    }


    private void initView() {
        mContentView = (FrameLayout) fragContentView.findViewById(R.id.mContentView);
        emptyLayoutStub = (ViewStub) fragContentView.findViewById(R.id.emptyLayoutStub);
    }

    private void initEmptyLayout() {
        if (!isEmptyLayoutInited) {
            emptyLayout = (FrameLayout) emptyLayoutStub.inflate();
            mNodataText = (TextView) emptyLayout.findViewById(R.id.nodata_text);
            mNodataImage = (ImageView) emptyLayout.findViewById(R.id.nodata_image);
            mProgressbar = (ProgressBar) emptyLayout.findViewById(R.id.mProgressbar);
            isEmptyLayoutInited = true;
            setEmptyLayoutClickListener();
        }
    }

    protected void setEmptyLayoutClickListener() {

    }

    private void initEmptyLayout(int emptyLayoutHeight) {
        initEmptyLayout();
        ViewGroup.LayoutParams lp = emptyLayout.getLayoutParams();
        lp.height = emptyLayoutHeight;
        emptyLayout.setLayoutParams(lp);
    }

    protected void showLoading() {
        initEmptyLayout();
        emptyLayout.setVisibility(View.VISIBLE);
        mNodataImage.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.VISIBLE);
        mNodataText.setVisibility(View.GONE);
    }

    protected void showLoading(int emptyLayoutHeight) {
        initEmptyLayout(emptyLayoutHeight);
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
        mNodataImage.setVisibility(View.VISIBLE);
        mNodataText.setVisibility(View.VISIBLE);
        mNodataText.setText(getString(R.string.nocontent));
        mProgressbar.setVisibility(View.GONE);
    }

    protected void showNoData(int icon, String str) {
        initEmptyLayout();
        emptyLayout.setVisibility(View.VISIBLE);
        mNodataImage.setImageResource(icon);
        mNodataText.setVisibility(View.VISIBLE);
        mNodataText.setText(str);
        mNodataImage.setVisibility(View.VISIBLE);
        mProgressbar.setVisibility(View.GONE);
    }

    protected void hideEmptyLayout() {
        initEmptyLayout();
        emptyLayout.setVisibility(View.GONE);
        mNodataText.setVisibility(View.GONE);
    }

    public void Toast(String str) {
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }


}
