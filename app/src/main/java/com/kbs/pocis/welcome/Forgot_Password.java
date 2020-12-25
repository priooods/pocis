package com.kbs.pocis.welcome;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class Forgot_Password extends Fragment {

    TextInputEditText input_email;
    Button btn_password;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgot_password, container, false);

        input_email = view.findViewById(R.id.input_email);
        btn_password = view.findViewById(R.id.btn_sendPassword);

        btn_password.setOnClickListener( v -> {
            if (isValid(input_email.getText())){
                Toasty.error(requireContext(), "Your Email not Valid", Toasty.LENGTH_SHORT, true).show();
            } else {
                Toast.makeText(requireContext(),"Eksekusi ke server", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    public static boolean isValid(CharSequence target){
        return !Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
