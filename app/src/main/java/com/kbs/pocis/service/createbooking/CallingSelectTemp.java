package com.kbs.pocis.service.createbooking;
import com.google.gson.annotations.SerializedName;
import com.kbs.pocis.model.createboking.Model_SelectTemplate;
import com.kbs.pocis.model.createboking.Model_UploadDocument;
import com.kbs.pocis.service.Calling;

import java.util.ArrayList;
import java.util.List;

public class CallingSelectTemp extends Calling {
    public SelTemp data;

    public class SelTemp {
        @SerializedName("temp_detail")
        public ArrayList<Model_SelectTemplate> list;
        public ArrayList<Model_UploadDocument> temp_document;
    }

    public class TempDocument {
        int id;
        String m_document_id, description, path;
    }
}
