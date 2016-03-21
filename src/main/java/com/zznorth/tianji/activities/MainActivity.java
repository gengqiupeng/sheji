package com.zznorth.tianji.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zznorth.tianji.R;
import com.zznorth.tianji.ZZNHApplication;
import com.zznorth.tianji.adapter.ViewPagerAdapter;
import com.zznorth.tianji.base.BaseActivity;
import com.zznorth.tianji.callBack.CallInt;
import com.zznorth.tianji.fragments.HotPointFragment;
import com.zznorth.tianji.fragments.InternalReferenceFragment;
import com.zznorth.tianji.fragments.NotificationFragment;
import com.zznorth.tianji.fragments.OpenInterestFragment;
import com.zznorth.tianji.fragments.TodayFragment;
import com.zznorth.tianji.services.TianjiService;
import com.zznorth.tianji.utils.Config;
import com.zznorth.tianji.utils.FileUtils;
import com.zznorth.tianji.utils.LogUtil;
import com.zznorth.tianji.utils.UIHelper;
import com.zznorth.tianji.utils.Update;
import com.zznorth.tianji.view.ZZRadioButton;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;
    @ViewInject(R.id.radioG_tab)
    private RadioGroup radioGroup;
    @ViewInject(R.id.text_top_title)
    private TextView top;
    private BroadcastReceiver receiver;
    private List<Fragment> fragments;
    private BroadcastReceiver receiver1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        getPhoneInfo();
        Intent intent = getIntent();
        int position = 0;
        if (null != intent) {
            position = intent.getIntExtra("position", 0);
        }
        //设置默认点击tab,默认title
        ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
        setTopTitle(0);
        //设置ViewPager预加载
        viewPager.setOffscreenPageLimit(0);
        FileUtils.StoreMainIsDestory(false);
        //初始化Fragments
        fragments = new ArrayList<>();
        TodayFragment fragment_today = new TodayFragment();
//        OpenInterestFragment fragment_open = new OpenInterestFragment();
        //HotPointFragment fragment1 = new HotPointFragment();
        HotPointFragment fragment_hot = new HotPointFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", Config.TypeHotPoint);
        fragment_hot.setArguments(bundle);
        InternalReferenceFragment fragment_internal = new InternalReferenceFragment();
        NotificationFragment fragment_notice = new NotificationFragment();
        fragments.add(fragment_today);//今日操作
//        fragments.add(fragment_open);//持仓状况
        fragments.add(fragment_hot);//实时热点
        fragments.add(fragment_internal);//独家内参
        // fragments.add(new OpenInterestFragment());
        fragments.add(fragment_notice);//通知公告
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        //初始化点击监听
        initAction();
        viewPager.setCurrentItem(position);
        initUpdate();
    }

    private void initAction() {
        //ViewPager监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑动过程中的监听
            }

            @Override
            public void onPageSelected(int position) {
                //从0开始
                checkedRadioButon(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //RadioGroup监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setCurrentViewPager(checkedId);
            }
        });
    }

    /**
     * 根据ViewPager设置RadioButton
     *
     * @param position
     */
    private void checkedRadioButon(int position) {
        ZZRadioButton rb = (ZZRadioButton) radioGroup.getChildAt(position);
        setTopTitle(position);
        rb.setChecked(true);
    }

    /**
     * 根据RadioButton设置ViewPager
     *
     * @param checkedId
     */
    private void setCurrentViewPager(int checkedId) {
        switch (checkedId) {
            case R.id.radio_tab_today:
                viewPager.setCurrentItem(0);
                break;
//            case R.id.radio_tab_interest:
//                viewPager.setCurrentItem(1);
//                break;
            case R.id.radio_tab_themes:
                viewPager.setCurrentItem(1);
                break;
            case R.id.radio_tab_notification:
                viewPager.setCurrentItem(2);
                break;
            case R.id.radio_tab_user:
                viewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }

    @Event(value = R.id.image_today_user, type = View.OnClickListener.class)
    private void user(View view) {
        Intent intent = new Intent(MainActivity.this, UserCenterActivity.class);
        startActivity(intent);
    }

    private void setTopTitle(int index) {
        String[] titles = new String[]{"今日操作", "实时热点", "独家内参", "通知公告"};
        top.setText(titles[index]);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Exit();
        }
        return false;
    }

    private long exitTime = 0;

    private void Exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            exitTime = System.currentTimeMillis();
            UIHelper.ToastUtil("请再次点击返回键退出");
        } else {
            ZZNHApplication.getInstance().ExitApp();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FileUtils.StoreMainIsResume(true);
        Intent intent = new Intent(this, TianjiService.class);
        startService(intent);
        UIHelper.RefreshCurrentTime();
        initBroadCastReceiver();
    }

    private void initBroadCastReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.zznorth.newMessageCome");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (-1 != intent.getIntExtra("hot", -1)) {
                    LogUtil.i(TAG, "hot");
                    setRedPoint(true, 2);
                }
                if (-1 != intent.getIntExtra("notice", -1)) {
                    LogUtil.i(TAG, "notice");
                    TodayFragment todayFragment = (TodayFragment) fragments.get(0);
                }
                if (-1 != intent.getIntExtra("history", -1)) {
                    LogUtil.i(TAG, "history");
                    setRedPoint(true, 0);
                }
            }
        };
        registerReceiver(receiver, filter);

        receiver1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                FileUtils.DeleteByTag(Config.SPAccountAuto);
                UIHelper.ToastUtil("您的账号在别处登录");
                //删除sessionid，退出服务
                FileUtils.DeleteByTag(Config.SPAccountSessionId);
                Intent exitIntent = new Intent();
                intent.setAction("com.zznorth.exitService");
                sendBroadcast(exitIntent);

                ZZNHApplication.getInstance().ExitApp();
                Intent intent1 = new Intent(ZZNHApplication.getInstance(), LoginActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ZZNHApplication.getInstance().startActivity(intent1);
            }
        };
        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("com.zznorth.singleLogin");
        registerReceiver(receiver1, filter1);
    }

    public void setRedPoint(boolean isShow, int position) {
        ZZRadioButton button = (ZZRadioButton) radioGroup.getChildAt(position);
        button.setTipOn(isShow);
    }

    public boolean isHaveNews(int position) {
        ZZRadioButton button = (ZZRadioButton) radioGroup.getChildAt(position);
        return button.isTipOn();
    }

    private void initUpdate() {
        if (!FileUtils.ReadIsClickUpdate()) {
            new Update(this, new CallInt() {
                @Override
                public void getInt(int code) {

                }
            });
        }
    }

    @Override
    protected void onStop() {
        FileUtils.StoreMainIsResume(false);
        unregisterReceiver(receiver);
        unregisterReceiver(receiver1);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileUtils.StoreMainIsDestory(true);
    }

    private void getPhoneInfo() {
        int sdk = Build.VERSION.SDK_INT;
        String model = Build.MODEL;
        String version = Build.VERSION.RELEASE;
        String rom = Build.MANUFACTURER;
        String cpu = Build.CPU_ABI;
        String hardware = Build.HARDWARE;
        // TxtLog.setInfo("phone\nsdk:" + sdk + " model:" + model + " version:" + version + " rom:" + rom + " cpu:" + cpu + " hardware:" + hardware);
    }
}
