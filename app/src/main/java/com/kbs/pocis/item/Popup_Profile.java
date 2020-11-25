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
import com.kbs.pocis.api.APIClient;
import com.kbs.pocis.service.onlinebooking.CallingData;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.api.UserService;
import com.kbs.pocis.welcome.Welcome_Screen;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Popup_Profile extends DialogFragment {

    TextView username;
    Button buttonLogout;
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

        UserData user = UserData.i;
        username = view.findViewById(R.id.username_profile);
        buttonLogout = view.findViewById(R.id.btn_logout_profile);
        username.setText(user.username);

        userService = APIClient.getClient().create(UserService.class);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutClick();

            }
        });
        username.setText(user.username);

        return view;

    }

    public void LogoutClick(){
        UserData user = UserData.i;
        Call<CallingData> call1 = userService.getLogoutUser(user.getToken());
        if (call1 == null) {
            Log.i("logout","CallingData Post Method is Bad!");
        }
        call1.enqueue(new Callback<CallingData>() {
            @Override
            public void onResponse(Call<CallingData> call, Response<CallingData> response) {

                CallingData.TreatResponse(getContext(), "logout", (CallingData) response.body());
                user.setToken("");
                pesanSuccess("Anda telah keluar");
                startActivity(new Intent(getActivity(), Welcome_Screen.class));
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<CallingData> call, Throwable t) {
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
