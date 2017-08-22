package com.onon.test.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by dami on 2017/8/21 0021.
 */

public class PagerView extends TextView {
    private Matrix mMatrix;
    private GradientDrawable mFolderShadowDrawableRL;
    private ColorMatrixColorFilter mColorMatrixFilter;
    private Bitmap bitmap1;
    private Canvas backCanvas;
    private float endx, endy;

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
    Paint mPaint1;

    Bitmap backgroundBitmap;

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint1 = new Paint();
        mPaint1.setColor(Color.GREEN);
        mPaint1.setStrokeWidth(5);
        mPaint1.setStyle(Paint.Style.FILL_AND_STROKE);
        mMatrix = new Matrix();
        int[] color = {0x333333, 0xb0333333};
        mFolderShadowDrawableRL = new GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT, color);
        mFolderShadowDrawableRL
                .setGradientType(GradientDrawable.LINEAR_GRADIENT);
        ColorMatrix cm = new ColorMatrix();
        float array[] = {0.55f, 0, 0, 0, 80.0f, 0, 0.55f, 0, 0, 80.0f, 0, 0,
                0.55f, 0, 80.0f, 0, 0, 0, 0.2f, 0};
        cm.set(array);
        mColorMatrixFilter = new ColorMatrixColorFilter(cm);
    }

    private static final String TAG = "dami";

    @Override
    public void setBackgroundColor(int color) {
        Bitmap bitmap = Bitmap.createBitmap(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(bitmap);
        canvas.drawColor(color);
        backgroundBitmap = bitmap;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Log.d(TAG, "drawableToBitmap: " + drawable.getIntrinsicWidth());
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void setBackgroundResource(int resid) {
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), resid);
    }

    @Override
    public void setBackground(Drawable background) {
        if (background.getIntrinsicHeight() <= 0 || background.getIntrinsicWidth() <= 0) {
        } else {
            backgroundBitmap = drawableToBitmap(background);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        if (background.getIntrinsicHeight() <= 0 || background.getIntrinsicWidth() <= 0) {
        } else {
            backgroundBitmap = drawableToBitmap(background);
        }
    }

    boolean animing = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (animing) {
            return true;
        }
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
                endx = event.getX();
                endy = event.getY();
                animing = true;
                ValueAnimator valueAnimator = new ValueAnimator();
                valueAnimator.setDuration(800);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Float animatedValue = (Float) animation.getAnimatedValue();
                        if (endx < (getWidth() / 2)) {
                            Log.d(TAG, "onAnimationUpdate: ");
                            ax = ax + (-getWidth() - ax + 50) * animatedValue;
                            ay = ay + (getHeight() - ay - 50) * animatedValue;
                        } else {
                            Log.d(TAG, "onAnimationUpdate: wwww");
                            ax = ax + (getWidth() - ax) * animatedValue;
                            ay = ay + (getHeight() - ay) * animatedValue;
                        }
                        if (animatedValue < 1) {
                            invalidate();
                        } else {
                            animing = false;
                        }
                    }
                });
                valueAnimator.setFloatValues(0, 1);
                valueAnimator.start();
//                touch = false;
                break;
        }
        return true;
    }

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

    Path mPath0 = new Path();
    Path mPath1 = new Path();
    float[] mMatrixArray = {0, 0, 0, 0, 0, 0, 0, 0, 1.0f};
    float mMaxLength = (float) Math.hypot(480, 800);

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: :::::" + ax);
        if (bitmap1 == null) {
            bitmap1 = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            this.backCanvas = new Canvas();
        }
        backCanvas.setBitmap(bitmap1);
        mathPoints();
        if (touch) {
            mPath1.reset();
            mPath1.moveTo(jx, jy);
            mPath1.lineTo(ix, iy);
            mPath1.lineTo(dx, dy);
            mPath1.lineTo(cx, cy);
            mPath1.lineTo(fx, fy);
            mPath1.close();
            Log.d(TAG, "onDraw: ");
            mPath0.reset();
            mPath0.moveTo(jx, jy);
            mPath0.quadTo(hx, hy, kx, ky);
            mPath0.lineTo(ax, ay);
            mPath0.lineTo(bx, by);
            mPath0.quadTo(ex, ey, cx, cy);
            mPath0.lineTo(fx, fy);
            mPath0.close();
            Log.d(TAG, "onDraw: ");
//绘制下一页
            canvas.save();
            canvas.clipPath(mPath0);
            canvas.drawColor(Color.TRANSPARENT);
            canvas.restore();
//绘制当前页
            canvas.save();
            canvas.clipPath(mPath0, Region.Op.XOR);
            if (backgroundBitmap == null) {
                setBackgroundColor(Color.WHITE);
            }
            canvas.drawBitmap(backgroundBitmap, 0, 0, null);
            backCanvas.drawBitmap(backgroundBitmap, 0, 0, null);
            super.onDraw(canvas);
            super.onDraw(backCanvas);
            backCanvas.save();
            canvas.restore();
//绘制当前页的背面
            canvas.save();
            drawCurrentBackArea(canvas, bitmap1);
            canvas.restore();
            Log.d(TAG, "onDraw: ");
        } else {
            super.onDraw(canvas);
        }
    }

    private void drawCurrentBackArea(Canvas canvas, Bitmap bitmap) {
        int i = (int) (cx + ex) / 2;
        float f1 = Math.abs(i - ex);
        int i1 = (int) (jy + hy) / 2;
        float f2 = Math.abs(i1 - hy);
        float f3 = Math.min(f1, f2);
        mPath1.reset();
        mPath1.moveTo(ix, iy);
        mPath1.lineTo(dx, dy);
        mPath1.lineTo(bx, by);
        mPath1.lineTo(ax, ay);
        mPath1.lineTo(kx, ky);
        mPath1.close();
        GradientDrawable mFolderShadowDrawable;
        int left;
        int right;

        left = (int) (cx - f3 - 1);
        right = (int) (cx + 1);
        mFolderShadowDrawable = mFolderShadowDrawableRL;

        canvas.save();
        canvas.clipPath(mPath0);
        canvas.clipPath(mPath1, Region.Op.INTERSECT);

        mPaint.setColorFilter(mColorMatrixFilter);

        float dis = (float) Math.hypot(fx - ex,
                hy - fy);
        float f8 = (fx - ex) / dis;
        float f9 = (hy - fy) / dis;
        mMatrixArray[0] = 1 - 2 * f9 * f9;
        mMatrixArray[1] = 2 * f8 * f9;
        mMatrixArray[3] = mMatrixArray[1];
        mMatrixArray[4] = 1 - 2 * f8 * f8;
        mMatrix.reset();
        mMatrix.setValues(mMatrixArray);
        mMatrix.preTranslate(-ex, -ey);
        mMatrix.postTranslate(ex, ey);
        canvas.drawBitmap(bitmap, mMatrix, mPaint);
        mPaint.setColorFilter(null);
        float mDegrees = (float) Math.toDegrees(Math.atan2(ex
                - fx, hy - fy));
        canvas.rotate(mDegrees, cx, cy);
        mFolderShadowDrawable.setBounds(left, (int) cy, right,
                (int) (cy + mMaxLength * 2));
        mFolderShadowDrawable.draw(canvas);
        canvas.restore();
    }
}
