package com.kbs.pocis.createboking;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andreseko.SweetAlert.SweetAlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Commodity;
import com.kbs.pocis.model.createboking.Model_UploadDocument;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class AddComodity extends Fragment {

    Button next, prev, addcommodity_two, addcommodity_one;
    TextInputEditText input_weigth,input_package,input_consigne,input_comdity;
    LinearLayout line_addcommodity_one;
    RelativeLayout line_addcommodity_two;

    RecyclerView recyclerView;
    ListComoodity listComoodity;

    ArrayList<Model_Commodity> model_uploadDocumentsd = new ArrayList<>();
    Model_Commodity model_commodity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_comodity, container, false);

        next = view.findViewById(R.id.add_commodity_nextBtn);
        prev = view.findViewById(R.id.add_commodity_prevBtn);
        recyclerView = view.findViewById(R.id.recycle_listcomodity);
        line_addcommodity_one = view.findViewById(R.id.line_addcommodity_one);
        line_addcommodity_two = view.findViewById(R.id.line_addcommodity_two);
        addcommodity_two = view.findViewById(R.id.add_commodity_btnUploadtwo);
        addcommodity_one = view.findViewById(R.id.add_commodity_btnUpload);

        addcommodity_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCommodityNya(getContext());
            }
        });

        addcommodity_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCommodityNya(getContext());
            }
        });

        ButtonFunction();
        SettList(model_commodity);

        return view;
    }

    public void AddCommodityNya (final Context context){
        View view  = LayoutInflater.from(context).inflate(R.layout.dialog_form_commodity, null);
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(view);
        dialogFragment.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        input_weigth = view.findViewById(R.id.input_weight_commodity);
        input_package = view.findViewById(R.id.input_packages_commodity);
        input_consigne = view.findViewById(R.id.input_consigne_commodity);
        input_comdity = view.findViewById(R.id.input_comodity_commodity);

        Button btn_close = view.findViewById(R.id.cancel_form_comodity);
        Button btn_go = view.findViewById(R.id.add_form_comodity);


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.cancel();
            }
        });
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_comdity.getText().toString().isEmpty() || input_consigne.getText().toString().isEmpty() ||
                        input_weigth.getText().toString().isEmpty() || input_package.getText().toString().isEmpty()){
                    Toasty.error(context, "Harap Lengkapi Semua From !", Toasty.LENGTH_SHORT, true).show();
                } else {
                    String com = input_comdity.getText().toString();
                    String con = input_consigne.getText().toString();
                    String wei = input_weigth.getText().toString();
                    String pac = input_package.getText().toString();

                    model_commodity = new Model_Commodity();
                    model_commodity.setCommodity(com);
                    model_commodity.setPackages(pac);
                    model_commodity.setConsigne(con);
                    model_commodity.setWeight(wei);
                    model_uploadDocumentsd.add(model_commodity);

                    SettList(model_commodity);

                    dialogFragment.dismiss();
                }
            }
        });
        dialogFragment.show();
    }

    public void SettList(Model_Commodity commodity){
        if (commodity != null){
            line_addcommodity_two.setVisibility(View.VISIBLE);
            line_addcommodity_one.setVisibility(View.GONE);
            listComoodity = new ListComoodity(getContext(), model_uploadDocumentsd);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(listComoodity);
        }
    }

    public void ButtonFunction(){
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (line_addcommodity_two.getVisibility() != View.GONE){
                    Bundle arg = new Bundle();
                    arg.putSerializable("comodity", model_uploadDocumentsd);
                    Fragment fragment = new VesselInformation();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    Toasty.error(getContext(), "Please Add Your Commodity", Toasty.LENGTH_SHORT, true).show();
                }
            }
        });
    }

    public class ListComoodity extends RecyclerView.Adapter<ListComoodity.vHolder>{

        Context context;
        List<Model_Commodity> model_commodities;

        public ListComoodity(Context context, List<Model_Commodity> model_commodities) {
            this.context = context;
            this.model_commodities = model_commodities;
        }

        @NonNull
        @Override
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_add_commodity, parent, false);
            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull vHolder holder, int position) {
            holder.weight.setText(model_commodities.get(position).getWeight() + " Tonage");
            holder.consigne.setText(model_commodities.get(position).getConsigne());
            holder.comodity.setText(model_commodities.get(position).getCommodity());
            holder.packages.setText(model_commodities.get(position).getPackages() + " Package");
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    model_commodities.remove(position);
                    notifyItemRemoved(position);
                    notifyItemChanged(position, model_commodities.size());
                    if (model_commodities.size() == 0){
                        line_addcommodity_one.setVisibility(View.VISIBLE);
                        line_addcommodity_two.setVisibility(View.GONE);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return model_commodities.size();
        }

        public class vHolder extends RecyclerView.ViewHolder{

            TextView consigne, weight, comodity, packages, delete;

            public vHolder(@NonNull View itemView) {
                super(itemView);

                consigne = itemView.findViewById(R.id.model_consigne_comodity);
                weight = itemView.findViewById(R.id.model_weight_comodity);
                comodity = itemView.findViewById(R.id.model_comodity_comodity);
                packages = itemView.findViewById(R.id.model_package_comodity);
                delete = itemView.findViewById(R.id.model_delete_comodity);

            }
        }


    }

}