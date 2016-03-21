package com.zznorth.tianji.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zznorth.tianji.services.TianjiService;

/**
 * coder by 中资北方 on 2016/1/7.
 */
public class RestartServiceBroadcast extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,TianjiService.class);
        context.startService(intent1);
    }
}
