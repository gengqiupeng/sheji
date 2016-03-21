package com.zznorth.tianji.activities;

import com.zznorth.tianji.R;
import com.zznorth.tianji.base.BaseSwipeActivity;

/**
 * coder by 中资北方 on 2015/12/2.
 */
public class ViewTest extends BaseSwipeActivity {

    @Override
    public int getLayoutId() {
        return R.layout.received_notification;
    }

    @Override
    public void initView() {
        setSwipeAnyWhere(true);
    }
}

