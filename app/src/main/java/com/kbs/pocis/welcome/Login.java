package com.kbs.pocis.welcome;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.internal.$Gson$Types;
import com.kbs.pocis.R;
import com.kbs.pocis.activity.HomePage;

// Function dibawah untuk CRUD
import es.dmoral.toasty.Toasty;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import com.kbs.pocis.model.CallingData;

interface CallTest {
    @FormUrlEncoded
    @POST("auth/login")
    Call<CallingData> getUserLogin(
            @Field("username") String id,
            @Field("password") String secret
    );

    //FooResponse postJson(@Body FooRequest body);
}

public class Login extends AppCompatActivity {

    TextInputEditText username, password;
    CallTest callInterface;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        //Karena App ini akan support untuk semua versi OS android.
        //Dibawah ini function untuk mengatur warna back and icon dari status Bar nya ( ini Light Mode )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite, this.getTheme()));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }

        username = findViewById(R.id.input_username_login);
        password = findViewById(R.id.input_password_login);
        Button btn_login = findViewById(R.id.btn_login);

        callInterface = APIClient.getClient().create(CallTest.class);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callInterface == null){
                    pesan("Failed to Connect! Please Enable Internet");
                    return;
                }else {
                    Log.i("login","Success to Connect!");
                }
                CallingData data = new CallingData(username.getText().toString(), password.getText().toString());
                //if (data.username.isEmpty() || data.password.isEmpty()){
                //    pesan("Harap Lengkapi Username dan Password");
                //} else {
                    loginRetrofit2Api(data);
                //}
            }
        });
    }

    private void loginRetrofit2Api(CallingData data) {
        if (data == null) {
            Log.i("login","Data is NULL!");
            return;
        }else{
            Log.i("login","Data is not NULL!");
        }
        Call<CallingData> call1 = callInterface.getUserLogin(data.username,data.password);
        if (call1 == null) {
            Log.i("login","CallingData Post Method is Bad!");
        }
        call1.enqueue(new Callback<CallingData>() {
            @Override
            public void onResponse(Call<CallingData> call, Response<CallingData> response) {
                CallingData loginResponse = response.body();

                Log.i("login_test", "loginResponse 1 --> " + loginResponse);
                if (loginResponse != null) {
                    Log.i("login", "Error          -->  " + loginResponse.error);
                    Log.i("login", "Description       -->  " + loginResponse.desc);

                    String responseCode = loginResponse.error;
                    if (responseCode.equals("0")) {
                        pesan("Welcome "+username.getText().toString());
                        Log.i("login", "Token        -->  " + loginResponse.data.token);
                        Intent intent = new Intent(Login.this, HomePage.class);
                        startActivity(intent);
                        finish();
                    } else {
                        pesan(loginResponse.desc);
                        Log.e("login","Failed : \n Error "+loginResponse.error+" : "+loginResponse.desc);

                        //pesan("Login anda gagal");
                    }
                }
            }

            @Override
            public void onFailure(Call<CallingData> call, Throwable t) {
                pesan("onFailure called!");
                Log.i("login_test", "onFailure called!");
                //.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                //call.cancel();
            }
        });
    }
    private void pesan(String pesan){
        Toasty.error(this, pesan, Toast.LENGTH_LONG, true).show();
    }
}
