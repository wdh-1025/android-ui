package com.ismartlib.ui.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private View convertView;
    private Context mContext;

    public BaseViewHolder(Context context, View view) {
        super(view);
        this.mContext = context;
        this.views = new SparseArray<View>();
        convertView = view;
    }

    /**
     * 检索查找view
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param value
     * @return
     */
    public BaseViewHolder setText(int viewId, CharSequence value) {
        TextView view = retrieveView(viewId);
        view.setText(value);
        return this;
    }

    /**
     * 设置文本颜色
     */
    public BaseViewHolder setTextColor(int viewId, int Color) {
        TextView view = retrieveView(viewId);
        view.setTextColor(Color);
        return this;
    }

    /**
     * 设置图片
     *
     * @param viewId
     * @param resId
     * @return
     */
    public BaseViewHolder setImageResource(int viewId, int resId) {
        ImageView imageView = retrieveView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    /**
     * 设置显示或隐藏
     *
     * @param viewId
     * @param visible
     * @return
     */
    public BaseViewHolder setVisible(int viewId, boolean visible) {
        View view = retrieveView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public BaseViewHolder linkify(int viewId) {
        TextView view = retrieveView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    /**
     * 动态添加view
     *
     * @param viewId
     * @param layoutResId
     * @param childListSize
     * @return
     */
    public LinearLayout addView(int viewId, int layoutResId, int childListSize) {
        LinearLayout linearLayout = retrieveView(viewId);
        int addviewcount = linearLayout.getChildCount();
        if (addviewcount < childListSize) {
            for (int i = 0; i < childListSize - addviewcount; i++) {
                linearLayout.addView(LayoutInflater.from(mContext).inflate(layoutResId, null));
            }
        } else if (addviewcount > childListSize) {
            for (int j = 0; j < addviewcount - childListSize; j++) {
                linearLayout.removeViewAt(addviewcount - 1 - j);
            }
        }
        return linearLayout;
    }

    /**
     * 获取view
     *
     * @param viewId
     * @return
     */
    public View getView(int viewId) {
        View view = retrieveView(viewId);
        return view;
    }


}



