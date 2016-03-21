package com.zznorth.tianji.utils;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.zznorth.tianji.R;
import com.zznorth.tianji.ZZNHApplication;
import com.zznorth.tianji.bean.UpdateBean;
import com.zznorth.tianji.callBack.CallInt;
import com.zznorth.tianji.callBack.NetCall;
import com.zznorth.tianji.callBack.ProgressCall;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * coder by 中资北方 on 2015/12/25.
 */
public class Update {

    private static final String TAG = "Update";
    private final Context context;
    private UpdateBean bean;
    private final String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/tianji.apk";
    private final ProgressDialog progressDialog;
    private final NotificationManager manager;

    public Update(Context context, CallInt call) {
        this.context = context;
        checkVer(call);
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("下载更新包");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
    }

    private String getCurrentVersion() {
        String localVer = "";
        PackageManager manager = context.getPackageManager();
        try {
            localVer = manager.getPackageInfo(Config.PackageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVer;
    }

    private int flag = Config.UpdateNone;

    private void checkVer(final CallInt call) {
        UIHelper.GetDataFromNet(APIUtils.CheckVersionLink(), new NetCall<String>() {
            @Override
            public void onSuccess(String result) {
                bean = new Gson().fromJson(result, UpdateBean.class);
                String serviceVer = bean.getCurrentVerson();
                if (CompareVer(getCurrentVersion(), serviceVer)) {
                    if (bean.getIsForceUpdate()) {
                        flag = Config.UpdateEnforce;
                    } else {
                        flag = Config.UpdateDefault;
                    }
                } else {
                    return;
                }
                call.getInt(flag);
                ShowInfo();
            }
        });
    }

    private boolean dialogIsAlive = false;

    public void ShowInfo() {
        FileUtils.StoreIsClickUpdate(false);
        if (!dialogIsAlive) {
            dialogIsAlive = true;
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setIcon(R.drawable.ic_launcher);
            dialog.setTitle("天玑壹号版本更新");
            String updateInfo = "";
            if (bean.getIsForceUpdate()) {
                updateInfo = "\n本次更新为必选更新";
            }
            dialog.setMessage(bean.getDescription() + updateInfo);
            dialog.setPositiveButton("现在更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UIHelper.ToastUtil("正在下载中，请稍后");
                    downLoad();
                    FileUtils.StoreIsClickUpdate(true);
                    dialogIsAlive = false;
                }
            });
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FileUtils.StoreIsClickUpdate(true);
                    dialogIsAlive = false;
                }
            });
            AlertDialog alertDialog = dialog.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    }

    static class MyHandler extends Handler{
        WeakReference<Update> updateWeakReference;
        MyHandler(Update update){
            updateWeakReference = new WeakReference<>(update);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Update update = updateWeakReference.get();
            Context context = ZZNHApplication.getInstance();
            switch (msg.what) {
                case Config.UpdateEnforce:
                    update.progressDialog.setMax(100);
                    update.progressDialog.setProgress(msg.arg1);
                    update.progressDialog.show();
                    break;
                case Config.UpdateDefault:
                    Notification.Builder builder = new Notification.Builder(context);
                    builder.setContentTitle("正在下载更新包");
                    builder.setContentText(msg.arg1 + "%");
                    if (Config.SYSVER > 19) {
                        builder.setSmallIcon(R.drawable.icon_no_bg);
                    } else {
                        builder.setSmallIcon(R.drawable.ic_launcher);
                    }
                    builder.setProgress(100, msg.arg1, false);
                    update.manager.notify(0, builder.getNotification());
                    break;
                case Config.UdateDone:
                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification.Builder done = new Notification.Builder(context);
                    done.setContentTitle("更新包准备完毕");
                    done.setContentText("点击安装");
                    if (Config.SYSVER > 19) {
                        done.setSmallIcon(R.drawable.icon_no_bg);
                    } else {
                        done.setSmallIcon(R.drawable.ic_launcher);
                    }
                    done.setProgress(100, 100, false);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, new Intent("Install"), PendingIntent.FLAG_UPDATE_CURRENT);
                    done.setContentIntent(pendingIntent);
                    manager.notify(0, done.getNotification());
                    break;
            }
        }
    }

    MyHandler handler = new MyHandler(Update.this);

    private void downLoad() {
        FileUtils.DownLoadFile(bean.getUpdateUrl(), path, new ProgressCall<File>() {
            @Override
            public void onSuccess(File result) {
                progressDialog.cancel();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                Message message = Message.obtain();
                message.what = Config.UdateDone;
                handler.sendMessage(message);
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                long temp = current * 100 / total;
                LogUtil.i(TAG, temp);
                Message message = Message.obtain();
                message.arg1 = (int) temp;
                if (bean.getIsForceUpdate()) {
                    message.what = Config.UpdateEnforce;
                } else {
                    message.what = Config.UpdateDefault;
                }
                handler.sendMessage(message);
            }
        });
    }

    private boolean CompareVer(String currentVer, String serviceVer) {
        LogUtil.i(TAG,currentVer+":"+serviceVer);
        String[] current = currentVer.split("\\.");
        String[] service = serviceVer.split("\\.");
        if (current.length != service.length) {
            return false;
        } else {
            for (int i = 0; i < current.length; i++) {
                if (Integer.parseInt(current[i]) < Integer.parseInt(service[i])) {
                    return true;
                }
            }
        }
        return false;
    }

}
