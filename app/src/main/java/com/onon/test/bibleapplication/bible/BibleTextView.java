package com.onon.test.bibleapplication.bible;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.onon.test.bibleapplication.beans.BibleVerses;

import java.util.ArrayList;

/**
 * Created by dami on 2017/8/21 0021.
 */

public class BibleTextView extends TextView {
    private final static String PRETAG = "tag:";

    public BibleTextView(Context context) {
        super(context);
    }

    public BibleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BibleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static BibleTextView getBibleView(Context context) {
        BibleTextView bibleTextView = new BibleTextView(context);
        return bibleTextView;
    }

    BibleLineBackgroundSpan backgroundColorSpan;

    public final void setContent(ArrayList<BibleVerses> strings) {
        SpannableStringBuilder spannableString = new SpannableStringBuilder("");
        int start = 0;
        int end = 0;
        backgroundColorSpan = new BibleLineBackgroundSpan(strings);
        for (int i = 0; i < strings.size(); i++) {
            Log.d("dami", "getSpannableStringBuilder: " + strings.get(i).getText());
            spannableString.append(strings.get(i).getText());
            end = end + strings.get(i).getText().length();
            MyClickableSpan clickableSpan = new MyClickableSpan(getCurrentTextColor()) {
                @Override
                public void onClick(View widget, String tag) {
                    if (itemClickable && tag != null) {
                       boolean selected= backgroundColorSpan.isUnderline(Integer.parseInt(tag.substring(PRETAG.length())));
                        if (selected) {
                            disUnderline(Integer.parseInt(tag.substring(PRETAG.length())));
                        } else {
                            underline(Integer.parseInt(tag.substring(PRETAG.length())));
                        }
                        if (itemClickListener != null) {
                            itemClickListener.OnClickListener(widget, Integer.parseInt(tag.substring(PRETAG.length())), selected);
                        }
                    }
                }
            };
            clickableSpan.setTag(PRETAG + i);
            spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end - 1;
        }
        spannableString.setSpan(backgroundColorSpan, 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setMovementMethod(MyMethord.getInstance());
        setText(spannableString);
        setPadding(0, 10, 0, 50);
        setLineSpacing(10, 1f);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        float textSize = getTextSize();
        float padding = getResources().getDisplayMetrics().widthPixels % textSize;
        if (padding <= textSize / 2)
            padding += textSize;
        super.setPadding((int) (padding / 2), top, (int) (padding / 2), bottom);
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);
        setPadding(0, getPaddingTop(), 0, getPaddingBottom());
    }

    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        setPadding(0, getPaddingTop(), 0, getPaddingBottom());
    }

    public void unSeclect(int position) {
        if (backgroundColorSpan != null) {
            backgroundColorSpan.unSeclection(position);
            invalidate();
        }
    }

    public void seclect(int position) {
        if (backgroundColorSpan != null) {
            backgroundColorSpan.seclection(position);
            invalidate();
        }
    }

    public void disUnderline(int position) {
        if (backgroundColorSpan != null) {
            backgroundColorSpan.disUnderline(position);
            invalidate();
        }
    }

    public void underline(int position) {
        if (backgroundColorSpan != null) {
            backgroundColorSpan.underline(position);
            invalidate();
        }
    }

    /**
     * 条目是不是可点击
     */
    private boolean itemClickable = false;

    public void setItemClickable(boolean itemClickable) {
        this.itemClickable = itemClickable;
    }

    /**
     * 条目点击监听
     */
    private onItemClickListener itemClickListener;

    public void setItemClickListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ArrayList<BibleVerses> getUnderline() {
        return backgroundColorSpan.getUnderline();
    }

    public void highlight(ArrayList<BibleVerses> underline) {
        for (BibleVerses bibleVerses : underline) {
            backgroundColorSpan.seclection(bibleVerses.getVerseid()-1);
        }
        invalidate();
        backgroundColorSpan.clearUnderline();
    }
    public void unHighlight(ArrayList<BibleVerses> underline) {
        for (BibleVerses bibleVerses : underline) {
            backgroundColorSpan.unSeclection(bibleVerses.getVerseid()-1);
        }
        invalidate();
        backgroundColorSpan.clearUnderline();
    }

    public interface onItemClickListener {
        public void OnClickListener(View view, int position, boolean selected);
    }
}
