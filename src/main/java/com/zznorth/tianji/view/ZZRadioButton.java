package com.zznorth.tianji.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.zznorth.tianji.R;

/**
 * coder by 中资北方 on 2015/12/21.
 */
public class ZZRadioButton extends RadioButton {
    private boolean mTipOn = false;
    private Dot mDot;

    public ZZRadioButton(Context context) {
        super(context);
        init();
    }

    public ZZRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZZRadioButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mDot = new Dot();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTipOn) {
            float cx = getWidth() - mDot.marginRight - mDot.radius;
            float cy = mDot.marginTop + mDot.radius;
            Drawable drawableTop = getCompoundDrawables()[1];
            if (drawableTop != null) {
                int drawableTopWidth = drawableTop.getIntrinsicWidth();
                if (drawableTopWidth > 0) {
                    int dotLeft = getWidth() / 2 + drawableTopWidth / 2;
                    cx = dotLeft + mDot.radius;
                }
            }
            Paint paint = getPaint();
            //save
            int tempColor = paint.getColor();
            paint.setColor(mDot.color);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(cx, cy, mDot.radius, paint);
            //restore
            paint.setColor(tempColor);
        }
    }

    public void setTipOn(boolean tip) {
        this.mTipOn = tip;
        invalidate();
    }

    public boolean isTipOn() {
        return mTipOn;
    }

    private class Dot {
        final int color;
        final int radius;
        final int marginTop;
        final int marginRight;

        Dot() {
            float density = getContext().getResources().getDisplayMetrics().density;
            radius = (int) (5 * density);
            marginTop = (int) (3 * density);
            marginRight = (int) (3 * density);

            color = getContext().getResources().getColor(R.color.red);
        }
    }

}