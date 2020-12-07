package com.kbs.pocis.service.createbooking;
import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.createboking.Model_SelectTemplate;
import com.kbs.pocis.service.Calling;

import java.util.ArrayList;

public class CallingSelectTemp extends Calling {
    public SelTemp data;

    public class SelTemp {
        @SerializedName("temp_detail")
        public ArrayList<Model_SelectTemplate> list;
        public ArrayList<TempDocument> temp_document;
    }

    public class TempDocument {
        int id;
        String m_document_id, description, path;
    }
}
