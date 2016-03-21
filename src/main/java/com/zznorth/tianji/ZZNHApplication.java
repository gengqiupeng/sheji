package com.zznorth.tianji;

import android.app.Activity;
import android.app.Application;

import com.zznorth.tianji.utils.LogUtil;

import org.xutils.x;

import java.util.Stack;


public class ZZNHApplication extends Application {
    private final String TAG = "ZZNHApplication";

    private static Stack<Activity> activityStack;
    private static ZZNHApplication singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(false);

        singleton = this;
    }

    public static ZZNHApplication getInstance() {
        return singleton;
    }

    /**
     * 把Activity添加到栈中
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
        LogUtil.i(TAG, "当前回退栈的Activity数量:" + activityStack.size());
    }

    /**
     * 当前Activity
     *
     * @return
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }


    public void finishCurrentActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void ExitApp() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            LogUtil.i(TAG,e.toString());
        }
    }

    /** 检查特定的Activity是否存活
     * @param cls
     * @return
     */
    public boolean isActivityAlive(Class<?> cls){
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

}
