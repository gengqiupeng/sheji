package com.zznorth.tianji.fragments;

import android.os.Handler;

import com.zznorth.tianji.activities.MainActivity;
import com.zznorth.tianji.base.BaseNewsFragment;

/**
 * coder by 中资北方 on 2015/12/22.
 */
public class InternalReferenceItem extends BaseNewsFragment {

    private static final String TAG = "InternalReferenceItem";
    private boolean isFirstTimeVisiable = true;

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisiable) {
            return;
        }
        if (!isFirstTimeVisiable) {
            RefreshData();
        } else {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    initRefresh();
                    refreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.setRefreshing(true);
                        }
                    });
                    listener.onRefresh();
                }
            };
            Handler handler = new Handler();
            handler.postDelayed(runnable, 200);
        }
        isFirstTimeVisiable = false;
    }

    @Override
    protected void RefreshData() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity.isHaveNews(3)) {
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                }
            });
            listener.onRefresh();
        }
    }
}
