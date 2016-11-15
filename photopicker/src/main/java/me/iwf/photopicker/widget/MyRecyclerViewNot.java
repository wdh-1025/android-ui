package me.iwf.photopicker.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyRecyclerViewNot extends RecyclerView {
    public static boolean isSliding = true;

    public MyRecyclerViewNot(Context context) {
        super(context);
    }

    public MyRecyclerViewNot(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerViewNot(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (isSliding) {
                return super.dispatchTouchEvent(ev);
            } else {
                return true;  //禁止滑动
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
