package com.onon.test.bibleapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.onon.test.bibleapplication.beans.BibleBooks;
import com.onon.test.bibleapplication.beans.BibleVerses;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "dami";
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<BibleBooks> all = DataSupport.findAll(BibleBooks.class);
            for (BibleBooks bibleBooks : all) {
                Log.d("dami", "handleMessage: " + bibleBooks.getBookName());
            }
        }
    };


    Button button;
    BibleTextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(broadcastReceiver, new IntentFilter("GETDATA"));
        title = (BibleTextView) findViewById(R.id.title);
        button = (Button) findViewById(R.id.button);
        if (MyApplication.HAS_COPY_DB) {
            ArrayList<BibleVerses> all2 = (ArrayList<BibleVerses>) DataSupport.where("bookid = ? and chapterid = ?", "1", "2").find(BibleVerses.class);
            for (int i = 0; i < all2.size(); i++) {
                all2.get(i).setText((i + 1) + all2.get(i).getText());
            }
            title.setContent(all2);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main2Activity.start(MainActivity.this);
//                ArrayList<BibleVerses> underline = title.getUnderline();
//                if (((Button) v).getText().equals("高亮")) {
//                    title.highlight(underline);
//                } else {
//                    title.unHighlight(underline);
//                }
//                title.setItemClickable(false);
//                v.setVisibility(View.GONE);
            }
        });

        title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                title.setItemClickable(true);
                return false;
            }
        });
        title.setItemClickListener(new BibleTextView.onItemClickListener() {
            @Override
            public void OnClickListener(View view, int position, boolean selected) {
                ArrayList<BibleVerses> underline = title.getUnderline();
                boolean contentsUnselected = false;
                for (BibleVerses bibleVerses : underline) {
                    Log.d(TAG, "OnClickListener: " + bibleVerses.isSelected());
                    if (!bibleVerses.isSelected()) {
                        contentsUnselected = true;
                        break;
                    }
                }
                button.setText(contentsUnselected ? "高亮" : "清除");
                if (button.getVisibility() != View.VISIBLE) {
                    button.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
