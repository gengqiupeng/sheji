package com.zznorth.tianji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

/**
 * coder by 中资北方 on 2015/12/31.
 */
public class ReaderWebView extends WebView{
    public ReaderWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReaderWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    /*这几个变量需要设置为全局变量，否则手指抬起时一直输出为0*/
    float xoffset =0,yoffset=0;
    float downx=0,downy=0;
    float upx=0,upy=0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downx=event.getX();
                downy=event.getY();
                break;
            case MotionEvent.ACTION_UP:
                upx=event.getX();
                upy=event.getY();
                break;
            default:
                break;
        }
        xoffset=upx-downx;
        yoffset=upy-downy;
        Log.i("TAG", "xoff" + xoffset + "y" + yoffset);
        int direct=0;
        if(Math.abs(xoffset)>Math.abs(yoffset)) {
            return false;
            /*if (xoffset < 0) {
                direct = 1;
            } else {
                direct = 3;
            }*/
        }else {
            return true;
            /*if (yoffset > 0) {
                direct = 4;
            } else {
                direct = 2;
            }*/
        }
    }
}
