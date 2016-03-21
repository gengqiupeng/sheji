package com.zznorth.tianji.utils;

import android.os.Environment;
import android.widget.Toast;

import com.zznorth.tianji.ZZNHApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * coder by 中资北方 on 2016-2-1.
 */
public class TxtLog {
    public static void setInfo(String message){
        String time = FileUtils.ReadTime()+"\n ";
        //saveTxt(time+message);
    }

    public static boolean saveTxt(String log){
        String path = Environment.getExternalStorageDirectory().getPath();
        //sd卡检测
        String sdStatus = Environment.getExternalStorageState();
        if(!sdStatus.equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(ZZNHApplication.getInstance(), "SD 卡不可用", Toast.LENGTH_SHORT).show();
            return false;
        }
        //检测文件夹是否存在
        File file = new File(path);
        file.exists();
        file.mkdirs();
        String p = path+ File.separator+"日志.txt";
        FileOutputStream outputStream = null;
        try {
            //创建文件，并写入内容
            outputStream = new FileOutputStream(new File(p),true);
            String msg = new String(log);
            outputStream.write(msg.getBytes("UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(outputStream!=null){
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
