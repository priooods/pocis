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
import com.kbs.pocis.R;
import com.kbs.pocis.activity.HomePage;

// Function dibawah untuk CRUD
import es.dmoral.toasty.Toasty;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

import com.kbs.pocis.service.SessionManager;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.api.UserService;
import com.kbs.pocis.service.onlinebooking.CallingData;

public class Login extends AppCompatActivity {

    TextInputEditText username, password;
    UserService callInterface;
    UserData user;
    SessionManager sessionManager;

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

        sessionManager = new SessionManager(this);
        username = findViewById(R.id.input_username_login);
        password = findViewById(R.id.input_password_login);
        Button btn_login = findViewById(R.id.btn_login);
        {
            user = new UserData("BIG", "BIG");
            callInterface = user.getService();
            if (callInterface == null) {
                pesan("Failed to Connect! Please Enable Internet");
                return;
            } else {
                Log.i("login", "Success to Connect!");
            }
            loginRetrofit2Api();
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new UserData(username.getText().toString(), password.getText().toString());
                callInterface = user.getService();
                if (callInterface == null) {
                    pesan("Failed to Connect! Please Enable Internet");
                    return;
                } else {
                    Log.i("login", "Success to Connect!");
                }
                loginRetrofit2Api();
            }
        });
    }

    public void loginRetrofit2Api() {
        Call<CallingData> call1 = callInterface.getUserLogin(user.username, user.password);
        if (call1 == null) {
            Log.i("login", "CallingData Post Method is Bad!");
        }
        call1.enqueue(new Callback<CallingData>() {
            @Override
            public void onResponse(Call<CallingData> call, Response<CallingData> response) {
                CallingData respone = (CallingData) response.body();
                if (CallingData.TreatResponse(Login.this, "login", respone)) {
                    pesanSuccess("Welcome " + user.username);
                    user.setToken(respone.data.token);
                    UserData.i = user;
                    startActivity(new Intent(Login.this, HomePage.class));
                    finish();
                } else {
                    pesan(respone.desc);
                    Log.e("login", "Failed : \n Error " + respone.error + " : " + respone.desc);
                }
            }

            @Override
            public void onFailure(Call<CallingData> call, Throwable t) {
                pesan("onFailure called login!");
                Log.i("login_test", "onFailure called!");
            }
        });
    }

    private void pesan(String pesan) {
        Toasty.error(this, pesan, Toast.LENGTH_SHORT, true).show();
    }


    private void pesanSuccess(String pesan) {
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show();
    }
}