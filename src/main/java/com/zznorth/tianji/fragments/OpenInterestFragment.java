package com.zznorth.tianji.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zznorth.tianji.R;
import com.zznorth.tianji.activities.RateRankActivity;
import com.zznorth.tianji.adapter.OpenInterestAdapter;
import com.zznorth.tianji.base.BaseFragment;
import com.zznorth.tianji.bean.ChartDataBean;
import com.zznorth.tianji.bean.IndexBean;
import com.zznorth.tianji.bean.StocksQueryBean;
import com.zznorth.tianji.bean.TotalAssetsBean;
import com.zznorth.tianji.callBack.NetCall;
import com.zznorth.tianji.utils.APIUtils;
import com.zznorth.tianji.utils.EncodeUtils;
import com.zznorth.tianji.utils.JsonParser;
import com.zznorth.tianji.utils.LineChart;
import com.zznorth.tianji.utils.LogUtil;
import com.zznorth.tianji.utils.NetUtil;
import com.zznorth.tianji.utils.UIHelper;
import com.zznorth.tianji.view.SwipeRefreshLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 持仓状况
 * coder by 中资北方 on 2015/12/3.
 */
public class OpenInterestFragment extends BaseFragment {

    private List<List<ChartDataBean>> list;
    private LineChart lineChart;
    @ViewInject(R.id.linear_interest_chart)
    private LinearLayout layout;
    @ViewInject(R.id.listView_interest_state)
    private ListView listViewState;
    @ViewInject(R.id.swipe_layout)
    private SwipeRefreshLayout refreshLayout;
    @ViewInject(R.id.text_chart_total)
    private TextView total;
    @ViewInject(R.id.text_interest_total)
    private TextView totalAssest;
    @ViewInject(R.id.text_chart_shzs)
    private TextView shzs;
    @ViewInject(R.id.text_chart_szzs)
    private TextView szzs;
    @ViewInject(R.id.text_no_data)
    TextView noData;
    private final List<StocksQueryBean> stockList = new ArrayList<>();
    private  static final String TAG = "OpenInterestFragment";
    private OpenInterestAdapter adapter;
    private final Timer timer = new Timer(true);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_interest, container,false);
        x.view().inject(this, view);
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.listview_header_interest, null);
        listViewState.addHeaderView(header);
        adapter = new OpenInterestAdapter(getActivity(), stockList);
        listViewState.setAdapter(adapter);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisiable) {
            return;
        }
        if (isFirstTimeVisiable) {
            initData();
            initChart();
            initTotal();
            initProgress();
            initPoint();
            timer.schedule(task, 10 * 1000, 10 * 1000);
        }
        isFirstTimeVisiable = false;
        timerCanRun = true;
    }

    private boolean timerCanRun = true;
    private final TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if(timerCanRun) {
                initData();
                initPoint();
            }
        }
    };

    @Override
    protected void onInvisible() {
        timerCanRun= false;
    }

    private void initProgress() {
        SwipeRefreshLayout.OnRefreshListener listener;
        refreshLayout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setMode(SwipeRefreshLayout.Mode.DISABLED);
        refreshLayout.setLoadNoFull(false);
        listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        };
        refreshLayout.setOnRefreshListener(listener);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
        listener.onRefresh();
    }

    private boolean DataIsFinish = false;
    private boolean ChartIsFinish = false;

    static class MyHandler extends Handler{
        WeakReference<OpenInterestFragment> openInterestFragment;
        MyHandler(OpenInterestFragment fragment){
            openInterestFragment = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            OpenInterestFragment interestFragment = openInterestFragment.get();
            super.handleMessage(msg);
            if (interestFragment.DataIsFinish&& interestFragment.ChartIsFinish) {
                interestFragment.refreshLayout.setRefreshing(false);
            }
        }
    }

    MyHandler handler = new MyHandler(OpenInterestFragment.this);

    /**
     * 初始化总资产
     */
    private void initTotal() {
        String link = APIUtils.TotalAssetsLink();
        UIHelper.GetDataFromNet(link, new NetCall<String>() {
            @Override
            public void onSuccess(String result) {
                TotalAssetsBean bean = JsonParser.ParserTotalAssets(result);
                total.setText(getString(R.string.text_total_assets,(int)bean.getTotalAssets()));
                totalAssest.setText(getString(R.string.text_total_info,bean.getTotalAssets(),bean.getAvailableCapital(),bean.getMarketValue()));
            }
        });
    }

    private void initPoint(){
        UIHelper.GetDataFromNet(APIUtils.SZZSLink(), new NetCall<String>() {
            @Override
            public void onSuccess(String result) {
               List<IndexBean> list = JsonParser.ParserIndexList(result);
                if(null!=list) {
                    if(null!=list.get(0).getPoint()) {
                        shzs.setText(getString(R.string.text_shzs,EncodeUtils.SplitStrNum(list.get(0).getPoint(), 2)));
                    }
                    if(null!=list.get(1).getPoint()) {
                        szzs.setText(getString(R.string.text_szzs,EncodeUtils.SplitStrNum(list.get(1).getPoint(),2)));
                    }
                }
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        final int page = 1;
        UIHelper.GetDataFromNet(APIUtils.StocksQueryLink(page), new NetCall<String>() {
            @Override
            public void onSuccess(String result) {
                stockList.clear();
                stockList.addAll(JsonParser.ParserStocksQuery(result));
                if(stockList.size()==0){
                    listViewState.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                }else {
                    listViewState.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFinished() {
                super.onFinished();
                DataIsFinish = true;
                handler.sendEmptyMessage(0);
            }
        });
    }

    @Event(value = R.id.text_interest_raterank, type = View.OnClickListener.class)
    private void RateRank(View view) {
        Intent intent = new Intent(getActivity(), RateRankActivity.class);
        startActivity(intent);
    }


    /**
     * 初始化图表
     */
    private void initChart() {
        final String link = APIUtils.ChartsDataSourceLink();
        UIHelper.GetDataFromNet(link, new NetCall<String>() {
            @Override
            public void onSuccess(String result) {
                list = JsonParser.ParserChartDataBean(result);
                lineChart = new LineChart(getActivity(), list);
                View chart = lineChart.getView();
                layout.addView(chart);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                refreshLayout.setRefreshing(false);
                refreshLayout.setLoading(false);
                if (NetUtil.isNetWork()) {
                    UIHelper.ToastUtil("数据加载错误");
                }
                LogUtil.i(TAG, ex.toString());
            }

            @Override
            public void onFinished() {
                super.onFinished();
                ChartIsFinish = true;
                handler.sendEmptyMessage(0);
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        timerCanRun = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isVisiable) {
            timerCanRun = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
