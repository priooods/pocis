package com.kbs.pocis.model;

public class Model_News {

    public static Model_News mn;
    public static int Code;
    public static boolean isExist(){
        if (Model_News.mn == null){
            return false;
        }
        return true;
    }

    public String image_url, title, dates, desc;
    public int id;

    public Model_News(String image_url, String title, String dates, String desc, int id) {
        this.image_url = image_url;
        this.title = title;
        this.dates = dates;
        this.desc = desc;
        this.id = id;
    }
}
