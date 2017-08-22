package com.onon.test.bibleapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.style.LineBackgroundSpan;
import android.util.Log;

import com.onon.test.bibleapplication.beans.BibleVerses;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dami on 2017/8/18 0018.
 */

public class BibleLineBackgroundSpan implements LineBackgroundSpan {
    private static final String TAG = "dami";
    private HashMap<String, BibleVerses> hashMap = new HashMap();
    private ArrayList<BibleVerses> underlines = new ArrayList();
    private int mEnd;
    private float startPoint;
    private int mStart;
    private float endPoint;
    private int curPos = 0;

    /**
     * 圆角半径
     */
    private int rx = 10;

    public BibleLineBackgroundSpan(ArrayList<BibleVerses> strings) {
        for (int i = 0; i < strings.size(); i++) {
            hashMap.put("i:" + i, strings.get(i));
        }
        initPaint();
    }

    public void seclection(int position) {
        hashMap.get("i:" + position).setSelected(true);
    }

    public void unSeclection(int position) {
        hashMap.get("i:" + position).setSelected(false);
    }
    public void clearUnderline( ) {
        for (BibleVerses underline : underlines) {
            underline.setUnderline(false);
        }
        underlines.clear();
    }
    public void underline(int position) {
        hashMap.get("i:" + position).setUnderline(true);
        underlines.add(hashMap.get("i:" + position));
    }

    public void disUnderline(int position) {
        hashMap.get("i:" + position).setUnderline(false);
        underlines.remove(hashMap.get("i:" + position));
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    private Paint mPaint;

    public int getmLineColor() {
        return mLineColor;
    }

    public void setmLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
    }

    /**
     * 下划线颜色
     */
    private int mLineColor = Color.parseColor("#FF484D4C");

    public int getmBackColor() {
        return mBackColor;
    }

    public void setmBackColor(int mBackColor) {
        this.mBackColor = mBackColor;
    }

    /**
     * 选中背景色
     */
    private int mBackColor = Color.parseColor("#FFE9CEC3");

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {

        float letterSpacing = getDistance(paint);
        if (lnum == 0) {
            curPos = 0;
            endPoint = 0;
            startPoint = 0;
            mStart = 0;
            mEnd = 0;

            mStart = mEnd;
            mEnd = mEnd + hashMap.get("i:" + curPos).getText().length();
            for (int i = 0; i < 8; i++) {
                if (hashMap.get("i:" + curPos).getText().charAt(i) == ' ') {
                    mStart++;
                } else {
                    break;
                }
            }
        }
        if (mEnd <= end) {
            Log.d(TAG, "drawBackground:mEnd ");
            for (int p = curPos; p < hashMap.size(); p++) {
                Log.d(TAG, "drawBackground: " + p);

                if (mEnd == end) {
                    Log.d(TAG, "drawBackground: mEnd == end");
                    //绘制
                    if (mStart <= start) {
                        startPoint = left;
                    } else {
                        startPoint = paint.measureText(text, start, mStart);
                    }
                    if (curPos == hashMap.size() - 1) {
                        Log.d(TAG, "drawBackground: curPos == hashMap.size()");
                        endPoint = paint.measureText(text, start, mEnd);
                    } else {
                        endPoint = right;
                    }
                    if (hashMap.get("i:" + curPos).isUnderline()) {
                        drawUnderLine(canvas, startPoint, endPoint, bottom, letterSpacing);
                    }
                    if (hashMap.get("i:" + curPos).isSelected()) {
                        drawBackground(canvas, startPoint, top, endPoint, bottom, letterSpacing);
                    }
                    curPos++;
                    if (hashMap.containsKey("i:" + curPos)) {
                        mStart = mEnd;
                        mEnd = mEnd + hashMap.get("i:" + curPos).getText().length();
                    }
                    break;
                } else if (mEnd < end) {
                    //绘制
                    if (mStart <= start) {
                        startPoint = left;
                    } else {
                        startPoint = paint.measureText(text, start, mStart);
                    }
                    endPoint = paint.measureText(text, start, mEnd);
                    Log.d(TAG, "startPoint: " + startPoint);

                    if (hashMap.get("i:" + curPos).isUnderline()) {
                        drawUnderLine(canvas, startPoint, endPoint - letterSpacing, bottom, letterSpacing);
                    }
                    if (hashMap.get("i:" + curPos).isSelected()) {
                        drawBackground(canvas, startPoint, top, endPoint - letterSpacing, bottom, letterSpacing);
                    }
                    Log.d(TAG, "drawBackground: ");
                    //取出下一个
                    curPos++;
                    if (hashMap.containsKey("i:" + curPos)) {
                        mStart = mEnd;
                        mEnd = mEnd + hashMap.get("i:" + curPos).getText().length();
                    }
                } else {
                    Log.d(TAG, "drawBackground: --");
                    //绘制
                    startPoint = paint.measureText(text, start, mStart);
                    if (hashMap.get("i:" + curPos).isUnderline()) {
                        drawUnderLine(canvas, startPoint, right, bottom, letterSpacing);
                    }
                    if (hashMap.get("i:" + curPos).isSelected()) {
                        drawBackground(canvas, startPoint, top, right, bottom, letterSpacing);
                    }
                    break;
                }
            }
        } else {
            if (mStart > start) {
                startPoint = paint.measureText(text, start, mStart);
                if (hashMap.get("i:" + curPos).isUnderline()) {
                    drawUnderLine(canvas, startPoint, right, bottom, letterSpacing);
                }
                if (hashMap.get("i:" + curPos).isSelected()) {
                    drawBackground(canvas, startPoint, top, right, bottom, letterSpacing);
                }
            } else {
                if (hashMap.get("i:" + curPos).isUnderline()) {
                    drawUnderLine(canvas, left, right, bottom, letterSpacing);
                }
                if (hashMap.get("i:" + curPos).isSelected()) {
                    drawBackground(canvas, left, top, right, bottom, letterSpacing);
                }
            }
        }

    }

    /**
     * 绘制选中背景
     *
     * @param canvas
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param letterSpacing
     */
    private void drawBackground(Canvas canvas, float left, float top, float right, float bottom, float letterSpacing) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBackColor);
        canvas.drawRoundRect(left, top, right, bottom - letterSpacing, rx, rx, mPaint);
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    /**
     * 下划线宽度
     */
    int strokeWidth = 4;

    /**
     * 绘制下划线
     *
     * @param canvas
     * @param left
     * @param right
     * @param bottom
     * @param letterSpacing
     */
    private void drawUnderLine(Canvas canvas, float left, float right, float bottom, float letterSpacing) {
        DashPathEffect pathEffect = new DashPathEffect(new float[]{8, 8}, 4);
        mPaint.setColor(mLineColor);
        mPaint.setPathEffect(pathEffect);
        mPaint.setStrokeWidth(strokeWidth);
        Path path = new Path();
        path.moveTo(left, bottom - letterSpacing + strokeWidth / 2);
        path.lineTo(right, bottom - letterSpacing + strokeWidth / 2);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);
    }

    private float getDistance(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float v = fontMetrics.bottom - fontMetrics.top - paint.getTextSize();
        return v / 2;
    }

    public ArrayList<BibleVerses> getUnderline() {
        return underlines;
    }

    public boolean isUnderline(int i) {
       return hashMap.get("i:" + i).isUnderline();
    }
}
