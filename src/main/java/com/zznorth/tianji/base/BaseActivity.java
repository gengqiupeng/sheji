package com.zznorth.tianji.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zznorth.tianji.R;
import com.zznorth.tianji.ZZNHApplication;

import org.xutils.x;


public abstract class BaseActivity extends FragmentActivity {
    protected static String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create();
        //添加Activity到堆栈
        ZZNHApplication.getInstance().addActivity(this);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        x.view().inject(this);
        TAG = this.getClass().getName();
        initView();
        //设置透明状态栏
       initStatusBar();
    }

    /**
     * 作为onCreate的备用方法。一般无需重写
     */
    protected void create(){}

    protected abstract int getLayoutId();

    protected abstract void initView();

    /**
     * 初始化状态栏
     */
    protected void initStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(setStatusBarColor());//通知栏所需颜色
        setTranslucentStatus(true);
    }

    /** 设置状态栏颜色的方法
     * @return
     */
    private int setStatusBarColor(){
        return R.color.title_bar;
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZZNHApplication.getInstance().finishActivity(this);
    }
}
