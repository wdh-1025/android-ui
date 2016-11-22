package com.ismartlib.ui.base.ui;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ismartlib.R;
import com.ismartlib.ui.base.adapter.BaseListAdapter;
import com.ismartlib.ui.base.utils.ItemDecorationGrid;
import com.ismartlib.ui.base.utils.ItemDecorationList;
import com.ismartlib.ui.base.utils.ListSettings;


/**
 * Created by 19659 on 2015/12/21.
 */
public class BaseListViewBuilder<T> {

    public Context mContext;
    public FrameLayout mContentView;
    public ListSettings mSettings;
    public SwipeRefreshLayout mSwiperefreshlayout;
    public RecyclerView mRecyclerview;
    //泛型列表适配器
    public BaseListAdapter<T> mAdapter;//列表页头部
    public View mHeaderView;
    public LinearLayoutManager mLayoutManager = null;//布局管理器
    public GridLayoutManager gridLayoutManager = null;//布局管理器
    public StaggeredGridLayoutManager staggerdLayoutManager = null;//布局管理器


    public BaseListViewBuilder(Context context, FrameLayout mContentView, BaseListAdapter<T> mAdapter, ListSettings mSettings) {
        this.mContext = context;
        this.mContentView = mContentView;
        this.mAdapter = mAdapter;
        this.mSettings = mSettings;
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initView() {
        //内容布局
        if (mSettings != null && mSettings.getCustomLayoutId() == 0) {
            mContentView.addView(LayoutInflater.from(mContext).inflate(R.layout.base_content_list_lib, null));
        } else {
            mContentView.addView(LayoutInflater.from(mContext).inflate(mSettings.getCustomLayoutId(), null));
        }
        //刷新组件
        mSwiperefreshlayout = (SwipeRefreshLayout) mContentView.findViewById(R.id.swiperefreshlayout);
        if (mSwiperefreshlayout != null) {
            mSwiperefreshlayout.setColorSchemeResources(R.color.YOUR_APP_THEME);
            if (!mSettings.isCanRefresh()) {
                mSwiperefreshlayout.setEnabled(false);
            }
        }
        //列表控件
        mRecyclerview = (RecyclerView) mContentView.findViewById(R.id.recyclerview);
        if (mAdapter == null) {
            return;
        }
        //设置Adapter必须在添加头部之后
        mRecyclerview.setAdapter(mAdapter);
        if (mSettings.getBottomBgColor() != 0) {
            mAdapter.setBottomBgColor(mSettings.getBottomBgColor());
        }
        if (mSettings.getNoDataBottomHeight() != 0) {
            mAdapter.setNoContBottomHeight(mSettings.getNoDataBottomHeight());
        }
        if (!mSettings.isCanLoadMore()) {
            mAdapter.setBottomCount(0);
        }
        initRecyclerViewType();
    }


    //初始化recyclerivew，包括布局类型，分隔线，头部底部宽度
    public void initRecyclerViewType() {
        switch (mSettings.getLayoutType()) {
            case ListSettings.LAYOUT_LIST:
                mLayoutManager = new LinearLayoutManager(mContext);
                mRecyclerview.setLayoutManager(mLayoutManager);
                ItemDecorationList mListDivider = new ItemDecorationList(mContext);
                if (mAdapter.getHeaderCount() != 0) {
                    mListDivider.setHasHeader(true);
                } else {
                    mListDivider.setHasHeader(false);
                }
                mListDivider.setHasFooter(true);
                mListDivider.setHeaderDivider(mSettings.getHeaderDividerHeight(), mSettings.getHeadDividerColor());
                mListDivider.setContentDivider(mSettings.getContentDividerHeight(), mSettings.getContentDividerColor());
                mRecyclerview.addItemDecoration(mListDivider);
                break;
            case ListSettings.LAYOUT_GRID:
                gridLayoutManager = new GridLayoutManager(mContext, mSettings.getGridSpanCount());
                mRecyclerview.setLayoutManager(gridLayoutManager);
                //设置头部及底部View占据整行空间
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (mAdapter.isHeaderView(position) || mAdapter.isBottomView(position))
                                ? gridLayoutManager.getSpanCount() : 1;
                    }
                });
                ItemDecorationGrid mGridDivider = new ItemDecorationGrid(mContext);
                if (mAdapter.getHeaderCount() != 0) {
                    mGridDivider.setHasHeader(true);
                } else {
                    mGridDivider.setHasHeader(false);
                }
                mGridDivider.setHasFooter(true);
                mGridDivider.setDivider(mSettings.getContentDividerHeight(), mSettings.getContentDividerColor());
                mRecyclerview.addItemDecoration(mGridDivider);
                break;
            case ListSettings.LAYOUT_STAGGERD:
                //暂不支持瀑布流
                break;
        }
    }

}
