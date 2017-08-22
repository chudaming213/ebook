package com.onon.test.bibleapplication.beans;

import org.litepal.crud.DataSupport;

/**
 * Created by dami on 2017/8/21 0021.
 */

public class BibleBooks extends DataSupport {
    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public int getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(int chapterCount) {
        this.chapterCount = chapterCount;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getShortBookName() {
        return shortBookName;
    }

    public void setShortBookName(String shortBookName) {
        this.shortBookName = shortBookName;
    }

    public String getEnBookName() {
        return enBookName;
    }

    public void setEnBookName(String enBookName) {
        this.enBookName = enBookName;
    }

    public String getEnShortBookName() {
        return enShortBookName;
    }

    public void setEnShortBookName(String enShortBookName) {
        this.enShortBookName = enShortBookName;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }
    private int id;
    private int bookid;
    private int chapterCount;
    private int isNew;
    private String bookName;
    private String shortBookName;
    private String enBookName;
    private String enShortBookName;
    private String belongTo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
