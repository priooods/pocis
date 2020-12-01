package com.kbs.pocis.model.createboking;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Model_ShowTemplate {

    String id;
    String img,name;
    public ArrayList<Model_SelectTemplate> list;
    boolean Check;

    public boolean OneChecked(){
        for(Model_SelectTemplate temp:list){
            if (temp.checked){
                return true;
            }
        }
        return false;
    }

    public Model_ShowTemplate(String id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
        list = null;
    }
    public Model_ShowTemplate(String id, String name, String img, ArrayList<Model_SelectTemplate> temp ) {
        this.id = id;
        this.name = name;
        this.img = img;
        Check = true;
        list = temp;
    }

    public ArrayList<Model_SelectTemplate> getList() {
        return list;
    }

    public void setList(ArrayList<Model_SelectTemplate> list) {
        this.list = list;
    }

    public boolean isCheck() {
        return Check;
    }

    public void setCheck(boolean check) {
        Check = check;
    }
    public boolean getCheck(){
        return Check;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
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
