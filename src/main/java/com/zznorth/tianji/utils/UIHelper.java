package com.zznorth.tianji.utils;

import android.content.Context;
import android.widget.Toast;

import com.zznorth.tianji.ZZNHApplication;
import com.zznorth.tianji.callBack.NetCall;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;


/**
 * coder by 中资北方 on 2015/12/1.
 */
public class UIHelper {

    /**Toast工具
     * @param message
     */
    public static void ToastUtil(String message) {
        Toast.makeText(ZZNHApplication.getInstance(),message,Toast.LENGTH_SHORT).show();
    }

    /**Toast工具
     * @param message
     */
    public static void ToastUtil(Context context,String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /** 通过url获取网络数据
     * @param url
     * @param call
     */
    public static void GetDataFromNet(String url,Callback.CommonCallback<String> call){
       // TxtLog.setInfo("url " + url);
        RequestParams params =  new RequestParams(url);
        params.setUseCookie(false);
        String SessionId = FileUtils.ReadSessionId();
       // TxtLog.setInfo("getDataSession " + SessionId);
        if(null!=SessionId) {
          //  TxtLog.setInfo("Session not null \n");
            params.addHeader("Cookie", "ASP.NET_SessionId=" + SessionId);
        }else {
          //  TxtLog.setInfo("Session is null \n");
        }
        String header ="";
        List list = params.getHeaders();
        for(int i=0;i<list.size(); i++){
            header=header+list.get(i).toString()+"\n";
        }
      //  TxtLog.setInfo("headers:" + header);
        x.http().get(params, call);
    }

    /**
     * 刷新当前时间
     */
    public static void RefreshCurrentTime(){
        GetDataFromNet(APIUtils.ServicesTimeLink(), new NetCall<String>() {
            String time;
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    time = jsonObject.getString("CurrentDateTime");
                    FileUtils.StoredTime(time);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                time = EncodeUtils.FormatCurrentTime();
                FileUtils.StoredTime(time);
            }
        });
    }

}
