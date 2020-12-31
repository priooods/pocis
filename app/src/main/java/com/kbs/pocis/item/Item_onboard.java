package com.kbs.pocis.item;

public class Item_onboard {

    String top, mid, bot;
    int img;

    public Item_onboard(String top, String mid, String bot, int img) {
        this.top = top;
        this.mid = mid;
        this.bot = bot;
        this.img = img;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getMid() {
        return mid;
    }

    public String getBot() {
        return bot;
    }

    public int getImg() {
        return img;
    }
}
