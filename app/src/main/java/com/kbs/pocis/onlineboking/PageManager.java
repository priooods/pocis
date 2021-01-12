package com.kbs.pocis.onlineboking;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

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

        //pages.add(current = new Page());
        current = new Page();
        page_last = -1;
        loaded = false;
        pack = 0;
    }

    public static class Page {
        ArrayList<Integer> page = new ArrayList<>();
        int list_from;
        public void setPage(int pages) {
            page.add(pages);
        }
        public void setPage(int pages, int lists){
            setPage(pages);
            list_from = lists;
        }
        @NotNull
        @Override
        public String toString(){
            return list_from+"|"+page.toString();
        }
    }
    //region Gunakan Function ini ketika membuat PageManager
    public boolean addPack(int page, int list) {
        if (pack < page_capacity){
            if (page_last!=page) {
                if (pack == 0)
                    current.setPage(page, list);
                else
                    current.setPage(page);
                page_last = page;
            }
            pack++;
            total++;
            return true;
        }else{
            preparePack();
            addPack(page,list);
            return false;
        }
    }

    public void preparePack() {
        pages.add(current);
        Log.e("filter","preparePack = "+current.toString());
        current = new Page();
        pack = 0;
    }
    public void finishLoad(){
        if (pack > 0){
            pages.add(current);
            pack = 0;
        }
        loaded = true;
        page_last = (int)Math.ceil((float)total/page_capacity);
        Log.i("booking_load","Total = "+total+"/"+page_capacity+" Return "+page_last+" Total Pages = "+pages.size());
    }
    //endregion
    //region Gunakan Function ini ketika menggunakan PageManager
    public void getCallPage(int page){
        current = pages.get(page - 1);
        pack = 0;
    }
    public int getPage(){
        return current.page.get(pack);
    }
    public int getList(){
        return current.list_from;
    }
    public int getNextPage(){
        pack++;
        return current.page.get(pack);
    }
    public boolean getLastPage() {
        return pack + 1 < current.page.size();
    }
    //endregion
}