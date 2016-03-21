package com.zznorth.tianji.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.Html;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zznorth.tianji.R;
import com.zznorth.tianji.ZZNHApplication;
import com.zznorth.tianji.activities.LoginActivity;
import com.zznorth.tianji.activities.NewsReaderActivity;
import com.zznorth.tianji.bean.HistoryRemarkBean;
import com.zznorth.tianji.bean.RefreshNewsBean;
import com.zznorth.tianji.callBack.CallBoolean;
import com.zznorth.tianji.callBack.NetCall;
import com.zznorth.tianji.utils.APIUtils;
import com.zznorth.tianji.utils.CheckSessionUtil;
import com.zznorth.tianji.utils.Config;
import com.zznorth.tianji.utils.EncodeUtils;
import com.zznorth.tianji.utils.FileUtils;
import com.zznorth.tianji.utils.JsonParser;
import com.zznorth.tianji.utils.LogUtil;
import com.zznorth.tianji.utils.UIHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * coder by 中资北方 on 2015/12/14.
 */
public class TianjiService extends Service {
    private static final String TAG = "Service";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        HistoryTask(isTime(), beforeTime());
        PullNewNews();
        initExitService();
        receiveExitBroadcast();
        return START_STICKY;
    }

    /**
     * 查询是否有新的文本消息
     */
    private void PullNewNews() {
        Timer newsTimer = new Timer(true);
        TimerTask news = new TimerTask() {
            @Override
            public void run() {
                String time = getLastRefreshTime();
                //String time = "2015-12-08 11:11:11";
                UIHelper.GetDataFromNet(APIUtils.RefreshNewsLink(time), new NetCall<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.i(TAG,result);
                        SendBroadCast(result);
                    }
                });
            }
        };
        //查询新闻的频率
        newsTimer.schedule(news, 3 * 60 * 1000, 1 * 60 * 1000);
    }

    /**
     * 查询是否有新的操作历史
     *
     * @param time
     * @param before
     */
    private void HistoryTask(long time, long before) {
        Timer Tend = new Timer(true);
        final Timer Twait = new Timer(true);
        TimerTask pullData = new TimerTask() {
            @Override
            public void run() {
                //TODO 记得改时间
                String time = getLastRefreshTime();
                //String time = "2015-12-20 11:02:02";
                UIHelper.GetDataFromNet(APIUtils.RefreshSoldHistory(time), new NetCall<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if (null != result) {
                            LogUtil.i(TAG, result);
                        }
                        RefreshHistory(result);
                    }
                });
            }
        };
        TimerTask stop = new TimerTask() {
            @Override
            public void run() {
                LogUtil.i(TAG, "执行了取消");
                Twait.cancel();
                try {
                    Thread.sleep(30 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HistoryTask(isTime(), beforeTime());
            }
        };
        //刷新操作历史频率
        if (time > 0) {//当前时间在开盘期，直接开始刷新，定时结束
            Twait.schedule(pullData, 3 * 60 * 1000, 1 * 60 * 1000);
            Tend.schedule(stop, time * 60 * 1000);
        } else if (before != -1) {//当前时间在开盘前,等待开启通知，等待定时结束
            Twait.schedule(pullData, before * 60 * 1000, 1 * 60 * 1000);
            Tend.schedule(stop, before * 60 * 1000 + 120 * 60 * 1000);
        } else {//当前时间在开盘后，取消数据刷新。
            Twait.cancel();
        }
    }

    /**
     * 处理得到的最新操作历史数据
     *
     * @param data 服务器上获取的字符串
     */
    private void RefreshHistory(String data) {
        Intent intent = new Intent();
        intent.setAction("com.zznorth.newMessageCome");
        List<HistoryRemarkBean> list = JsonParser.ParserHistoryRemarkList(data);
        if (null!=list&&list.size() <= 0) {
            return;
        }
        if(FileUtils.ReadMainIsResume()) {
            intent.putExtra("history", Config.TypeHistory);
            sendBroadcast(intent);
        }else {
            List<RefreshNewsBean> history = new ArrayList<>();
            RefreshNewsBean bean = new RefreshNewsBean();
            //message
            HistoryRemarkBean bean1 = list.get(0);
            if(bean1.getOperation().contains("买")) {
                bean.setTitle("股票买入"+bean1.getStockName()+"("+bean1.getStockId()+")");
            }else {
                bean.setTitle("股票卖出"+bean1.getStockName()+"("+bean1.getStockId()+")");
            }
            bean.setContext("价格："+bean1.getPrice()+"数量："+bean1.getVolume()+"金额：" + bean1.getAmount());
            history.add(bean);
            OpenNotification(history, LoginActivity.class);
        }
    }

    //发送消息
    private void SendBroadCast(String data) {
        List<RefreshNewsBean> list = null;
        Intent intent = new Intent();
        intent.setAction("com.zznorth.newMessageCome");
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray array = jsonObject.getJSONArray("rows");
            list = new Gson().fromJson(array.toString(), new TypeToken<List<RefreshNewsBean>>() {
            }.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (null == list && list.size() <= 0) {
            return;
        }
        List<RefreshNewsBean> hot = new ArrayList<>();
        List<RefreshNewsBean> notice = new ArrayList<>();
        for (RefreshNewsBean m : list) {
            if (m.getType().contains("实时热点")) {
                intent.putExtra("hot", Config.TypeHotPoint);
                hot.add(m);
            }
            if (m.getType().equals("天玑提示")) {
                m.setTitle("天玑提示");
                intent.putExtra("notice", Config.TypeNotice);
                notice.add(m);
            }
        }
        UIHelper.RefreshCurrentTime();
        if (FileUtils.ReadMainIsResume()) {
            sendBroadcast(intent);
        } else {
            OpenNotification(hot, NewsReaderActivity.class);
            OpenNotification(notice, LoginActivity.class);
        }
    }

    //弹出通知
    private void OpenNotification(List<RefreshNewsBean> list, Class<?> cls) {
        if (null != list && list.size() > 0) {
            NotificationManager manager = (NotificationManager) getSystemService(ZZNHApplication.NOTIFICATION_SERVICE);
            for (int i = 0; i < list.size(); i++) {
                RefreshNewsBean bean = list.get(i);
                Notification notification = new Notification(R.drawable.ic_launcher, bean.getTitle(), System.currentTimeMillis());
                if (Config.SYSVER > 19) {
                    notification.icon = R.drawable.icon_no_bg;
                } else {
                    notification.icon = R.drawable.ic_launcher;
                }
                notification.defaults |= Notification.DEFAULT_SOUND;
                notification.defaults |= Notification.DEFAULT_VIBRATE;
                Intent intent = new Intent(ZZNHApplication.getInstance(), cls);
                intent.putExtra("id", bean.getId());
                intent.putExtra("title",bean.getType());
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                PendingIntent intent1 = PendingIntent.getActivity(ZZNHApplication.getInstance(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setLatestEventInfo(ZZNHApplication.getInstance(), bean.getTitle(), Html.fromHtml(bean.getContext()), intent1);
                manager.notify(i, notification);
            }
        }//end if
    }

    //=======================时间逻辑判断================================

    /**
     * @return 是否在开盘期间
     */
    private long isTime() {
        //9:30-11:30,13:00-15:00;
        Calendar cal = EncodeUtils.Str2Date(FileUtils.ReadTime());//日期
        int week = cal.get(Calendar.DAY_OF_WEEK);
        int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
        int minute = cal.get(Calendar.MINUTE);// 获取分钟
        int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数
        final int start = 9 * 60 + 30;// 起始时间的分钟数
        final int start2 = 12 * 60;
        final int end = 11 * 60 + 30;// 结束时间的分钟数
        final int end2 = 18 * 60;
        if (week == 1 || week == 7) {
            return -1;
        } else if (minuteOfDay >= start && minuteOfDay <= end) {
            return end - minuteOfDay;
        } else if (minuteOfDay >= start2 && minuteOfDay <= end2) {
            return end2 - minuteOfDay;
        } else {
            return -1;
        }
    }

    /**
     * @return 是否在开盘时间前
     */
    private long beforeTime() {
        //9:30-11:30,13:00-15:00;
        Calendar cal = EncodeUtils.Str2Date(FileUtils.ReadTime());//日期
        int week = cal.get(Calendar.DAY_OF_WEEK);
        int hour = cal.get(Calendar.HOUR_OF_DAY);// 获取小时
        int minute = cal.get(Calendar.MINUTE);// 获取分钟
        int minuteOfDay = hour * 60 + minute;// 从0:00分开是到目前为止的分钟数
        final int start = 9 * 60 + 30;// 起始时间的分钟数
        final int start2 = 12 * 60;
        if (week == 1 || week == 7) {
            return -1;
        } else if (minuteOfDay < start) {
            return start - minuteOfDay;
        } else if (minuteOfDay < start2) {
            return start2 - minuteOfDay;
        } else {
            return -1;
        }
    }

    private String getLastRefreshTime() {
        String time = FileUtils.ReadTime();
        if (null == time) {
            time = EncodeUtils.FormatCurrentTime();
        } else {
            time = FileUtils.ReadTime();
        }
        return time;
    }
//============================时间逻辑判断结束==========================

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!isNormalExit) {
            Intent intent = new Intent(ZZNHApplication.getInstance(), TianjiService.class);
            startService(intent);
        }
    }

    private boolean isNormalExit = false;
    /**
     * 退出服务
     */
    private void initExitService(){

       final CheckSessionUtil checkSessionUtil = new CheckSessionUtil(new CallBoolean() {
            @Override
            public void getBoolean(Boolean singleLogin) {
                if(!singleLogin){
                    Intent intent1 = new Intent();
                    intent1.setAction("com.zznorth.singleLogin");
                    ZZNHApplication.getInstance().sendBroadcast(intent1);
                    isNormalExit = true;
                    stopSelf();
                }
            }
        });
        checkSessionUtil.CheckSession();
    }

    private void receiveExitBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.zznorth.exitService");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LogUtil.i(TAG,"jieshou");
                isNormalExit = true;
                stopSelf();
            }
        };
        registerReceiver(receiver,filter);
    }
}
