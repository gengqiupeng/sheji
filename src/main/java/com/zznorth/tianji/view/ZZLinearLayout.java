package com.zznorth.tianji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * coder by 中资北方 on 2015/12/7.
 */
public class ZZLinearLayout extends LinearLayout {

    public ZZLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZZLinearLayout(Context context) {
        this(context, null);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
