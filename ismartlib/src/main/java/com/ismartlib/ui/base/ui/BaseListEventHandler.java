package com.ismartlib.ui.base.ui;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ismartlib.ui.base.adapter.BaseListAdapter;
import com.ismartlib.ui.base.utils.OnRcvScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 19659 on 2015/12/21.
 */
public class BaseListEventHandler<T> implements SwipeRefreshLayout.OnRefreshListener, BaseListAdapter.OnItemclickListener {

    //未刷新状态
    public static final int STATE_NONE = 0;
    //正在刷新
    public static final int STATE_REFRESHING = 1;
    //正在加载更多
    public static final int STATE_LOADING_MORE = 2;
    //没有更多数据
    public static final int STATE_NO_MORE_DATA = 3;
    //当前状态
    public static int mState = STATE_NONE;
    //当前加载的数据页数
    public int PAGE_INDEX = 1;
    //列表页数据大小
    public int PAGE_SIZE = 10;

    public Context mContext;
    public BaseListViewBuilder builder;
    //滑动的距离
    private int dx_, dy_;

    public BaseListEventHandler(Context context, BaseListViewBuilder builder) {
        this.mContext = context;
        this.builder = builder;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        PAGE_SIZE = builder.mSettings.getPageSize() == 0 ? PAGE_SIZE : builder.mSettings.getPageSize();
        if (builder.mSwiperefreshlayout != null) {
            builder.mSwiperefreshlayout.setOnRefreshListener(this);
        }
        builder.mAdapter.setOnItemClickListener(this);
        builder.mRecyclerview.addOnScrollListener(mOnScrollListener);
    }

    //滚动到底部监听
    private OnRcvScrollListener mOnScrollListener = new OnRcvScrollListener() {

        @Override
        public void onBottom() {
            super.onBottom();
            if (builder.mSettings.isCanLoadMore()) {
                if (builder.mAdapter == null || builder.mAdapter.getContentItemCount() == 0) {
                    return;
                }
                // 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
                if (mState == STATE_LOADING_MORE || mState == STATE_REFRESHING) {
                    return;
                }
                /**开始加载更多**/
                if (mState == STATE_NONE) {
                    if (builder.mAdapter.getState() == BaseListAdapter.STATE_PULL_LOAD_MORE) {
                        PAGE_INDEX++;
                        mState = STATE_LOADING_MORE;
                        builder.mAdapter.setBottomCount(1);
                        builder.mAdapter.setState(BaseListAdapter.STATE_LOADING);
                        builder.mAdapter.notifyDataSetChanged();
                        if (mDataListener != null) {
                            mDataListener.requestData(false);
                        }
                    }
                }

            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            dx_ = dx;
            dy_ = dy;
            if (mScrollListener != null) {
                mScrollListener.onSroll(recyclerView, dx, dy, builder);
            }
        }

    };

    //swiperefreshlayout刷新监听
    @Override
    public void onRefresh() {
        //正在刷新则不处理
        if (mState == STATE_REFRESHING) {
            return;
        }
        // 设置顶部正在刷新
        setRefreshStateLoading();
        PAGE_INDEX = 1;
        mState = STATE_REFRESHING;
        if (mDataListener != null) {
            mDataListener.requestData(true);
        }
    }

    /**
     * 页面网络请求成功返回时，在子Activity中调用此方法
     */
    public void onDataLoaded(List<T> data) {
        setRefreshSateFinished();
        handleDataResult(data);
    }


    //将网络返回的数据经过处理后，可以传给适配器
    private void handleDataResult(List<T> data) {
        int adapterState;
        if (data == null) {
            if (PAGE_INDEX == 1 && builder.mAdapter.getData().size() == 0) {//第一页数据为null，并且adapter里面没有数据，则为没有网络
                builder.mAdapter.setState(BaseListAdapter.STATE_NO_NETWORK);
                builder.mAdapter.notifyDataSetChanged();
                if (mDataListener != null) {
                    if (!builder.mSettings.isNoDataShowHeader()) {//在子类中设置了没数据时列表不显示头部时
                        mDataListener.onNoNetwork();
                    } else {
                        mDataListener.onHideEmptyLayout();
                    }
                    return;
                }
            }
            data = new ArrayList<>();
        } else {
            if (PAGE_INDEX == 1) {
                builder.mAdapter.clear();
            }
        }
        if ((builder.mAdapter.getContentItemCount() + data.size()) == 0) {//列表为空，则为空内容状态
            adapterState = BaseListAdapter.STATE_NO_CONTENT;
            if (mDataListener != null) {
                if (!builder.mSettings.isNoDataShowHeader()) {
                    mDataListener.onNoContent();
                } else {
                    mDataListener.onHideEmptyLayout();
                }
            }
        } else if ((data.size() < PAGE_SIZE)) {//加载到的数据小于页大小，则为已无更多数据
            if (mDataListener != null && PAGE_INDEX == 1) {
                mDataListener.onHideEmptyLayout();
            }
            adapterState = BaseListAdapter.STATE_LOAD_FINISHED;
        } else {//加载到的数据等于页大小，还有更多数据，显示上拉加载更多
            if (mDataListener != null && PAGE_INDEX == 1) {
                mDataListener.onHideEmptyLayout();
            }
            adapterState = BaseListAdapter.STATE_PULL_LOAD_MORE;
        }
        builder.mAdapter.setState(adapterState);
        try {
            builder.mAdapter.addData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //设置底部正在加载的状态
    private void setRefreshStateLoading() {
        if (builder.mSwiperefreshlayout != null && builder.mSwiperefreshlayout.isEnabled()) {
            builder.mSwiperefreshlayout.setRefreshing(true);
            // 防止多次重复刷新
            builder.mSwiperefreshlayout.setEnabled(false);
        }
    }

    // 设置刷新状态为完成
    private void setRefreshSateFinished() {
        if (builder.mSwiperefreshlayout != null) {
            builder.mSwiperefreshlayout.setRefreshing(false);
            //builder.mSwiperefreshlayout.setEnabled(true);
            builder.mSwiperefreshlayout.setEnabled(builder.mSettings.isCanRefresh());

        }
        mState = STATE_NONE;
    }

    @Override
    public void onClickItem(View itemView, int position) {
        if (mDataListener != null) {
            mDataListener.onItemClick(itemView, position);
        }
    }

    @Override
    public void onLongClickItem(View itemView, int position) {
        if (mDataListener != null) {
            mDataListener.onItemLongClick(itemView, position);
        }
    }

    @Override
    public void onBottomClick() {
        if (mDataListener != null) {
            mDataListener.onBottomClick();
        }
    }

    private OnListDataListener mDataListener;

    public void setOnListDataListener(OnListDataListener mListener) {
        this.mDataListener = mListener;
    }

    public interface OnListDataListener {
        void requestData(boolean isRefreshing);

        void onNoNetwork();

        void onNoContent();

        void onHideEmptyLayout();

        void onItemClick(View itemView, int position);

        void onItemLongClick(View itemView, int position);

        void onBottomClick();
    }

    private OnListScrollListener mScrollListener;

    public void setOnListScrollListener(OnListScrollListener mListener) {
        this.mScrollListener = mListener;
    }

    public interface OnListScrollListener {
        void onSroll(RecyclerView recyclerView, int dx, int dy, BaseListViewBuilder builder);
    }
}


