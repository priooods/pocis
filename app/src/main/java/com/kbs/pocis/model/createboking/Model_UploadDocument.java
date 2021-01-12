package com.kbs.pocis.model.createboking;

import android.net.Uri;
import android.util.Log;

import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.BookingDetailData;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Model_UploadDocument {

//    public static Model_UploadDocument i;
//    public static boolean isExist(){
//        if (Model_UploadDocument.i == null){
//            return false;
//        }
//        return true;
//    }

    public static List<Model_UploadDocument> model_uploadDocuments;

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
        Log.e("model_upload_document", "Uri ="+(uri == null ? "NULL":uri.getName())+", description="+description);
    }
}
