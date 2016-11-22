package com.ismartlib.ui.base.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.ismartlib.R;


public class ItemDecorationGrid extends RecyclerView.ItemDecoration {
    /**
     * issue2:多于2列时列宽不一致
     */

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;
    private int mDividerWidth;

    private boolean hasHeader = false;
    private boolean hasFooter = true;

    public ItemDecorationGrid(Context context) {
        setDefaultDivider(context);
    }

    private void setDefaultDivider(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.base_line_divider_grid_default);
        mDividerWidth = mDivider.getIntrinsicHeight();
    }

    public void setDivider(int width, int color) {
        this.mDividerWidth = width;
        mDivider.setColorFilter(color, PorterDuff.Mode.DARKEN);
    }

    public void setHasHeader(boolean hasHeader) {
        this.hasHeader = hasHeader;
    }

    public void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    /**
     * 绘制水平分割线
     */
    public void drawHorizontal(Canvas c, RecyclerView parent) {
        if (isNeedDrawBeneath(parent)) {
            int visibleChildCount = parent.getChildCount();
            for (int i = 0; i < visibleChildCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getLeft() - params.leftMargin;
                final int top = child.getBottom() + params.bottomMargin;
                final int right = child.getRight() + params.rightMargin
                        + mDividerWidth;
                if (!isFooter(child, parent)) {
                    final int bottom = top + mDividerWidth;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        }
    }

    /**
     * 绘制垂直分割线
     */
    public void drawVertical(Canvas c, RecyclerView parent) {
        final int visibleChildCount = parent.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDividerWidth;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
            if (isFirstContentColum(child, parent)) {
                final int leftFirst = 0;
                final int topFirst = child.getTop() - params.topMargin;
                final int rightFirst = leftFirst + mDividerWidth;
                final int bottomFirst = child.getBottom() + params.bottomMargin + mDividerWidth;
                mDivider.setBounds(leftFirst, topFirst, rightFirst, bottomFirst);
                mDivider.draw(c);
            }
        }
    }

    /**
     * 给每个item设置偏移量,实际相当于给Item设置margin/padding
     * 如果有足够的空间，将向外扩张，如果没有，就向内挤压
     * TODO 多于2列时列宽不相等Bug
     */
    @Override
    public void getItemOffsets(Rect outRect, View childView, RecyclerView parent, RecyclerView.State state) {
        if (isNeedDrawBeneath(parent)) {
            if (isHeader(childView, parent)) {
                outRect.set(0, 0, 0, mDividerWidth);
            } else if (isFooter(childView, parent)) {
                outRect.set(0, 0, 0, 0);
            } else if (isFirstContentColum(childView, parent)) {
                if (!isFooter(childView, parent)) {
                    outRect.set(mDividerWidth, 0, mDividerWidth / 2, mDividerWidth);
                } else {
                    outRect.set(mDividerWidth, 0, mDividerWidth / 2, 0);
                }
            } else if (isLastContentColum(childView, parent)) {
                if (!isFooter(childView, parent)) {
                    outRect.set(mDividerWidth / 2, 0, mDividerWidth, mDividerWidth);
                } else {
                    outRect.set(mDividerWidth / 2, 0, mDividerWidth, 0);
                }
            } else {
                if (!isFooter(childView, parent)) {
                    outRect.set(mDividerWidth / 2, 0, mDividerWidth / 2, mDividerWidth);
                } else {
                    outRect.set(mDividerWidth / 2, 0, mDividerWidth / 2, 0);
                }
            }
        }

    }

    /**
     * 是否头部
     */
    private boolean isHeader(View childView, RecyclerView parent) {
        final int childViewAdapterPosition = parent.getChildAdapterPosition(childView);
        return hasHeader && childViewAdapterPosition == 0;
    }

    /**
     * 是否底部
     */
    private boolean isFooter(View childView, RecyclerView parent) {
        final int itemCount = parent.getAdapter().getItemCount();
        final int childViewAdapterPosition = parent.getChildAdapterPosition(childView);
        return hasFooter && (childViewAdapterPosition == itemCount - 1);
    }

    /**
     * 是否第一个内容列
     */
    private boolean isFirstContentColum(View childView, RecyclerView parent) {
        final int childViewAdapterPosition = parent.getChildAdapterPosition(childView);
        final int spanCount = getSpanCount(parent);
        if (!isHeader(childView, parent) && !isFooter(childView, parent)) {
            if (hasHeader && childViewAdapterPosition % spanCount == 1) {
                return true;
            } else {
                return !hasHeader && childViewAdapterPosition % spanCount == 0;
            }
        } else {
            return false;
        }
    }

    /**
     * 是否内容列的最后一列
     */
    private boolean isLastContentColum(View childView, RecyclerView parent) {
        final int itemCount = parent.getAdapter().getItemCount();
        final int childViewAdapterPosition = parent.getChildAdapterPosition(childView);
        final int spanCount = getSpanCount(parent);

        if (hasHeader && (childViewAdapterPosition != 0 && (childViewAdapterPosition % spanCount == 0//有头部，满列不含头部
                || childViewAdapterPosition == (itemCount - 2)))) {
            return true;
        } else {
            return !hasHeader && (childViewAdapterPosition != 0 && ((childViewAdapterPosition + 1) % spanCount == 0//无头部，满列
                    || childViewAdapterPosition == (itemCount - 2)));
        }
    }


    private boolean isNeedDrawBeneath(RecyclerView parent) {
        if (hasHeader && hasFooter && parent.getAdapter().getItemCount() == 2) {
            return false;
        } else if (hasHeader && !hasFooter && parent.getAdapter().getItemCount() == 1) {
            return false;
        } else if (!hasHeader && hasFooter && parent.getAdapter().getItemCount() == 1) {
            return false;
        } else {
            return !(!hasHeader && !hasFooter && parent.getAdapter().getItemCount() == 0);
        }
    }

    /**
     * 获取grid的列数
     */
    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

}
