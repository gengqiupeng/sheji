package com.zznorth.tianji.fragments;

import com.zznorth.tianji.activities.MainActivity;
import com.zznorth.tianji.base.BaseNewsFragment;

/**
 * coder by 中资北方 on 2015/12/3.
 */
public class HotPointFragment extends BaseNewsFragment {

    private static final String TAG = "Hot";

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
            android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(runnable, 500);
        }
        isFirstTimeVisiable = false;

    }

    @Override
    protected void RefreshData() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity.isHaveNews(2)) {
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                }
            });
            listener.onRefresh();
            mainActivity.setRedPoint(false, 2);
        }
    }
}
