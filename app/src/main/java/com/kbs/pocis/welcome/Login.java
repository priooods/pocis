package com.kbs.pocis.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import com.kbs.pocis.api.APIClient;
import com.kbs.pocis.service.SessionManager;
import com.kbs.pocis.service.response.CallingDataLogin;
import com.kbs.pocis.service.UserService;

public class Login extends AppCompatActivity {

    TextInputEditText username, password;
    UserService callInterface;

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

        callInterface = APIClient.getClient().create(UserService.class);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callInterface == null){
                    pesan("Failed to Connect! Please Enable Internet");
                    return;
                }else {
                    Log.i("login","Success to Connect!");
                }
                CallingDataLogin data = new CallingDataLogin(username.getText().toString(), password.getText().toString());
                loginRetrofit2Api(data);
            }
        });
    }

    public void loginRetrofit2Api(CallingDataLogin data) {
        if (data == null) {
            Log.i("login","Data is NULL!");
            return;
        }else{
            Log.i("login","Data is not NULL!");
        }
        Call<CallingDataLogin> call1 = callInterface.getUserLogin(data.username,data.password);
        if (call1 == null) {
            Log.i("login","CallingData Post Method is Bad!");
        }
        call1.enqueue(new Callback<CallingDataLogin>() {
            @Override
            public void onResponse(Call<CallingDataLogin> call, Response<CallingDataLogin> response) {
                CallingDataLogin loginResponse = response.body();

                Log.i("login", "loginResponse 1 --> " + loginResponse);
                if (loginResponse != null) {
                    Log.i("login", "Error          -->  " + loginResponse.error);
                    Log.i("login", "Description       -->  " + loginResponse.desc);

                    String responseCode = loginResponse.error;
                    if (responseCode.equals("0")) {
                        Log.i("login", "Token        -->  " + loginResponse.data.token);
                        pesanSuccess("Welcome " + username.getText().toString());


                        SessionManager.setLoggedIn(Login.this, loginResponse.data.token);

                        Intent intent = new Intent(Login.this, HomePage.class);
                        intent.putExtra("token", loginResponse);
//                        intent.putExtra("username", username.getText().toString());
                        startActivity(intent);
                        finish();
                    } else {
                        pesan(loginResponse.desc);
                        Log.e("login","Failed : \n Error "+loginResponse.error+" : "+loginResponse.desc);
                    }
                }
            }

            @Override
            public void onFailure(Call<CallingDataLogin> call, Throwable t) {
                pesan("onFailure called login!");
                Log.i("login_test", "onFailure called!");
            }
        });
    }

    private void pesan(String pesan){
        Toasty.error(this, pesan, Toast.LENGTH_SHORT, true).show();
    }

    private void pesanSuccess(String pesan){
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show();
    }
}
