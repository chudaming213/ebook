package com.onon.test.bibleapplication;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by dami on 2017/8/18 0018.
 */

public abstract class MyClickableSpan extends ClickableSpan {
    private static final String TAG = "dami";
    private int mColor;
    private String tag;


    public MyClickableSpan(int mColor) {
        this.mColor = mColor;
    }

    public abstract void onClick(View widget, String tag);

    @Override
    public void onClick(View widget) {
        onClick(widget, null);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setColor(mColor);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
