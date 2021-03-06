package com.kbs.pocis.service.PublicList;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.Model_Monitoring;
import com.kbs.pocis.onlineboking.Filters;
import com.kbs.pocis.service.Calling;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Loading_Unloading extends Calling {
    public Datas data;

    public static class Datas {
        @SerializedName("current_page")
        public int current_page;
        @SerializedName("data")
        public ArrayList<Model_Monitoring> model;
        @SerializedName("from")
        public int from_page;
        @SerializedName("to")
        public int to_page;
        public int last_page, per_page, total;

        public Datas setUpFilter(Filters filter, Datas model) {
            current_page = model.current_page;
            ArrayList<Model_Monitoring> output = new ArrayList<>();
            for (Model_Monitoring data : model.model) {
                if (filter.checkFilter(data)) {
                    output.add(data);
                }
            }
            model.model.clear();
            model.model = output;
            return this;
        }
    }
}
