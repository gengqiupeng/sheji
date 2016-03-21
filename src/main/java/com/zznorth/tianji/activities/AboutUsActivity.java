package com.zznorth.tianji.activities;

import android.view.View;

import com.zznorth.tianji.R;
import com.zznorth.tianji.base.BaseSwipeActivity;

import org.xutils.view.annotation.Event;

/**
 * coder by 中资北方 on 2016/1/5.
 */
public class AboutUsActivity extends BaseSwipeActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initView() {
        setSwipeAnyWhere(true);
    }

    @Event(value = R.id.image_back, type = View.OnClickListener.class)
    private void finish(View view) {
        finish();
    }

    @Event(value = R.id.text_top_title, type = View.OnClickListener.class)
    private void finish2(View view) {
        finish();
    }
}
