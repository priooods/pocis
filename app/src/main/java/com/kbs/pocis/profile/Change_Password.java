package com.kbs.pocis.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.welcome.Auth;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Change_Password extends Fragment {

    View view;
    ImageView icon_back;
    TextInputEditText password,confirm_password, old_password;
    Button cancel, save;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.password_change, container, false);

        password = view.findViewById(R.id.password);
        old_password = view.findViewById(R.id.old_password);
        icon_back = view.findViewById(R.id.icon_back);
        confirm_password = view.findViewById(R.id.confirm_password);
        sharedPreferences = requireActivity().getSharedPreferences("sesi", Context.MODE_PRIVATE);
        cancel = view.findViewById(R.id.btn_cancel);
        old_password.setText(UserData.i.password);
        save = view.findViewById(R.id.btn_save);
        cancel.setOnClickListener(v->requireActivity().onBackPressed());

        icon_back.setOnClickListener(v->requireActivity().onBackPressed());
        save.setOnClickListener(v->{
            if (Objects.requireNonNull(password.getText()).toString().isEmpty() || Objects.requireNonNull(confirm_password.getText()).toString().isEmpty()) {
                Toasty.error(requireContext(), "Please Add Password & Confirm password", Toasty.LENGTH_SHORT, true).show();
            }else {
                changePassword();
            }
        });


        return view;
    }

    private void changePassword(){
        Call<Calling> call = UserData.i.getService().updatePassword(Objects.requireNonNull(password.getText()).toString(),
                Objects.requireNonNull(confirm_password.getText()).toString(), UserData.i.getToken());
        call.enqueue(new Callback<Calling>() {
            @Override
            public void onResponse(@NotNull Call<Calling> call, @NotNull Response<Calling> response) {
                Calling data = response.body();
                assert data != null;
                if (data.error.equals("0")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Toasty.success(requireContext(),data.desc, Toasty.LENGTH_SHORT, true).show();
                    Intent intent = new Intent(requireActivity(), Auth.class);
                    startActivity(intent);
                    requireActivity().finish();

                } else {
                    Toasty.error(requireContext(),data.desc, Toasty.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Calling> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
