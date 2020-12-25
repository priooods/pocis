package com.kbs.pocis.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;

import es.dmoral.toasty.Toasty;

public class Change_Password extends Fragment {

    View view;
    ImageView icon_back;
    TextInputEditText password,confirm_password;
    Button cancel, save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.password_change, container, false);

        password = view.findViewById(R.id.password);
        icon_back = view.findViewById(R.id.icon_back);
        confirm_password = view.findViewById(R.id.confirm_password);
        cancel = view.findViewById(R.id.btn_cancel);
        save = view.findViewById(R.id.btn_save);
        cancel.setOnClickListener(v->{
            requireActivity().onBackPressed();
        });

        icon_back.setOnClickListener(v->{
            requireActivity().onBackPressed();
        });
        save.setOnClickListener(v->{
            if (password.getText().toString().isEmpty() || confirm_password.getText().toString().isEmpty()){
                Toasty.error(requireContext(), "Please Add Password & Confirm password", Toasty.LENGTH_SHORT,true).show();
            } else if (!confirm_password.getText().toString().equals(password.getText().toString())){
                Toasty.error(requireContext(), "Confirm Password Not Valid, Please Check !", Toasty.LENGTH_SHORT,true).show();
            } else {
                Toast.makeText(requireContext(),"Eksekusi ke Server", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
