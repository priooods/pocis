package com.kbs.pocis.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.detailbooking.CallingDetail;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Profile extends Fragment {

    View view;
    TextView phone,email,code,fax,username,npwp,contact_name,contact_email,contact_phone,cust_type, address;
    Button edit_profile;
    ImageView back;
    RecyclerView list_type;
    List<BookingDetailData.Type> models;

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
        list_type = view.findViewById(R.id.l5);
        back.setOnClickListener(v->requireActivity().onBackPressed());
        models = new ArrayList<>();
        getUserProfile();

        edit_profile.setOnClickListener(v->{
            Fragment fragment;
            fragment = new Profile_Edit();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });

        return view;
    }

    public void getUserProfile(){
        Call<CallingDetail> call = UserData.i.getService().getUserInfo(UserData.i.getToken());
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail data = response.body();
                if (Calling.TreatResponse(requireContext(),"profile", data)){
                    assert data != null;
                    username.setText(data.data.name);
                    address.setText(data.data.address);
                    phone.setText(data.data.phone);
                    fax.setText(data.data.fax);
                    email.setText(data.data.email);
                    npwp.setText(data.data.npwp);
                    contact_email.setText(data.data.contact_email);
                    contact_name.setText(data.data.contact);
                    contact_phone.setText(data.data.contact_hp);
                    models = data.data.types;
                    BookingDetailData.i = data.data;

//                    BookingDetailData.i.setAllUserData(
//                            username.getText().toString(),
//                            address.getText().toString(),
//                            fax.getText().toString(),
//                            email.getText().toString(),
//                            phone.getText().toString(),
//                            npwp.getText().toString(),
//                            contact_email.getText().toString(),
//                            contact_name.getText().toString(),
//                            contact_phone.getText().toString()
//                    );

                    ListType adapter_project_service = new ListType(requireContext(), models);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    list_type.setLayoutManager(layoutManager);
                    list_type.setAdapter(adapter_project_service);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: " + t);
            }
        });
    }

    public static class ListType extends RecyclerView.Adapter<ListType.vHolder>{

        Context context;
        List<BookingDetailData.Type> model;

        public ListType(Context context, List<BookingDetailData.Type> model) {
            this.context = context;
            this.model = model;
        }

        @NonNull
        @Override
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_userprofile_type, parent, false);
            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull vHolder holder, int position) {
            holder.type.setText(model.get(position).name);
        }

        @Override
        public int getItemCount() {
            return model.size();
        }

        public static class vHolder extends RecyclerView.ViewHolder{

            TextView type;
            public vHolder(@NonNull View itemView) {
                super(itemView);
                type = itemView.findViewById(R.id.cutomer_type);
            }
        }
    }
}
