package com.zznorth.tianji.utils;

import com.zznorth.tianji.bean.LoginInfo;
import com.zznorth.tianji.callBack.CallBoolean;
import com.zznorth.tianji.callBack.NetCall;

import java.util.Timer;
import java.util.TimerTask;

/**
 * coder by 中资北方 on 2016/1/8.
 */
public class CheckSessionUtil {

    private String TAG = "CheckUtil";
    private Timer check;
    private CallBoolean call;

    public CheckSessionUtil(CallBoolean call) {
        this.call = call;
    }

    /**
     * 检查是否是多点登录
     */
    public void CheckSession() {
        LogUtil.i(TAG, "run");
        check = new Timer(true);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                UIHelper.GetDataFromNet(APIUtils.CheckSessionLink(), new NetCall<String>() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtil.i(TAG, result);
                        LoginInfo info = JsonParser.ParserLoginInfo(result);
                        isLogOut(info, check);
                    }
                });
            }
        };
            check.schedule(task, 30*1000, 30 * 1000);
    }

    public void cancleCheck(){
        check.cancel();
    }

    private void isLogOut(LoginInfo info, Timer check) {
        call.getBoolean(info.isResult());

        LogUtil.i(TAG, info.isResult() + "");
        if (!info.isResult()) {
            check.cancel();
        }
    }
}
