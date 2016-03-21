package com.zznorth.tianji.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.zznorth.tianji.R;
import com.zznorth.tianji.activities.MainActivity;
import com.zznorth.tianji.adapter.HistoryRemarkAdapter;
import com.zznorth.tianji.base.BaseFragment;
import com.zznorth.tianji.bean.HistoryRemarkBean;
import com.zznorth.tianji.callBack.NetCall;
import com.zznorth.tianji.utils.APIUtils;
import com.zznorth.tianji.utils.JsonParser;
import com.zznorth.tianji.utils.LogUtil;
import com.zznorth.tianji.utils.NetUtil;
import com.zznorth.tianji.utils.UIHelper;
import com.zznorth.tianji.view.SwipeRefreshLayout;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 今日操作
 * coder by 中资北方 on 2015/12/3.
 */
public class TodayFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private static final String TAG = "TodayFragment";
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;
    private ViewPager viewPager;
    private ImageView iv1, iv2, iv3, iv4, iv5;
    private int page = 1;
    private final List<HistoryRemarkBean> list = new ArrayList<>();
    private HistoryRemarkAdapter adapter;
    private SwipeRefreshLayout.OnRefreshListener listener;
    private ImageView[] viewPagerImg;
    private ImageView[] smallPic;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today, container, false);
        x.view().inject(this, view);
        initView();
        adapter = new HistoryRemarkAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        isPrepared = true;
        setViewPagerData();
        lazyLoad();
        viewPagerLoadData();
        viewPagerWebData();
        return view;
    }

    /**
     * 网络下载ViewPager的图片
     */
    private void viewPagerWebData() {
        String imgUrl = "";
        ImageOptions options = new ImageOptions.Builder().build();
        x.image().loadDrawable(imgUrl, options, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void initView() {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout_history);
        viewPager = (ViewPager) view.findViewById(R.id.vp);
        listView = (ListView) view.findViewById(R.id.listView_today_list);
        iv1 = (ImageView) view.findViewById(R.id.iv01);
        iv1.setImageResource(R.drawable.page_now);
        iv2 = (ImageView) view.findViewById(R.id.iv02);
        iv3 = (ImageView) view.findViewById(R.id.iv03);
        iv4 = (ImageView) view.findViewById(R.id.iv04);
        iv5 = (ImageView) view.findViewById(R.id.iv05);
        smallPic = new ImageView[]{iv1, iv2, iv3, iv4, iv5};
    }

    /**
     * ViewPager显示默认图片
     */
    private void viewPagerLoadData() {
        ViewPagerAdaper pagerAdaper = new ViewPagerAdaper();
        viewPager.setAdapter(pagerAdaper);
        viewPager.setOnPageChangeListener(this);
//        viewPager.getChildAt(0);
    }

    /**
     * 将ViewPager中默认需要的数据准备好
     */
    private void setViewPagerData() {
        int[] dwIds = new int[]{R.drawable.view_pager01, R.drawable.view_pager02, R.drawable.view_pager03, R.drawable.view_pager04, R.drawable.view_pager05};
        viewPagerImg = new ImageView[dwIds.length];
        for (int i = 0; i < dwIds.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(dwIds[i]);
            viewPagerImg[i] = imageView;
        }
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisiable) {
            return;
        }
        if (isFirstTimeVisiable) {
            initListViewData(page);
            initRefresh();
        }
        CancelRedPoint();
        isFirstTimeVisiable = false;
    }

    /**
     * 初始化listView的数据
     */
    private void initListViewData(final int page) {
        // TxtLog.setInfo("link " + APIUtils.PurchaseHistoryLink(page) + "\n");
        UIHelper.GetDataFromNet(APIUtils.PurchaseHistoryLink(page), new NetCall<String>() {
            @Override
            public void onSuccess(String result) {
                //  TxtLog.setInfo("todaysuccess " + result + "\n");
                if (page == 1) {
                    list.clear();
                    CancelRedPoint();
                }
                list.addAll(JsonParser.ParserHistoryRemarkList(result));
                initListView();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //  TxtLog.setInfo("todayerror " + ex.toString() + "\n");
                initListView();
                refreshLayout.setRefreshing(false);
                refreshLayout.setLoading(false);
                if (NetUtil.isNetWork()) {
                    UIHelper.ToastUtil("糟糕，加载失败，向下滑动重新加载");
                }
                LogUtil.i(TAG, ex.toString());
            }
        });
    }

    /**
     * 初始化下拉刷新
     */
    private void initRefresh() {
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
                initListViewData(page);
            }
        };
        refreshLayout.setOnRefreshListener(listener);

        refreshLayout.setOnLoadListener(new SwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                page++;
                initListViewData(page);
            }
        });
    }

    private void initListView() {
        refreshLayout.setLoading(false);
        refreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }

    private void CancelRedPoint() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (null != mainActivity && mainActivity.isHaveNews(0)) {
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                }
            });
            listener.onRefresh();
            mainActivity.setRedPoint(false, 0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < viewPagerImg.length; i++) {
            smallPic[i].setImageResource(R.drawable.page);
        }
        smallPic[position % viewPagerImg.length].setImageResource(R.drawable.page_now);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class ViewPagerAdaper extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewPagerImg[position % viewPagerImg.length]);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewPagerImg[position % viewPagerImg.length], 0);
            return viewPagerImg[position % viewPagerImg.length];
        }
    }
}
