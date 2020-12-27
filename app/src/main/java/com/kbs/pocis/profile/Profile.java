package com.kbs.pocis.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.kbs.pocis.R;
import com.kbs.pocis.service.UserData;

public class Profile extends Fragment {

    View view;
    TextView phone,email,code,fax,username,npwp,contact_name,contact_email,contact_phone,cust_type, address;
    Button edit_profile;
    ImageView back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile, container, false);

        edit_profile = view.findViewById(R.id.btn_editUser);
        back = view.findViewById(R.id.icon_back);

        phone = view.findViewById(R.id.phone);
        address = view.findViewById(R.id.address);
        email = view.findViewById(R.id.email);
        code = view.findViewById(R.id.code);
        fax = view.findViewById(R.id.pay);
        username = view.findViewById(R.id.username);
        npwp = view.findViewById(R.id.npwp);
        contact_name = view.findViewById(R.id.contact_name);
        contact_email = view.findViewById(R.id.contact_email);
        contact_phone = view.findViewById(R.id.contact_phone);
        cust_type = view.findViewById(R.id.cutomer_type);

        back.setOnClickListener(v->{
            requireActivity().onBackPressed();
        });

        if (UserData.isExists()){
            username.setText(UserData.i.username);
        }

        edit_profile.setOnClickListener(v->{
            UserData.i.setAllUserData(
                    username.getText().toString(),
                    address.getText().toString(),
                    fax.getText().toString(),
                    email.getText().toString(),
                    phone.getText().toString(),
                    npwp.getText().toString(),
                    contact_name.getText().toString(),
                    contact_phone.getText().toString(),
                    contact_email.getText().toString()
                    );
            Fragment fragment;
            fragment = new Profile_Edit();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });

        return view;
    }
}
