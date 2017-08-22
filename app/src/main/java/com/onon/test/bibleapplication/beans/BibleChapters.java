package com.onon.test.bibleapplication.beans;

import org.litepal.crud.DataSupport;

/**
 * Created by dami on 2017/8/21 0021.
 */

public class BibleChapters extends DataSupport {
    private int id;

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapterid() {
        return chapterid;
    }

    public void setChapterid(int chapterid) {
        this.chapterid = chapterid;
    }

    public int getOsisid() {
        return osisid;
    }

    public void setOsisid(int osisid) {
        this.osisid = osisid;
    }

    public int getVerseCount() {
        return verseCount;
    }

    public void setVerseCount(int verseCount) {
        this.verseCount = verseCount;
    }

    public String getFormattext() {
        return formattext;
    }

    public void setFormattext(String formattext) {
        this.formattext = formattext;
    }

    public String getEnformattext() {
        return enformattext;
    }

    public void setEnformattext(String enformattext) {
        this.enformattext = enformattext;
    }

    private int bookid;
    private int chapterid;
    private int osisid;
    private int verseCount;
    private String formattext;
    private String enformattext;
}
