package com.zznorth.tianji.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zznorth.tianji.R;
import com.zznorth.tianji.activities.NewsReaderActivity;
import com.zznorth.tianji.adapter.NewsListAdapter;
import com.zznorth.tianji.bean.NewsListBean;
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
 * coder by 中资北方 on 2015/12/22.
 */
public abstract class BaseNewsFragment extends BaseFragment {

    private static final String TAG = "BaseNewsFragment";
    @ViewInject(R.id.listView_hot_point)
    private ListView listView;
    @ViewInject(R.id.refresh_layout_hotpoint)
    protected SwipeRefreshLayout refreshLayout;
    private int page = 1;
    private final List<NewsListBean> list = new ArrayList<>();
    private NewsListAdapter adapter;
    protected SwipeRefreshLayout.OnRefreshListener listener;
    private int type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_point, container,false);
        x.view().inject(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
        }
        adapter = new NewsListAdapter(getActivity(), list, true);
        listView.setAdapter(adapter);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    /**
     * 初始化列表数据
     */
    private void initData() {
        String link = APIUtils.ArticleListLink(page, 20, type);
       // TxtLog.setInfo("newslink " + link);
        UIHelper.GetDataFromNet(link, new NetCall<String>() {
            @Override
            public void onSuccess(String result) {
              //  TxtLog.setInfo("newssuccess " + result);
                if (page == 1) {
                    list.clear();
                }
                list.addAll(JsonParser.ParserNewsList(result));
                initListView(list);
                RefreshData();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
               // TxtLog.setInfo("newerror " + ex.toString());
                refreshLayout.setRefreshing(false);
                refreshLayout.setLoading(false);
                if(NetUtil.isNetWork()) {
                    UIHelper.ToastUtil("糟糕，加载失败，向下滑动重新加载");
                }
                LogUtil.i(TAG, ex.toString());
            }
        });
    }

    /**
     * 初始化下拉刷新
     */
    protected void initRefresh() {
        refreshLayout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setMode(SwipeRefreshLayout.Mode.BOTH);
        refreshLayout.setLoadNoFull(false);
        refreshLayout.setOnLoadListener(new SwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                page++;
                initData();
            }
        });
        listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                initData();
            }
        };
        //下拉刷新需要自动刷新，所以需要将listener设置为全局变量
        refreshLayout.setOnRefreshListener(listener);
    }

    protected abstract void RefreshData();

    /**
     * 初始化listView
     *
     * @param list
     */
    private void initListView(final List<NewsListBean> list) {
        refreshLayout.setRefreshing(false);
        refreshLayout.setLoading(false);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsListBean bean = list.get(position);
                Intent intent = new Intent(getActivity(), NewsReaderActivity.class);
                String title ;
                switch (type) {//独家内参也的这个Fragment
                    case Config.TypeHotPoint:
                        title = "实时热点";
                        break;
                    case Config.TypeZZView:
                        title = "中资观点";
                        break;
                    case Config.TypeTheme:
                        title = "题材风向标";
                        break;
                    case Config.TypeInterpretation:
                        title = "政策解读";
                        break;
                    default:
                        title = "天玑一号";
                        break;
                }
                intent.putExtra("title", title);
                intent.putExtra("id", bean.getId());
                startActivity(intent);
            }
        });
    }


}
