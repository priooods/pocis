package com.kbs.pocis.model.createboking;

import android.net.Uri;
import android.util.Log;

import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.BookingDetailData;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;

public class Model_UploadDocument {

    public File uri;
    public String username;
    public int id;
    public String m_document_id, description, path;
    public int size;

    public Model_UploadDocument(File uri, String username, int size) {
        this.uri = uri;
        Log.i("file","Load file model : "+username);
        this.username = username;
        this.size = size;
    }

    public void Update(File uri, String username, int size){
        this.uri = uri;
//        this.description = titles;
        Log.i("file","Load file model : "+username);
        this.username = username;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void getString(){
        Log.i("model_upload_document", "Uri = "+(uri == null ? "NULL ":uri.getName())+", description = "+description);
    }

    public RequestBody input_form(String req) {
        return RequestBody.create(req, MediaType.parse("multipart/form-data"));
    }

    public void getMap(HashMap<String, RequestBody> map, int i) {
        map.put("BookingDocument[m_document_id][" + i + "]", input_form(m_document_id));
        map.put("BookingDocument[path][" + i + "]", input_form(path));
        Log.i(TAG, "new data document: " + map + " size " + i);
    }
}
