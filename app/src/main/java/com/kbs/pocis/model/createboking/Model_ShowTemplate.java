package com.kbs.pocis.model.createboking;


import java.util.List;

public class Model_ShowTemplate {

    String id, name;
    int img;
    public List<Model_SelectTemplate> list;
    boolean Check;

    public Model_ShowTemplate() {
    }

    public Model_ShowTemplate(String id, String name, int img) {
        this.id = id;
        this.name = name;
        this.img = img;
        list = null;
    }
    public Model_ShowTemplate(String id, String name, int img, List<Model_SelectTemplate> temp) {
        this.id = id;
        this.name = name;
        this.img = img;
        list = temp;
    }

    public void setCheck(boolean check) {
        Check = check;
    }
    public boolean getCheck(){
        return Check;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
