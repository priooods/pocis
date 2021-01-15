package com.kbs.pocis.complains;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.Adapter_Complain;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.myproject.detail.Documents;
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.detailbooking.CallingDetail;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class Detail_Complain extends Fragment {

    TextView name_company, complain_type,complain_desc,complain_date;
    RecyclerView list_attach,list_comment;
    Button btn_send;
    TextInputEditText input_comment;
    List<Model_Project> attach_model;
    ImageView back_complain;
    RecyclerPDF adapter_chatt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_complain, container, false);

        name_company = view.findViewById(R.id.name_company);
        complain_type = view.findViewById(R.id.complain_type);
        complain_desc = view.findViewById(R.id.complain_desc);
        complain_date = view.findViewById(R.id.complain_date);
        back_complain = view.findViewById(R.id.back_complain);
        list_attach = view.findViewById(R.id.list_attach);
        list_comment = view.findViewById(R.id.list_comment);
        input_comment = view.findViewById(R.id.input_comment);
        btn_send = view.findViewById(R.id.btn_send);
        back_complain.setOnClickListener(v->requireActivity().onBackPressed());

        Model_Project data = Model_Project.mp;
        name_company.setText(data.customer_name);
        complain_date.setText(data.created);
        complain_desc.setText(data.complain_desc);
        complain_type.setText(data.complain_title);

        callingDetailsComplain(data);
        btn_send.setOnClickListener(v->{
            if (Objects.requireNonNull(input_comment.getText()).toString().isEmpty()){
                Toasty.error(requireContext(),"Please add your comment", Toasty.LENGTH_SHORT, true).show();
            } else {
                newComment(data,input_comment);
            }
        });


        return view;
    }

    public void callingDetailsComplain(Model_Project data){
        Call<CallingDetail> call = UserData.i.getService().detailComplaint(UserData.i.getToken(), data.id);
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail detail = response.body();
                if (Calling.TreatResponse(getContext(),"detail_complaint", detail)){
                    assert detail != null;
                    Model_Project.attachments = detail.data.attachments;
                    Model_Project.details = detail.data.details;
                    attachList();
                    commentList();
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: " + t);
            }
        });
    }

    public void attachList(){
        attach_model = new ArrayList<>();
        attach_model.addAll(Model_Project.attachments);
        RecyclerPDF adapter_chatt = new RecyclerPDF(getContext(), attach_model,0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        list_attach.setLayoutManager(layoutManager);
        list_attach.setAdapter(adapter_chatt);
    }

    public void commentList(){
        attach_model = new ArrayList<>();
        attach_model.addAll(Model_Project.details);
        adapter_chatt = new RecyclerPDF(getContext(), attach_model, 1);
        Log.i(TAG, "commentList: " + Model_Project.mp.user_name);
        LinearLayoutManager mn = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mn.setStackFromEnd(true);
        list_comment.setLayoutManager(mn);
        list_comment.setAdapter(adapter_chatt);
        adapter_chatt.notifyDataSetChanged();
    }

    public void newComment(Model_Project data, TextInputEditText input_comment){
        Log.i(TAG, "newComment: " + data.id);
        Call<CallingDetail>call = UserData.i.getService().newCommentComplain(UserData.i.getToken(), data.id, Objects.requireNonNull(input_comment.getText()).toString());
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail detail = response.body();
                if (Calling.TreatResponse(getContext(), "new_coment", detail)){
                    assert detail != null;
                    Log.i(TAG, "onResponse: new " + " success");
                    List<Model_Project> mod= new ArrayList<>();
                    mod.add(new Model_Project(detail.data.created,detail.data.description,detail.data.name));
                    adapter_chatt.updateComment(mod);
                    Objects.requireNonNull(list_comment.getLayoutManager()).scrollToPosition(adapter_chatt.getItemCount() - 1);
                    input_comment.getText().clear();
                } else {
                    Toasty.error(requireContext(),"Your Connection Failure, Please Check Your Connection", Toasty.LENGTH_SHORT, true).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.i(TAG, "onFailure: " + t);
            }
        });
    }

    public static class RecyclerPDF extends RecyclerView.Adapter<RecyclerPDF.vHolder>{

        Context context;
        List<Model_Project> model_document;
        int type;

        public RecyclerPDF(Context context, List<Model_Project> model_document, int type) {
            this.context = context;
            this.type = type;
            this.model_document = model_document;
        }

        @NonNull
        @Override
        public RecyclerPDF.vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.complain_model, parent, false);
            return new RecyclerPDF.vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerPDF.vHolder holder, int position) {
            switch (type){
                case 0:
                    holder.tap.setVisibility(View.VISIBLE);
                    holder.nama.setText(model_document.get(position).filename);
                    holder.sizefile.setText(R.string.size_file_mb);
                    holder.ds.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                    ));
                    holder.tap.setOnClickListener(v->{
                        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse(model_document.get(position).path));
                        this.context.startActivity(web);
                    });
                    break;
                case 1:
                    holder.ln_com.setVisibility(View.VISIBLE);
                    holder.dates.setText(model_document.get(position).created);
                    holder.desc_commen.setText(model_document.get(position).description);
                    holder.cust_name.setText(model_document.get(position).name);
                    if (holder.cust_name.getText().toString().equals(Model_Project.mp.user_name)){
                        ConstraintSet set = new ConstraintSet();
                        set.clone(holder.ds);
                        set.connect(R.id.ln_com,ConstraintSet.END,ConstraintSet.PARENT_ID, ConstraintSet.END,0);
                        set.clear(R.id.ln_com,ConstraintSet.START);
                        set.applyTo(holder.ds);
                    }
                    break;
            }

        }

        public void updateComment(List<Model_Project> data){
            model_document.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return model_document.size();
        }

        public static class vHolder extends RecyclerView.ViewHolder{

            ConstraintLayout tap,ds;
            CardView ln_com;
            TextView nama,  sizefile,dates, desc_commen, cust_name;

            public vHolder(@NonNull View itemView) {
                super(itemView);
                tap = itemView.findViewById(R.id.tap);
                ds = itemView.findViewById(R.id.ds);
                ln_com = itemView.findViewById(R.id.ln_com);
                nama = itemView.findViewById(R.id.model_uploadpdf_name);
                sizefile = itemView.findViewById(R.id.model_uploadpdf_size);
                dates = itemView.findViewById(R.id.dates);
                desc_commen = itemView.findViewById(R.id.desc_commen);
                cust_name = itemView.findViewById(R.id.cust_name);
            }
        }
    }
}
