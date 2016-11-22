package com.ismartlib.ui.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ismartlib.R;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    /**************************************************/
    public static final int STATE_PULL_LOAD_MORE = 1;//上拉加载更多
    public static final int STATE_LOADING = 2;//加载中
    public static final int STATE_LOAD_FINISHED = 3;//已加载全部
    public static final int STATE_NO_CONTENT = 4;//暂无数据
    public static final int STATE_NO_NETWORK = 5;//没有网络
    protected int state = STATE_PULL_LOAD_MORE;

    protected String _pullLoadMoreText;//上拉加载更多
    protected String _loadingText;//正在加载
    protected String _loadFinishedText;//已加载全部
    protected String _noDateText;//暂无内容

    protected ArrayList<T> mDatas = new ArrayList<>();

    /**************************************************/

    //item类型
    public enum ITEM_TYPE {
        ITEM_TYPE_HEADER,
        ITEM_TYPE_BOTTOM,
        ITEM_TYPE_CONTENT
    }

    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    protected int mHeaderCount;//头部View个数
    protected int mBottomCount = 1;//默认底部View个数为1

    protected int mBottomBgColor;
    protected int noContBottomHeight = 0;

    //内容布局id
    private int mContentLayoutResId;
    //头部内容id
    private int mHeadLayoutResId;

    /**
     * @param context
     * @param contentLayoutResId 内容item
     * @param headLayoutResId    头部item
     */
    public BaseListAdapter(Context context, int contentLayoutResId, int headLayoutResId) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        _pullLoadMoreText = context.getResources().getString(R.string.pull_load_more);
        _loadingText = context.getResources().getString(R.string.loading);
        _loadFinishedText = context.getResources().getString(R.string.loading_no_more);
        _noDateText = context.getResources().getString(R.string.error_view_no_data);
        this.mContentLayoutResId = contentLayoutResId;
        this.mHeadLayoutResId = headLayoutResId;
        if (mHeadLayoutResId != 0) {
            mHeaderCount = 1;
        }
    }

    /**
     * @param context
     * @param contentLayoutResId 内容item
     */
    public BaseListAdapter(Context context, int contentLayoutResId) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        _pullLoadMoreText = context.getResources().getString(R.string.pull_load_more);
        _loadingText = context.getResources().getString(R.string.loading);
        _loadFinishedText = context.getResources().getString(R.string.loading_no_more);
        _noDateText = context.getResources().getString(R.string.error_view_no_data);
        this.mContentLayoutResId = contentLayoutResId;
        this.mHeadLayoutResId = 0;
        if (mHeadLayoutResId != 0) {
            mHeaderCount = 1;
        }
    }

    //获取中间内容个数
    public int getContentItemCount() {
        return mDatas.size();
    }

    public void setData(ArrayList<T> data) {
        mDatas = data;
        notifyDataSetChanged();
    }

    public ArrayList<T> getData() {
        return mDatas == null ? (mDatas = new ArrayList<T>()) : mDatas;
    }

    public void addData(List<T> data) {
        if (mDatas != null && data != null && !data.isEmpty()) {
            mDatas.addAll(data);
        }
        notifyDataSetChanged();
    }

    //往特定位置，添加一个元素
    public void add(int position, T data) {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.add(position, data);
        notifyDataSetChanged();
    }

    public void removeItem(int obj) {
        mDatas.remove(obj);
        notifyDataSetChanged();
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()) {
            return onCreateHeadAndContentView(parent, viewType, mHeadLayoutResId);
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_BOTTOM.ordinal()) {
            return onCreateBottomView(parent);
        } else /*(viewType == ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal()*/ {
            return onCreateHeadAndContentView(parent, viewType, mContentLayoutResId);
        }
    }

    /**
     * 数据绑定
     *
     * @param holder   之前所创建好的View
     * @param position item的position
     */
    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        if (holder instanceof BottomViewHolder) {
            setBottomView((BottomViewHolder) holder);
        } else {
            onBindViewData(holder, position - mHeaderCount);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount) {//头部View
            return ITEM_TYPE.ITEM_TYPE_HEADER.ordinal();
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {//底部View
            return ITEM_TYPE.ITEM_TYPE_BOTTOM.ordinal();
        } else {
            return getMultiTypeContentViewType(position);
        }
    }

    /**
     * 多布局的内容Item
     *
     * @param position
     * @return
     */
    public int getMultiTypeContentViewType(int position) {
        return ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal();
    }

    protected final BaseViewHolder onCreateBottomView(ViewGroup parent) {
        BottomViewHolder holder = new BottomViewHolder(mContext, mLayoutInflater.inflate(R.layout.base_footer_refreshable_lib, parent, false),
                noContBottomHeight, mBottomBgColor);
        holder.setListBottomClickListener(new BottomViewHolder.OnListBottomClickListener() {
            @Override
            public void onBottomClick() {
                if (mItemCLickListener != null) {
                    mItemCLickListener.onBottomClick();
                }
            }
        });
        return holder;
    }

    /**
     * 设置底部提示信息
     */
    protected void setBottomView(BottomViewHolder bottomViewHolder) {
        switch (getState()) {
            case STATE_PULL_LOAD_MORE:
                bottomViewHolder.mProgress.setVisibility(View.GONE);
                bottomViewHolder.mTextView.setVisibility(View.VISIBLE);
                bottomViewHolder.mTextView.setText(_pullLoadMoreText);
                bottomViewHolder.relativeLayout.setVisibility(View.GONE);
                break;
            case STATE_LOADING:
                bottomViewHolder.mProgress.setVisibility(View.VISIBLE);
                bottomViewHolder.mTextView.setVisibility(View.VISIBLE);
                bottomViewHolder.mTextView.setText(_loadingText);
                bottomViewHolder.relativeLayout.setVisibility(View.GONE);
                break;
            case STATE_LOAD_FINISHED:
                bottomViewHolder.mProgress.setVisibility(View.GONE);
                bottomViewHolder.mTextView.setVisibility(View.VISIBLE);
                bottomViewHolder.mTextView.setText(_loadFinishedText);
                bottomViewHolder.relativeLayout.setVisibility(View.GONE);
                break;
            case STATE_NO_CONTENT:
                bottomViewHolder.mProgress.setVisibility(View.GONE);
                if (noContBottomHeight != 0) {
                    bottomViewHolder.mTextView.setVisibility(View.GONE);
                    bottomViewHolder.relativeLayout.setVisibility(View.VISIBLE);
                    bottomViewHolder.mBigImageView.setImageResource(R.drawable.base_nocontent_img);
                } else {
                    bottomViewHolder.mTextView.setVisibility(View.VISIBLE);
                    bottomViewHolder.mTextView.setText(_noDateText);
                    bottomViewHolder.relativeLayout.setVisibility(View.GONE);
                }
                break;
            case STATE_NO_NETWORK:
                bottomViewHolder.mProgress.setVisibility(View.GONE);
                if (noContBottomHeight != 0) {
                    bottomViewHolder.mTextView.setVisibility(View.GONE);
                    bottomViewHolder.relativeLayout.setVisibility(View.VISIBLE);
                    bottomViewHolder.mBigImageView.setImageResource(R.drawable.default_error_image);
                } else {
                    bottomViewHolder.mTextView.setVisibility(View.VISIBLE);
                    bottomViewHolder.mTextView.setText(_noDateText);
                    bottomViewHolder.relativeLayout.setVisibility(View.GONE);
                }
                break;
            default:
                bottomViewHolder.mProgress.setVisibility(View.GONE);
                bottomViewHolder.mTextView.setText(_noDateText);
                bottomViewHolder.relativeLayout.setVisibility(View.GONE);
                break;
        }
    }

    public void setHeaderCount(int headerCount) {
        this.mHeaderCount = headerCount;
    }

    public int getHeaderCount() {
        return this.mHeaderCount;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public int getBottomBgColor() {
        return mBottomBgColor;
    }

    public void setBottomBgColor(int bottomBgColor) {
        this.mBottomBgColor = bottomBgColor;
    }

    public int getNoContBottomHeight() {
        return noContBottomHeight;
    }

    public void setNoContBottomHeight(int noContBottomHeight) {
        this.noContBottomHeight = noContBottomHeight;
    }

    public void setBottomCount(int bottomCount) {
        this.mBottomCount = bottomCount;
    }

    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    public boolean isBottomView(int position) {
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }

    /**
     * 滑动到底部的监听
     **/
    public interface OnBottomListener {
        void onBottom();
    }

    public interface OnItemclickListener {
        void onClickItem(View view, int position);

        void onLongClickItem(View view, int position);

        void onBottomClick();
    }

    protected OnItemclickListener mItemCLickListener;

    public void setOnItemClickListener(OnItemclickListener listener) {
        this.mItemCLickListener = listener;
    }


    public BaseViewHolder onCreateHeadAndContentView(ViewGroup parent, int viewType, int layoutResId) {
        View item = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        return new BaseViewHolder(mContext, item);
    }


    public void onBindViewData(BaseViewHolder holder, final int position) {
        if (position < 0) {
            onBindHeadViewData(holder, position + mHeaderCount);
        } else {
            onBindContentViewData(holder, mDatas.get(position), position);
        }
    }

    protected abstract void onBindContentViewData(BaseViewHolder helper, T item, int position);

    protected abstract void onBindHeadViewData(BaseViewHolder helper, int position);
}
