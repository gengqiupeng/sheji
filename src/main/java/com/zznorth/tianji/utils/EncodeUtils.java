package com.zznorth.tianji.utils;

import android.content.res.Resources;
import android.util.TypedValue;

import com.zznorth.tianji.ZZNHApplication;

import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class EncodeUtils {
    private static final String TAG = "EncodeUtils";

    /**
     * MD5加密方法
     * @param text 要加密的字符串
     * @return 加密后的字符串，注意为空判断
     */
    public static String getMD5Encode(String text) {
        String encode="";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(text.getBytes());
            byte[] m = messageDigest.digest();//加密
            //小写32位加密
            StringBuffer sf = new StringBuffer();
            for (byte b:m) {
                int a = b&0xff;
                if(a<16){
                    sf.append(0);
                }
                sf.append(Integer.toHexString(a));
            }
            encode=sf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encode;
    }

    /** 浮点数转成字符串
     * @param number
     * @param length 要保留的小数位
     * @return
     */
    public static String Float2Str(float number,int length){
        String temp = number+"";
        return SplitStrNum(temp,length);
    }

    /** URL编码
     * @param decode 要编码的url
     * @return
     */
    public static String URLEncode(String decode){
        URL url = null;
        try {
            url = new URL(decode);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            url = uri.toURL();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(null!=url) {
            return url.toString();
        }else {
            return decode;
        }
    }

    /** 字符串小数截取
     * @param number
     * @param length 要保留的小数位
     * @return
     */
    public static String SplitStrNum(String number,int length){
        String[] temps=number.split("\\.");
        //always false;
        /*if(null==temps){
            return number;
        }*/
        if(temps.length<2){
            return number;
        }
        if(temps[1].length()<length){
            return temps[0]+"."+temps[1].substring(0,temps[1].length());
        }else {
            return temps[0]+"."+temps[1].substring(0,length);
        }
    }

    /** 将long类型的时间转换成字符串
     * @return
     */
    public static String FormatCurrentTime(){
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date(currentTime));
    }

    /** 把String转换成Calendar
     * @param time yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Calendar Str2Date(String time){
        Date date = null;
        if(time==null){
            return Calendar.getInstance();
        }else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = format.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            if(null!=date) {
                calendar.setTime(date);
                return calendar;
            }else {
                return Calendar.getInstance();
            }
        }
    }

    /** 像素转dp
     * @param px
     * @return
     */
    public static float Px2Dp(float px){
        Resources resources = ZZNHApplication.getInstance().getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.getDisplayMetrics());
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px( float dpValue) {
        final float scale = ZZNHApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip( float pxValue) {
        final float scale = ZZNHApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
