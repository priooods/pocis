package com.kbs.pocis.onlineboking;

import android.util.Log;

import java.util.ArrayList;

public class PageManager {
    public final int page_capacity;
    ArrayList<Page> pages;
    Page current;
    public boolean loaded;
    public int total;
    public int pack;
    public int page_last;

    public PageManager(int max) {
        page_capacity = max;
        if (pages != null)
            pages.clear();
        else
            pages = new ArrayList<>();
        loaded = false;
        pack = 0;
    }

    public class Page {
        int page_to, list_to;

        public Page(int page_to, int list_to) {
            this.page_to = page_to;
            this.list_to = list_to;
        }
    }

    public boolean addPack(int page, int list) {
        if (pack < page_capacity) {
            pack++;
            total++;
            return true;
        }
        finalPack(page, list);
        return false;
    }

    public void finalPack(int page, int list) {
        current = new Page(page, list);
        pages.add(current);
        pack = 0;
    }
    public int getPage(int page){
        if (page>1){
            page-=2;
            if (page<pages.size())
                return pages.get(page).page_to;
        }
        return 1;
    }
    public int getList(int page){
        if (page>1){
            page-=2;
            if (page<pages.size())
                return pages.get(page).list_to;
        }
        return 0;
    }
    public void finishLoad(){
        loaded = true;
        page_last = (int)Math.ceil((float)total/page_capacity);
        Log.i("booking_load","Total = "+total+"/"+page_capacity+" Return "+((float)total/page_capacity)+" Ceil 0.4= "+Math.ceil((float)total/page_capacity)+" Round 0.4= "+Math.round((float)total/page_capacity)+" Floor 0.4= "+Math.floor((float)total/page_capacity));
    }
}