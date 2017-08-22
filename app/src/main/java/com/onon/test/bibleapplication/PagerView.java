package com.onon.test.bibleapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dami on 2017/8/21 0021.
 */

public class PagerView extends View {
    public PagerView(Context context) {
        super(context);
        init();
    }

    public PagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    boolean touch = false;

    Paint mPaint;

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private static final String TAG = "dami";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ax = event.getX();
                ay = event.getY();
                touch = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                ax = event.getX();
                ay = event.getY();
                touch = true;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch = false;
                break;
        }
        return true;
    }

    Path mPath0;
    float ax, ay, bx, by, cx, cy, dx, dy, ex, ey, fx, fy, gx, gy, hx, hy, ix, iy, jx, jy, kx, ky;

    private void mathPoints() {
        fx = getWidth();
        fy = getHeight();
        gx = (ax + fx) / 2;
        gy = (ay + fy) / 2;
        float em = (getHeight() - gy) * (getHeight() - gy) / (fx - gx);

        ex = gx - em;
        ey = getHeight();

        float hn = (getWidth() - gx) * (getWidth() - gx) / (fy - gy);

        hx = getWidth();
        hy = gy - hn;

        cx = ex - (fx - ex) / 2;
        cy = getHeight();

        jx = getWidth();
        jy = hy - (fy - hy) / 2;

        float a1, a2, b1, b2;

        a1 = (ay - ey) / (ax - ex);
        b1 = (ey * ax - ay * ex) / (ax - ex);
        a2 = (cy - jy) / (cx - jx);
        b2 = (jy * cx - cy * jx) / (cx - jx);
        bx = (b2 - b1) / (a1 - a2);
        by = a1 * bx + b1;

        a1 = (ay - hy) / (ax - hx);
        b1 = (hy * ax - ay * hx) / (ax - hx);
        a2 = (cy - jy) / (cx - jx);
        b2 = (jy * cx - cy * jx) / (cx - jx);
        kx = (b2 - b1) / (a1 - a2);
        ky = a1 * kx + b1;

        dx = ((cx + bx) / 2 + ex) / 2;
        dy = ((cy + by) / 2 + ey) / 2;

        ix = ((jx + kx) / 2 + hx) / 2;
        iy = ((jy + ky) / 2 + hy) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mathPoints();
        mPath0 = new Path();
        if (touch) {
            mPath0.moveTo(jx, jy);
            mPath0.quadTo(hx, hy, kx, ky);
            mPath0.lineTo(ax, ay);
            mPath0.lineTo(bx, by);
            mPath0.quadTo(ex, ey, cx, cy);
            mPath0.lineTo(fx, fy);
            mPath0.close();
        }
        Log.d(TAG, "onDraw: ");
        canvas.drawPath(mPath0, mPaint);
        canvas.clipPath(mPath0);
//        canvas.clipPath(mPath1, Region.Op.DIFFERENCE); //绘制第一次不同于第二次的区域
    }
}
