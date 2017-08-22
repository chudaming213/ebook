package com.onon.test.bibleapplication;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.onon.test.bibleapplication.beans.BibleBooks;
import com.onon.test.bibleapplication.beans.BibleChapters;
import com.onon.test.bibleapplication.beans.BibleVerses;

import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by dami on 2017/8/21 0021.
 */

public class MyApplication extends LitePalApplication {
    public static boolean HAS_COPY_DB;
    private File dir;
    private String DATABASE_NAME = "bible_zhiban.db";
    private File dest;
    private File saveDir;
    Handler endcopyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent("GETDATA");
            MyApplication.HAS_COPY_DB = true;
            sendBroadcast(intent);
            List<BibleBooks> all = DataSupport.findAll(BibleBooks.class);
            for (BibleBooks bibleBooks : all) {
                Log.d("dami", "handleMessage: " + bibleBooks.getBookName());
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        // 拷贝数据库
        Thread copyDatabaseThread = new Thread(new Runnable() {
            @Override
            public void run() {
                copyDB();
            }
        });
        String pkName = this.getPackageName();
        String DATABASES_DIR = "/data/data/" + pkName + "/databases";
        dir = new File(DATABASES_DIR);
        // 判断数据库是否需要拷贝
        dest = new File(dir, DATABASE_NAME);
        if (dest.exists()) {
            Log.d("dami", "copyDB: ");
            MyApplication.HAS_COPY_DB = true;
            return;
        } else {
            copyDatabaseThread.start();
        }
        if (HAS_COPY_DB) {
//            List<BibleBooks> all = BibleBooks.findAll(BibleBooks.class);
//            List<BibleChapters> all1 = DataSupport.findAll(BibleChapters.class);
//        for (BibleBooks bibleBooks : all) {
//            Log.d("dami", "handleMessage: " + bibleBooks.getBookid());
//        }
//        for (int i = 0; i < 5; i++) {
//            Log.d("dami", "onCreate: " + all1.get(i).getFormattext());
//        }
        }


    }

    // 拷贝数据库
    private void copyDB() {
        if (!dir.exists()) {
            try {
                dir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            if (dest.exists()) {
                Log.d("dami", "copyDB: ");
                MyApplication.HAS_COPY_DB = true;
                return;
            }
            dest.createNewFile();
            InputStream in = getClass().getResourceAsStream(
                    "/assets/bible_zhiban.db");
            FileOutputStream out = new FileOutputStream(dest);
            int size = 1024;
            byte buf[] = new byte[size];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            endcopyHandler.sendEmptyMessage(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
