package com.zznorth.tianji.activities;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zznorth.tianji.R;
import com.zznorth.tianji.adapter.RateRankAdapter;
import com.zznorth.tianji.base.BaseSwipeActivity;
import com.zznorth.tianji.bean.RateRankBean;
import com.zznorth.tianji.callBack.NetCall;
import com.zznorth.tianji.utils.APIUtils;
import com.zznorth.tianji.utils.JsonParser;
import com.zznorth.tianji.utils.LogUtil;
import com.zznorth.tianji.utils.NetUtil;
import com.zznorth.tianji.utils.UIHelper;
import com.zznorth.tianji.view.SwipeRefreshLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/** 收益排行榜
 * coder by 中资北方 on 2015/12/10.
 */
public class RateRankActivity extends BaseSwipeActivity {
    private List<RateRankBean> list;
    @ViewInject(R.id.listView_rate_rank)
    private ListView listView;
    @ViewInject(R.id.text_top_title)
    private TextView title;
    @ViewInject(R.id.refresh_layout_raterank)
    private SwipeRefreshLayout refreshLayout;

    private RateRankAdapter adapter;
    private SwipeRefreshLayout.OnRefreshListener listener;

    @Override
    public int getLayoutId() {
        return R.layout.activity_rate_rank;
    }

    @Override
    public void initView() {
        setSwipeAnyWhere(true);
        title.setText("持仓状况");
        String[] colors = new String[]{"#e70012", "#eb6100", "#f19801", "#ffba00"};
        list = new ArrayList<>();
        adapter = new RateRankAdapter(this, list, colors);
        listView.setAdapter(adapter);
        //initData();
        initRefresh();
        initListView();
        initLoading();
    }

    private void initRefresh() {
        refreshLayout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setMode(SwipeRefreshLayout.Mode.PULL_FROM_START);
        refreshLayout.setLoadNoFull(false);
        listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        };
        refreshLayout.setOnRefreshListener(listener);
    }

    private void initData() {
        UIHelper.GetDataFromNet(APIUtils.StockRateRankLink(), new NetCall<String>() {
            @Override
            public void onSuccess(String result) {
                list.clear();
                list.addAll(JsonParser.ParserRateRankList(result));
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                refreshLayout.setRefreshing(false);
                if (NetUtil.isNetWork()) {
                    UIHelper.ToastUtil("糟糕，加载失败，向下滑动重新加载");
                }
                LogUtil.i(TAG, ex.toString());
            }
        });
    }

    @Event(value = R.id.image_back, type = View.OnClickListener.class)
    private void back(View view) {
        finish();
    }

    @Event(value = R.id.text_top_title, type = View.OnClickListener.class)
    private void back2(View view) {
        finish();
    }

    @Event(value = R.id.image_title_user, type = View.OnClickListener.class)
    private void user(View view) {
        Intent intent = new Intent(RateRankActivity.this, UserCenterActivity.class);
        startActivity(intent);
    }

    private void initLoading() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
        listener.onRefresh();
    }

    private void initListView(){
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

}
