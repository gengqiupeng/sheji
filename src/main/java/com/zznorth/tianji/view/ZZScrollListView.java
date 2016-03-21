package com.zznorth.tianji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * coder by 中资北方 on 2015/12/9.
 */
public class ZZScrollListView extends ListView{

    public ZZScrollListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZZScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2
                , MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
