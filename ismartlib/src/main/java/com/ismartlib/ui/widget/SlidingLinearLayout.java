package com.ismartlib.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃永无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * Created by Administrator on 2016/8/30.
 * Email:924686754@qq.com
 * 平移辅助类
 * 在xml里包上就可以用
 */

public class SlidingLinearLayout extends LinearLayout {
    private Scroller mScroller = null;
    private int duration = 1000;

    public SlidingLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }


    //获取滑动是否结束
    public boolean getIsFinished() {
        return mScroller.isFinished();
    }

    /**
     * 平移持续时间
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * 在X轴滚动
     */
    public void ScrollToX(int StartX, int dx) {
        mScroller.startScroll(StartX, getCurrY(), dx, 0, duration);
        invalidate();
    }

    /**
     * 在Y轴滚动
     */
    public void ScrollToY(int StartY, int dy) {
        mScroller.startScroll(getCurrX(), StartY, 0, dy, duration);
        invalidate();
    }

    //获取X轴的滚动距离
    public int getCurrX() {
        return mScroller.getCurrX();
    }

    //获取Y轴的滚动距离
    public int getCurrY() {
        return mScroller.getCurrY();
    }


}
