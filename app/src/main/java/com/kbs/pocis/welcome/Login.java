package com.kbs.pocis.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.activity.HomePage;

import es.dmoral.toasty.Toasty;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;

import com.kbs.pocis.service.UserData;
import com.kbs.pocis.api.UserService;
import com.kbs.pocis.service.onlinebooking.CallingData;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class Login extends Fragment {

    TextInputEditText username, password;
    SharedPreferences sharedPreferences;
    UserService callInterface;
    UserData user;
    TextView forgot_password;

    View view;

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_screen, container, false);

        username = view.findViewById(R.id.input_username_login);
        password = view.findViewById(R.id.input_password_login);
        forgot_password = view.findViewById(R.id.forgot_password);
        sharedPreferences = requireActivity().getSharedPreferences("sesi", Context.MODE_PRIVATE);


        //Lupa password di click ke Page Forgot Password
        forgot_password.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new Forgot_Password();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.replace(R.id.frameAuth, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });
        Button btn_login = view.findViewById(R.id.btn_login);
//        {
//            user = new UserData("BIG", "BIG");
//            callInterface = user.getService();
//            if (callInterface == null) {
//                pesan("Failed to Connect! Please Enable Internet");
//            } else {
//                Log.i("login", "Success to Connect!");
//            }
//            loginRetrofit2Api();
//        }
        //onClick Button Login
        btn_login.setOnClickListener(v -> {
            user = new UserData(Objects.requireNonNull(username.getText()).toString(), Objects.requireNonNull(password.getText()).toString());
            callInterface = user.getService();
            if (callInterface == null) {
                pesan("Failed to Connect! Please Enable Internet");
                return;
            } else {
                Log.i("login", "Success to Connect!");
            }
            loginRetrofit2Api();
        });

        return view;
    }

    //Function Login
    public void loginRetrofit2Api() {
        Call<CallingData> call1 = callInterface.getUserLogin(user.username, user.password);
        if (call1 == null) {
            Log.i("login", "CallingData Post Method is Bad!");
        }
        assert call1 != null;
        call1.enqueue(new Callback<CallingData>() {
            @Override
            public void onResponse(@NotNull Call<CallingData> call, @NotNull Response<CallingData> response) {
                CallingData respone = (CallingData) response.body();
                if (CallingData.TreatResponse(requireContext(), "login", respone)) {
                    pesanSuccess("Welcome " + user.username);
                    assert respone != null;
//                    user.setToken(respone.data.token);
//                    user.setCustId(respone.data.cust_id);
                    UserData.i = user;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", respone.data.token);
                    editor.putString("name", user.username);
                    editor.putString("cust", respone.data.cust_id);
                    editor.apply();
                    startActivity(new Intent(requireActivity(), HomePage.class));
                    requireActivity().overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);
                    requireActivity().finish();
                } else {
                    assert respone != null;
                    pesan(respone.desc);
                    Log.e("login", "Failed : \n Error " + respone.error + " : " + respone.desc);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingData> call, @NotNull Throwable t) {
                pesan("onFailure called login!");
                Log.i("login_test", "onFailure called!");
            }
        });
    }

    private void pesan(String pesan) {
        Toasty.error(requireContext(), pesan, Toast.LENGTH_SHORT, true).show();
    }

    private void pesanSuccess(String pesan) {
        Toast.makeText(requireContext(), pesan, Toast.LENGTH_SHORT).show();
    }
}