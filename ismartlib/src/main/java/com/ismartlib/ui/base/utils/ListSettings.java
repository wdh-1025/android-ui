package com.ismartlib.ui.base.utils;

/**
 * Created by 19659 on 2015/8/29.
 * 基本列表页的配置
 */
public class ListSettings {
    //垂直方向的列表
    public static final int LAYOUT_LIST = 0;
    //垂直方向的表格布局
    public static final int LAYOUT_GRID = 1;
    //垂直方向的瀑布流(暂不支持瀑布流)
    public static final int LAYOUT_STAGGERD = 2;
    // 分页数据页大小
    protected int pageSize = 10;
    // 自定义内容布局Id
    private int customLayoutId;
    //列表类型
    private int layoutType;
    //grid列数
    private int gridSpanCount;
    //头部分隔线的高度
    private int headerDividerHeight;
    //头部分隔线的颜色
    private int headDividerColor;
    //内容分隔线的高度
    private int contentDividerHeight;
    // 内容分隔线的颜色
    private int contentDividerColor;
    //底部背景色
    private int bottomBgColor;
    //是否可以刷新
    private boolean canRefresh = true;
    //是否可以加载更多
    private boolean canLoadMore = true;
    //无内容背景颜色
    private int rlNodataMaskBackColor = 0;

    //没网络、空内容时，“遮罩”是否覆盖整个数据列表，即连头部也不显示
    private boolean isNoDataShowHeader = false;
    //设置没网络、空内容，并且在底部item显示图片时，底部的高度，当此高度大于0时
    private int noDataBottomHeight = 0;


    public int getRlNodataMaskBackColor() {
        return rlNodataMaskBackColor;
    }

    public void setRlNodataMaskBackColor(int rlNodataMaskBackColor) {
        this.rlNodataMaskBackColor = rlNodataMaskBackColor;
    }

    public int getNoDataBottomHeight() {
        return noDataBottomHeight;
    }

    public void setNoDataBottomHeight(int noDataBottomHeight) {
        this.noDataBottomHeight = noDataBottomHeight;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCustomLayoutId() {
        return customLayoutId;
    }

    public void setCustomLayoutId(int customLayoutId) {
        this.customLayoutId = customLayoutId;
    }


    public int getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(int listType) {
        this.layoutType = listType;
    }

    public int getGridSpanCount() {
        return gridSpanCount;
    }

    public void setGridSpanCount(int gridSpanCount) {
        this.gridSpanCount = gridSpanCount;
    }

    public int getHeaderDividerHeight() {
        return headerDividerHeight;
    }

    public void setHeaderDividerHeight(int headerDividerHeight) {
        this.headerDividerHeight = headerDividerHeight;
    }

    public int getHeadDividerColor() {
        return headDividerColor;
    }

    public void setHeadDividerColor(int headDividerColor) {
        this.headDividerColor = headDividerColor;
    }

    public int getContentDividerHeight() {
        return contentDividerHeight;
    }

    public void setContentDividerHeight(int contentDividerHeight) {
        this.contentDividerHeight = contentDividerHeight;
    }

    public int getContentDividerColor() {
        return contentDividerColor;
    }

    public void setContentDividerColor(int contentDividerColor) {
        this.contentDividerColor = contentDividerColor;
    }

    public int getBottomBgColor() {
        return bottomBgColor;
    }

    public void setBottomBgColor(int bottomBgColor) {
        this.bottomBgColor = bottomBgColor;
    }

    public boolean isCanRefresh() {
        return canRefresh;
    }

    public void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }

    public boolean isCanLoadMore() {
        return canLoadMore;
    }

    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }

    public boolean isNoDataShowHeader() {
        return isNoDataShowHeader;
    }

    public void setIsNoDataShowHeader(boolean isNoDataShowHeader) {
        this.isNoDataShowHeader = isNoDataShowHeader;
    }
}
