package com.kbs.pocis.welcome;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.api.ApiClient;
import com.kbs.pocis.api.LoginResponse;
import com.kbs.pocis.model.Model_User;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    TextInputEditText username, password;
    Model_User model_user;

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
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model_user = new Model_User();
                model_user.setUsername_model(username.getText().toString());
                model_user.setPassword_model(password.getText().toString());
                //Kalau kosong form nya muncul peringatan.
                if (model_user.getUsername_model().isEmpty() || model_user.getPassword_model().isEmpty()){
                    pesan("Harap Lengkapi Username dan Password");
                } else {
                    LoginUser();
                }
            }
        });


    }

    private void pesan(String pesan){
        Toasty.error(this, pesan, Toast.LENGTH_LONG, true).show();
    }

    public void LoginUser(){
        Model_User model_users = new Model_User();
        model_users.setUsername_model(username.getText().toString());
        model_users.setPassword_model(password.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserServices().UserLogin(model_users);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(Login.this, response.message(), Toast.LENGTH_LONG).show();

                } else {
                    pesan("Login gagal");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, "Trouble " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
