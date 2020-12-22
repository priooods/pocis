package com.kbs.pocis.service.PublicList;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.service.Calling;

public class PublicList extends Calling {
    public Datas data;

    public static class Datas {
        @SerializedName("current_page")
        public int current_page;
        @SerializedName("data")
        public Model_Project[] model;
        @SerializedName("from")
        public int from_page;
        @SerializedName("to")
        public int to_page;
        public int last_page, per_page, total;
    }
}
