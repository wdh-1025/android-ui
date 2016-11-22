package com.ismartlib.ui.base.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.ismartlib.ui.base.adapter.BaseListAdapter;
import com.ismartlib.ui.base.utils.ListSettings;

import java.util.List;

public abstract class BaseListActivity<T> extends BaseLayoutActivity implements BaseListEventHandler.OnListDataListener {

    protected BaseListViewBuilder<T> mViewBuilder;
    protected BaseListEventHandler<T> mEventHandler;
    protected BaseListAdapter<T> mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewBuilder();
        initEventHandler();
        OnViewCreated();
        requestData(true);
    }

    private void initViewBuilder() {
        mAdapter = getListAdapter();
        mViewBuilder = new BaseListViewBuilder<>(this, mContentView, mAdapter, getBaseSettings());
    }

    private void initEventHandler() {
        mEventHandler = new BaseListEventHandler<>(this, mViewBuilder);
        mEventHandler.setOnListDataListener(this);
    }

    @Override
    protected final void setEmptyLayoutClickListener() {
        emptyLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v == emptyLayout && mProgressbar.getVisibility() == View.GONE) {
                    showLoading();
                    requestData(true);
                }
            }
        });
    }

    @Override
    public void onBottomClick() {
        showLoading();
        requestData(true);
    }

    /**
     * 页面网络请求成功返回时，在子Activity中调用此方法
     */
    protected void onDataLoaded(List<T> data) {
        mEventHandler.onDataLoaded(data);
    }

    @Override
    public void onNoNetwork() {
        showNoNetWork();
    }

    @Override
    public void onNoContent() {
        showNoData();
    }

    @Override
    public void onHideEmptyLayout() {
        hideEmptyLayout();
    }

    //如需使用此方法，则需在子fragment中重写此方法
    @Override
    public void onItemClick(View itemView, int position) {
    }

    //如需使用此方法，则需在子fragment中重写此方法
    @Override
    public void onItemLongClick(View itemView, int position) {

    }

    /**
     * 通知子Fragment
     * 父Fragmentview创建好了之后，子Fragment才可以去使用Header等View
     **/
    protected abstract void OnViewCreated();

    /**
     * 请求网络数据
     *
     * @param needRefresh 是否为下拉刷新
     */
    @Override
    public abstract void requestData(boolean needRefresh);


    /**
     * 获取页面适配器
     */
    protected abstract BaseListAdapter<T> getListAdapter();


    /**
     * 获取列表页的基本设置
     * 默认页面设置，如果需要其他设置需要在外面重写此方法
     **/
    protected ListSettings getBaseSettings() {
        ListSettings settings = new ListSettings();
        settings.setLayoutType(ListSettings.LAYOUT_LIST);
        settings.setContentDividerColor(Color.parseColor("#b3b3b3"));
        settings.setContentDividerHeight(1);
        return settings;
    }


}
