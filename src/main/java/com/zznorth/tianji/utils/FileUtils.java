package com.zznorth.tianji.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zznorth.tianji.ZZNHApplication;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.ref.WeakReference;


/**
 * coder by 中资北方 on 2015/12/3.
 */
public class FileUtils {

    //==================SharedPreference相关=========================

    /**
     * 保存用户名密码
     *
     * @param name
     * @param pwd
     */
    public static void StoreNamePwd(String name, String pwd, boolean isAutoLogin) {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.SPAccountName, name);
        editor.putString(Config.SPAccountPwd, pwd);
        editor.putBoolean(Config.SPAccountAuto, isAutoLogin);
        editor.apply();
    }

    /**
     * 储存用户名
     *
     * @param name
     */
    public static void StoreName(String name) {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.SPAccountName, name);
        editor.apply();
    }

    /**
     * 储存用户
     *
     * @param context
     */
    public static void StoreUser(com.zznorth.tianji.bean.Context context) {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.SPScreenName, context.getAccountName());
        editor.putString(Config.SPOutDate, context.getExpiredTime());
        editor.apply();
    }

    /**
     * 读取用户
     *
     * @return
     */
    public static com.zznorth.tianji.bean.Context ReadUser() {
        WeakReference<ZZNHApplication> s_context = new WeakReference<>(ZZNHApplication.getInstance());
        ZZNHApplication application = s_context.get();
        SharedPreferences sharedPreferences = application.getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        com.zznorth.tianji.bean.Context context = new com.zznorth.tianji.bean.Context();
        context.setAccountId(sharedPreferences.getString(Config.SPAccountName, "ID"));
        context.setAccountName(sharedPreferences.getString(Config.SPScreenName, "用户名"));
        context.setExpiredTime(sharedPreferences.getString(Config.SPOutDate, "过期时间"));
        return context;
    }

    /**
     * 储存SessionId
     *
     * @param SessionId
     */
    public static void StoredSessionId(String SessionId) {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.SPAccountSessionId, SessionId);
       // TxtLog.setInfo("session " + SessionId);
        editor.commit();
    }

    /**
     * 读取SessionId
     *
     * @return
     */
    public static String ReadSessionId() {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        return sharedPreferences.getString(Config.SPAccountSessionId, null);
    }


    /**
     * 删除保存的账号密码
     */
    public static void RemoveNamePwd() {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Config.SPAccountName);
        editor.remove(Config.SPAccountPwd);
        editor.remove(Config.SPAccountAuto);
        editor.apply();
    }

    /**
     * 删除保存的密码
     */
    public static void RemovePwd() {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Config.SPAccountPwd);
        editor.remove(Config.SPAccountAuto);
        editor.apply();
    }

    /**
     * 从sharedpreferences读取用户名
     *
     * @return
     */
    public static String ReadName() {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        return sharedPreferences.getString(Config.SPAccountName, null);
    }

    /**
     * 从sharedpreferences读取密码
     *
     * @return
     */
    public static String ReadPwd() {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        return sharedPreferences.getString(Config.SPAccountPwd, null);
    }

    /**
     * 从sharedpreferences读取是否自动登陆
     *
     * @return
     */
    public static boolean ReadIsAuto() {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        return sharedPreferences.getBoolean(Config.SPAccountAuto, false);
    }

    /**
     * 存储时间
     *
     * @param time
     */
    public static void StoredTime(String time) {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Config.SPTime, time);
        editor.apply();
    }

    /**
     * 存储MainActivity是否可见
     */
    public static void StoreMainIsResume(boolean resume) {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Config.SPResume, resume);
        editor.commit();
    }

    /**
     * @return 读取MainActivity是否可见
     */
    public static boolean ReadMainIsResume() {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        return sharedPreferences.getBoolean(Config.SPResume, false);
    }

    /**
     * 存储MainActivity是否销毁
     */
    public static void StoreMainIsDestory(boolean resume) {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Config.SPDestroy, resume);
        editor.apply();
    }

    /**
     * @return 读取MainActivity是否销毁
     */
    public static boolean ReadMainIsDestory() {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        return sharedPreferences.getBoolean(Config.SPDestroy, false);
    }

    /**
     * @param tag
     */
    public static void DeleteByTag(String tag) {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(tag);
        editor.apply();
    }

    /** 获取时间，非空判断
     * @return time or null
     */
    public static String ReadTime() {
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_MULTI_PROCESS);
        return sharedPreferences.getString(Config.SPTime, null);
    }

    /** 储存是否点击了登录界面的按钮
     * @param isClick
     */
    public static void StoreIsClickUpdate(boolean isClick){
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Config.SPIsClickUpdate,isClick);
        editor.apply();
    }

    /** 读取是否点击量登录界面的按钮
     * @return
     */
    public static boolean ReadIsClickUpdate(){
        SharedPreferences sharedPreferences = ZZNHApplication.getInstance().getSharedPreferences(Config.SPFileName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Config.SPIsClickUpdate,true);
    }

    //=======================下载======================
    public static <T> Callback.Cancelable DownLoadFile(String url, String filepath, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        return x.http().get(params, callback);
    }

}
