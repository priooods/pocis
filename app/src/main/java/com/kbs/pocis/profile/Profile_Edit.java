package com.kbs.pocis.profile;

import android.os.Bundle;
import android.util.Log;
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
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.UserData;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

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

        name.setText(BookingDetailData.i.name);
        phone.setText(BookingDetailData.i.phone);
        fax.setText(BookingDetailData.i.fax);
        email.setText(BookingDetailData.i.email);
        address.setText(BookingDetailData.i.address);
        contact_email.setText(BookingDetailData.i.contact_email);
        contact_phone.setText(BookingDetailData.i.contact_hp);
        contact_name.setText(BookingDetailData.i.contact);
        npwp.setText(BookingDetailData.i.npwp);

        save.setOnClickListener(v->{
            if (Objects.requireNonNull(name.getText()).toString().isEmpty() || Objects.requireNonNull(phone.getText()).toString().isEmpty() || Objects.requireNonNull(fax.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(email.getText()).toString().isEmpty() || Objects.requireNonNull(contact_name.getText()).toString().isEmpty() || Objects.requireNonNull(contact_email.getText()).toString().isEmpty() ||
                    Objects.requireNonNull(address.getText()).toString().isEmpty() || Objects.requireNonNull(contact_phone.getText()).toString().isEmpty()){
                Toasty.error(requireContext(),"Please Add All Form", Toasty.LENGTH_SHORT,true).show();
            } else {
                UpdateProfile();
            }
        });

        return view;
    }


    private void UpdateProfile(){
        Call<BookingDetailData> call = UserData.i.getService().changeProfile(
                Integer.parseInt(BookingDetailData.i.m_city_id),
                BookingDetailData.i.name,
                BookingDetailData.i.address,
                BookingDetailData.i.phone,
                BookingDetailData.i.email,
                BookingDetailData.i.npwp,
                BookingDetailData.i.fax,
                BookingDetailData.i.contact,
                BookingDetailData.i.contact_email,
                BookingDetailData.i.contact_hp,
                UserData.i.getToken()
        );
        call.enqueue(new Callback<BookingDetailData>() {
            @Override
            public void onResponse(@NotNull Call<BookingDetailData> call, @NotNull Response<BookingDetailData> response) {
                BookingDetailData data = response.body();
                assert data != null;
                if (data.message != null){
                    Toasty.success(requireContext(), data.message, Toast.LENGTH_SHORT,true).show();
                    requireActivity().onBackPressed();
                } else {
                    Toasty.error(requireContext(),"Failure Update Profile", Toasty.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<BookingDetailData> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
