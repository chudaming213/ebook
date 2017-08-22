package com.onon.test.bibleapplication;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by dami on 2017/8/18 0018.
 */

public class MyMethord extends LinkMovementMethod {
    private static MyMethord sInstance;

    public static MyMethord getInstance() {
        if (sInstance == null)
            sInstance = new MyMethord();
        return sInstance;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            MyClickableSpan[] link = buffer.getSpans(off, off, MyClickableSpan.class);

            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(widget, link[0].getTag());
                } else if (action == MotionEvent.ACTION_DOWN) {

                }
                return true;
            } else {
                Selection.removeSelection(buffer);
            }
        }
        return super.onTouchEvent(widget, buffer, event);
    }

    @Override
    protected boolean down(TextView widget, Spannable buffer) {
        return true;
    }

    @Override
    protected boolean up(TextView widget, Spannable buffer) {
        return true;
    }
}
