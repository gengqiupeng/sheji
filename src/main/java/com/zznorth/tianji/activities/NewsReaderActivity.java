package com.zznorth.tianji.activities;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.zznorth.tianji.R;
import com.zznorth.tianji.ZZNHApplication;
import com.zznorth.tianji.base.BaseSwipeActivity;
import com.zznorth.tianji.bean.NewsBean;
import com.zznorth.tianji.callBack.NetCall;
import com.zznorth.tianji.utils.APIUtils;
import com.zznorth.tianji.utils.Config;
import com.zznorth.tianji.utils.JsonParser;
import com.zznorth.tianji.utils.NetUtil;
import com.zznorth.tianji.utils.UIHelper;
import com.zznorth.tianji.view.SwipeRefreshLayout;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * coder by 中资北方 on 2015/12/8.
 */
public class NewsReaderActivity extends BaseSwipeActivity {

    @ViewInject(R.id.text_read_title)
    private TextView title;
    @ViewInject(R.id.text_read_time)
    private TextView time;
    @ViewInject(R.id.text_top_title)
    private TextView top;
    @ViewInject(R.id.webView)
    private WebView web;
    @ViewInject(R.id.refresh_layout_reader)
    private SwipeRefreshLayout refreshLayout;
    private SwipeRefreshLayout.OnRefreshListener listener;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_reader;
    }

    @Override
    public void initView() {
        setSwipeAnyWhere(true);
        initRefresh();
        web.setBackgroundColor(0);
        if (Config.SYSVER < 19) {
            web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        web.getSettings().setBuiltInZoomControls(false);
        //initData();
        initLoading();
    }

    private void initRefresh() {
        refreshLayout.setColor(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setMode(SwipeRefreshLayout.Mode.BOTH);
        refreshLayout.setLoadNoFull(false);
        refreshLayout.setMode(SwipeRefreshLayout.Mode.DISABLED);
        listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        };
        refreshLayout.setOnRefreshListener(listener);
    }

    private void initData() {
        Intent intent = getIntent();
        if (null != intent) {
            String top_title = intent.getStringExtra("title");
            top.setText(top_title);
            String id = intent.getStringExtra("id");
            String link = APIUtils.ArticleContextLinkById(id);
            UIHelper.GetDataFromNet(link, new NetCall<String>() {
                @Override
                public void onSuccess(String result) {
                    NewsBean bean = JsonParser.ParserNews(result);
                    title.setText(bean.getTitle());
                    time.setText(bean.getFbrq());
                    web.loadDataWithBaseURL(null, bean.getContext(), "text/html", "utf-8", null);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    if (NetUtil.isNetWork()) {
                        UIHelper.ToastUtil("数据加载出错");
                    }
                }

                @Override
                public void onFinished() {
                    super.onFinished();
                    refreshLayout.setRefreshing(false);
                }
            });
        }
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

    @Event(value = R.id.image_back, type = View.OnClickListener.class)
    private void back(View view) {
       // finish();
        restart();
    }

    @Event(value = R.id.text_top_title, type = View.OnClickListener.class)
    private void finish2(View view) {
       // finish();
        restart();
    }

    @Event(value = R.id.image_title_user, type = View.OnClickListener.class)
    private void user(View v) {
        Intent intent = new Intent(NewsReaderActivity.this, UserCenterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        restart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!isNormalExit){
            restart();
        }
    }
    boolean isNormalExit = false;
    private void restart(){
        isNormalExit = true;
        Boolean alive= ZZNHApplication.getInstance().isActivityAlive(MainActivity.class);
        if(alive){
            finish();
        }else {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("position",2);
            startActivity(intent);
            finish();
        }
    }
}
