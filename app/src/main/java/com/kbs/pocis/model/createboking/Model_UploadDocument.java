package com.kbs.pocis.model.createboking;

import android.net.Uri;
import android.util.Log;

import java.io.File;

public class Model_UploadDocument {

    public File uri;
    public String username;
    public int size;

    public Model_UploadDocument(File uri, String username, int size) {
        this.uri = uri;
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

    public File getUri() {
        return uri;
    }

    public void setUri(File uri) {
        this.uri = uri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
