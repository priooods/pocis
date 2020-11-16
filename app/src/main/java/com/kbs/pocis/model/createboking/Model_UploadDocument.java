package com.kbs.pocis.model.createboking;

import android.net.Uri;
import android.util.Log;

public class Model_UploadDocument {

    Uri uri;
    String username;
    int size;

    public Model_UploadDocument(Uri uri, String username, int size) {
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

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
