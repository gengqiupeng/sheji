package com.zznorth.tianji.view;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/** 控制viewPager中SetCurrentItem的滚动速度（取消滚动）
 * coder by 中资北方 on 2015/12/22.
 */
public class ViewPagerScroll extends Scroller{
    private final int mDuration = 0;

    public ViewPagerScroll(Context context) {
        super(context);
    }

    public ViewPagerScroll(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public ViewPagerScroll(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }


    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
