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
import com.kbs.pocis.service.UserData;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class Profile_Edit extends Fragment {

    View view;
    TextInputEditText name,phone,fax,email,address,contact_name,contact_email,contact_phone,npwp;
    Button cancel,save;
    ImageView back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_editing, container, false);

        name = view.findViewById(R.id.name);
        npwp = view.findViewById(R.id.npwp);
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

        back.setOnClickListener(v->requireActivity().onBackPressed());

        cancel.setOnClickListener(v->requireActivity().onBackPressed());

        if (UserData.isExists()){
            name.setText(UserData.i.username);
            phone.setText(UserData.i.phone);
            fax.setText(UserData.i.fax);
            email.setText(UserData.i.email);
            address.setText(UserData.i.address);
            contact_email.setText(UserData.i.contact_email);
            contact_phone.setText(UserData.i.contact_phone);
            contact_name.setText(UserData.i.contact_name);
            npwp.setText(UserData.i.npwp);

        }

        save.setOnClickListener(v->{
            if (Objects.requireNonNull(name.getText()).toString().isEmpty() || Objects.requireNonNull(phone.getText()).toString().isEmpty() || Objects.requireNonNull(fax.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(email.getText()).toString().isEmpty() || Objects.requireNonNull(contact_name.getText()).toString().isEmpty() || Objects.requireNonNull(contact_email.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(address.getText()).toString().isEmpty() || Objects.requireNonNull(contact_phone.getText()).toString().isEmpty()){
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
