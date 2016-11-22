package com.ismartlib.ui.base.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemDecorationList extends RecyclerView.ItemDecoration {
    /**
     * 1，有头部：可自定义头部下面的divider，也可使用默认的
     * 2，无头部：可设置recyclerview的顶部divider，也可使用默认的
     */

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    private Drawable mContentDivider;
    private int contentDividerHeight;

    private Drawable mHeaderDivider;
    private int headerDividerHeight;

    private boolean hasHeader = false;

    private boolean hasFooter = true;

    public ItemDecorationList(Context context) {
        setDefaultDivider(context);
    }

    private void setDefaultDivider(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mContentDivider = a.getDrawable(0);
        contentDividerHeight = mContentDivider.getIntrinsicHeight();

        mHeaderDivider = a.getDrawable(0);
        headerDividerHeight = mHeaderDivider.getIntrinsicHeight();//使用写好的drawable无法修改颜色
        a.recycle();
    }

    /**
     * 如果有头部，设置头部以下的分隔区域高度
     */
    public void setHeaderDivider(int height, int color) {
        this.headerDividerHeight = height;
        mHeaderDivider.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
        //  mHeaderDivider.setColorFilter(color, PorterDuff.Mode.SRC);
    }

    /**
     * 设置内容的分隔线
     */
    public void setContentDivider(int height, int color) {
        this.contentDividerHeight = height;
        mContentDivider.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        //mContentDivider.setColorFilter(color, PorterDuff.Mode.SRC);
    }

    /**
     * 设置是否有头部
     */
    public void setHasHeader(boolean has) {
        this.hasHeader = has;
    }

    /**
     * 设置是否有底部，默认有
     */
    public void setHasFooter(boolean has) {
        this.hasFooter = has;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawTopPadding(c, parent);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State stat) {
        drawVerticalList(c, parent);
    }

    private void drawTopPadding(Canvas c, RecyclerView parent) {
        if (isNeedDrawOver(parent)) {
            final View child = parent.getChildAt(0);
            /**获取当前item在所有item中的实际位置**/
            final int childAdapterPosition = parent.getChildAdapterPosition(child);
            if (childAdapterPosition == 0) {
                final int left = parent.getLeft();
                final int top = child.getTop() - headerDividerHeight;
                final int right = parent.getRight();
                final int bottom = child.getTop();
                mHeaderDivider.setBounds(left, top, right, bottom);
                mHeaderDivider.draw(c);
            }
        }
    }

    private void drawVerticalList(Canvas c, RecyclerView parent) {
        if (isNeedDrawBeneath(parent)) {
            /**所有的Item数量，包含头部底部**/
            final int itemCount = parent.getAdapter().getItemCount();
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();
            /**此处的parent只会返回当前可见的Child数量**/
            final int visibleChildCount = parent.getChildCount();
            for (int i = 0; i < visibleChildCount; i++) {
                final View child = parent.getChildAt(i);
                /**获取当前item在所有item中的实际位置**/
                final int childAdapterPosition = parent.getChildAdapterPosition(child);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                if (hasHeader && childAdapterPosition == 0) {//没有设置头部divider
                    int bottom;
                    bottom = top + headerDividerHeight;
                    mHeaderDivider.setBounds(left, top, right, bottom);
                    mHeaderDivider.draw(c);
                } else if (childAdapterPosition >= 0 && childAdapterPosition < itemCount - 2) {//内容项，不包括最后一项
                    int bottom;
                    bottom = top + contentDividerHeight;
                    mContentDivider.setBounds(left, top, right, bottom);
                    mContentDivider.draw(c);
                }//底部不需要
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (isNeedDrawBeneath(parent)) {
            final int viewPosition = parent.getChildAdapterPosition(view);
            final int itemCount = parent.getAdapter().getItemCount();
            if (viewPosition == 0) {
                if (hasHeader) {
                    outRect.set(0, 0, 0, headerDividerHeight);
                } else {
                    outRect.set(0, headerDividerHeight, 0, contentDividerHeight);
                }
            } else if (viewPosition >= 0 && viewPosition <= itemCount - 3) {//内容项
                outRect.set(0, 0, 0, contentDividerHeight);
            } else if (viewPosition == itemCount - 1) {//底部
                outRect.set(0, 0, 0, 0);
            }
        }
    }

    private boolean isNeedDrawOver(RecyclerView parent) {
        return !hasHeader &&
                (hasFooter && parent.getAdapter().getItemCount() > 1
                        || !hasFooter && parent.getAdapter().getItemCount() > 0);
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

}
