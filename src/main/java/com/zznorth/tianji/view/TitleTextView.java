package com.zznorth.tianji.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * coder by 中资北方 on 2015/12/4.
 */
public class TitleTextView extends TextView {
    public TitleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFont(context);
    }

    private void initFont(Context context) {
        AssetManager am = context.getAssets();
        Typeface font = Typeface.createFromAsset(am, "title_text_view.ttf");
        setTypeface(font);
    }

}
