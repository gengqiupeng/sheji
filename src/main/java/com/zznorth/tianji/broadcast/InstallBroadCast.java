package com.zznorth.tianji.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

/**
 * coder by 中资北方 on 2016/1/7.
 */
public class InstallBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
         String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/tianji.apk";
        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        intent1.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
