package com.kbs.pocis.service.PublicList;

import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.service.Calling;

public class CallProjectList extends Calling {
    public Datas data;

    public static class Datas {
        public PublicList.Datas Open,Close,All;
    }
}
