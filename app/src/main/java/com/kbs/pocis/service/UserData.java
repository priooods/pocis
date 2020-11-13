package com.kbs.pocis.service;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.kbs.pocis.api.APIClient;

public class UserData implements Parcelable {
    public String username;
    public String password;
    private String token;
    private UserService service;
    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public UserService getService(){
        if (service == null){
            service = APIClient.getClient().create(UserService.class);
        }
        return service;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        Log.i("token","Token Updated to : "+token);
        this.token = token;
    }

    protected UserData(Parcel in) {
        username = in.readString();
        password = in.readString();
        token = in.readString();
    }
    //region Parceable Things
    public static final Parcelable.Creator<UserData> CREATOR = new Parcelable.Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(token);
    }
    //endregion
}
