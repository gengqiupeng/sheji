package com.zznorth.tianji.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zznorth.tianji.R;
import com.zznorth.tianji.adapter.NotificationAdapter;
import com.zznorth.tianji.base.BaseFragment;
import com.zznorth.tianji.bean.ContextNewsListBean;
import com.zznorth.tianji.callBack.NetCall;
import com.zznorth.tianji.utils.APIUtils;
import com.zznorth.tianji.utils.Config;
import com.zznorth.tianji.utils.JsonParser;
import com.zznorth.tianji.utils.LogUtil;
import com.zznorth.tianji.utils.NetUtil;
import com.zznorth.tianji.utils.UIHelper;
import com.zznorth.tianji.view.SwipeRefreshLayout;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知公告
 * coder by 中资北方 on 2015/12/3.
 */
public class NotificationFragment extends BaseFragment {
    private static final String TAG = "NotificationFragment";
    @ViewInject(R.id.listView_notification)
    private ListView listView;
    @ViewInject(R.id.refresh_layout_notification)
    private SwipeRefreshLayout refreshLayout;
    private int page = 1;

    private final List<ContextNewsListBean> list = new ArrayList<>();
    private NotificationAdapter adapter;
    private SwipeRefreshLayout.OnRefreshListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container,false);
        x.view().inject(this, view);
        adapter = new NotificationAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisiable) {//初始化未完成或不可见
            return;
        }
        if (isFirstTimeVisiable) {
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

    /**
     * 初始化列表数据
     */
    private void initData() {
        UIHelper.GetDataFromNet(APIUtils.ContextArticleListLink(page, Config.TypeAnnouncement), new NetCall<String>() {
            @Override
            public void onSuccess(String result) {
                if (page == 1) {
                    list.clear();
                }
                list.addAll(JsonParser.ParserContextNewsList(result));
                initListView();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                refreshLayout.setRefreshing(false);
                refreshLayout.setLoading(false);
                if(NetUtil.isNetWork()) {
                    LogUtil.i(TAG, ex.toString() + "==");
                }
                initListView();
            }
        });
    }

    private  void initRefresh() {
        refreshLayout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setMode(SwipeRefreshLayout.Mode.BOTH);
        refreshLayout.setLoadNoFull(false);
        listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                initData();
            }
        };
        refreshLayout.setOnRefreshListener(listener);

        refreshLayout.setOnLoadListener(new SwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                page++;
                initData();
            }
        });
    }

    private void initListView() {
        adapter.notifyDataSetChanged();
        refreshLayout.setLoading(false);
        refreshLayout.setRefreshing(false);
    }

}
