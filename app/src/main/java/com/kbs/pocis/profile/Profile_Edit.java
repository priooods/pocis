package com.kbs.pocis.profile;

import android.os.Bundle;
import android.util.Patterns;
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

public class Profile_Edit extends Fragment {

    View view;
    TextInputEditText name,phone,fax,email,address,contact_name,contact_email,contact_phone;
    Button cancel,save;
    ImageView back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_editing, container, false);

        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        fax = view.findViewById(R.id.fax);
        email = view.findViewById(R.id.email);
        address = view.findViewById(R.id.address);
        contact_name = view.findViewById(R.id.contact_name);
        contact_email = view.findViewById(R.id.contact_email);
        contact_phone = view.findViewById(R.id.contact_phone);
        cancel = view.findViewById(R.id.btn_cancel);
        save = view.findViewById(R.id.btn_save);
        back = view.findViewById(R.id.icon_back);

        back.setOnClickListener(v->{
            requireActivity().onBackPressed();
        });

        cancel.setOnClickListener(v->{
            requireActivity().onBackPressed();
        });

        save.setOnClickListener(v->{
            if (name.getText().toString().isEmpty() ||phone.getText().toString().isEmpty() ||fax.getText().toString().isEmpty() ||
                    email.getText().toString().isEmpty() ||contact_name.getText().toString().isEmpty() ||contact_email.getText().toString().isEmpty() ||
                    address.getText().toString().isEmpty() ||contact_phone.getText().toString().isEmpty()){
                Toasty.error(requireContext(),"Please Add All Form", Toasty.LENGTH_SHORT,true).show();
            } else if (isValid(email.getText()) || isValid(contact_email.getText())){
                Toasty.error(requireContext(),"Email or Contact Email not Valid", Toasty.LENGTH_SHORT,true).show();
            } else {
                Toast.makeText(requireContext(),"Eksekusi ke Server", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public static boolean isValid(CharSequence target){
        return !Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
