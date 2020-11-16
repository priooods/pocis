package com.kbs.pocis.model.createboking;

import java.util.List;

public class Model_Summary_ServiceInfo_Title {

    String code, title;
    public List<Model_Summary_ServiceInfoList> lists;


    public Model_Summary_ServiceInfo_Title(String code, String title, List<Model_Summary_ServiceInfoList> lists) {
        this.code = code;
        this.title = title;
        this.lists = lists;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
