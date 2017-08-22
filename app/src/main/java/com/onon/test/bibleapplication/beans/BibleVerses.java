package com.onon.test.bibleapplication.beans;

import org.litepal.crud.DataSupport;

/**
 * Created by dami on 2017/8/21 0021.
 */

public class BibleVerses  extends DataSupport {
    private int id;
    private int bookid;
    private int chapterid;
    private int verseid;
    private String text;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    private boolean selected;
    private boolean underline;
    public String getEntext() {
        return entext;
    }

    public void setEntext(String entext) {
        this.entext = entext;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public int getChapterid() {
        return chapterid;
    }

    public void setChapterid(int chapterid) {
        this.chapterid = chapterid;
    }

    public int getVerseid() {
        return verseid;
    }

    public void setVerseid(int verseid) {
        this.verseid = verseid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private String entext;
}
