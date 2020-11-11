package com.kbs.pocis.model.createboking;

import android.net.Uri;

public class Model_UploadDocument {

    Uri uri;
    String username, size;

    public Model_UploadDocument() {
    }

    public Model_UploadDocument(Uri uri, String username, String size) {
        this.uri = uri;
        this.username = username;
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
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
