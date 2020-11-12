package com.kbs.pocis.item;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.kbs.pocis.R;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.api.APIClient;
import com.kbs.pocis.service.SessionManager;
import com.kbs.pocis.service.UserService;
import com.kbs.pocis.service.response.CallingDataLogin;
import com.kbs.pocis.service.response.LogoutResponse;
import com.kbs.pocis.welcome.Login;
import com.kbs.pocis.welcome.Welcome_Screen;

import java.io.Serializable;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Popup_Profile extends DialogFragment {

    TextView username;
    Button buttonLogout;
    String  usernameUser;
    CallingDataLogin tokens;
    UserService userService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.dialog_profile_box, container, false);

        tokens = (CallingDataLogin)getActivity().getIntent().getParcelableExtra("token");
        Log.i("TAG", "onCreateView: " + tokens.data.token);
//        usernameUser = getActivity().getIntent().getStringExtra("username");

        username = view.findViewById(R.id.username_profile);
        buttonLogout = view.findViewById(R.id.btn_logout_profile);
        username.setText(usernameUser);

        userService = APIClient.getClient().create(UserService.class);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutResponse logoutResponse = new LogoutResponse(tokens.data.token);
                LogoutClick(logoutResponse);
            }
        });

        return view;

    }

    public void LogoutClick(LogoutResponse logoutResponse){
        if (logoutResponse == null) {
            Log.i("logout","Data is NULL!");
            return;
        }else{
            Log.i("logout","Data is not NULL!");
        }
        Call<LogoutResponse> call1 = userService.getLogoutUser(tokens.data.token);
        if (call1 == null) {
            Log.i("logout","CallingData Post Method is Bad!");
        }
        call1.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                LogoutResponse response1 = response.body();
                Log.i("logout", "logoutResponse 1 --> " + response1);
                if (response1 != null) {
                    Log.i("logout", "Error          -->  " + response1.error);
                    Log.i("logout", "Description       -->  " + response1.desc);

                    String responseCode = response1.error;
                    if (responseCode.equals("403")) {
//                        Log.i("login", "Token        -->  " + loginResponse.data.token);
                            pesanSuccess("Anda telah keluar");
//                            SessionManager.setLoggedIn(getContext(), false);
                            startActivity(new Intent(getActivity(), Welcome_Screen.class));
                            getActivity().finish();
                    } else {
                        pesan(response1.desc);
                        Log.e("login","Failed : \n Error "+response1.error+" : "+response1.desc);
                    }
                } else {
                    Log.i("logout", "onResponseError: " + "POST Logout gagal");
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                pesan("onFailure called login!");
                Log.i("login_test", "onFailure called!");
            }
        });
    }

    private void pesan(String pesan){
        Toasty.error(getContext(), pesan, Toast.LENGTH_LONG, true).show();
    }

    private void pesanSuccess(String pesan){
        Toast.makeText(getContext(), pesan, Toast.LENGTH_SHORT).show();
    }

}
