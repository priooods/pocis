package com.kbs.pocis.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kbs.pocis.BuildConfig;
import com.kbs.pocis.R;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.onlinebooking.CallingData;
import com.kbs.pocis.welcome.Welcome_Screen;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_Menu extends Fragment {

    View view;
    LinearLayout menu_profile, logout, change_password;
    TextView version;
    ImageView icon_back;
    String versionApp = BuildConfig.VERSION_NAME;
    SharedPreferences sharedPreferences;
    String getVersionApp = "App Version " + versionApp;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_menu, container,false);

        icon_back = view.findViewById(R.id.icon_back);
        menu_profile = view.findViewById(R.id.go_profile);
        logout = view.findViewById(R.id.go_logout);
        version = view.findViewById(R.id.appversion);
        change_password = view.findViewById(R.id.go_password);
        version.setText(getVersionApp);
        sharedPreferences = requireActivity().getSharedPreferences("sesi", Context.MODE_PRIVATE);
        icon_back.setOnClickListener(v -> requireActivity().onBackPressed());
        logout.setOnClickListener(v -> LogoutClick());

        menu_profile.setOnClickListener(v-> {
            Fragment fragment;
            fragment = new Profile();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });

        change_password.setOnClickListener(v->{
            Fragment fragment;
            fragment = new Change_Password();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });

        return view;
    }

    public void LogoutClick(){
        Call<CallingData> call1 = UserData.i.getService().getLogoutUser(UserData.i.getToken());
        if (call1 == null) {
            Log.i("logout","CallingData Post Method is Bad!");
        }
        assert call1 != null;
        call1.enqueue(new Callback<CallingData>() {
            @Override
            public void onResponse(@NotNull Call<CallingData> call, @NotNull Response<CallingData> response) {

                CallingData.TreatResponse(getContext(), "logout", (CallingData) response.body());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                UserData.i.setToken("");
                pesan("Anda telah keluar");
                startActivity(new Intent(getActivity(), Welcome_Screen.class));
                requireActivity().finish();
            }

            @Override
            public void onFailure(@NotNull Call<CallingData> call, @NotNull Throwable t) {
                pesan("onFailure called login!");
                Log.i("login_test", "onFailure called!");
            }
        });
    }


    private void pesan(String pesan){
        Toast.makeText(getContext(), pesan, Toast.LENGTH_SHORT).show();
    }
}
