package com.kbs.pocis.createboking;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kbs.pocis.R;
import com.kbs.pocis.item.FormCommodity;
import com.kbs.pocis.model.Model_Commodity;

import java.util.List;

public class AddComodity extends Fragment {

    Button next, prev, addcommodity_two, addcommodity_one;
    String consignee, packages, comodity, weight;
    LinearLayout line_addcommodity_one;
    RelativeLayout line_addcommodity_two;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_comodity, container, false);

        next = view.findViewById(R.id.add_commodity_nextBtn);
        prev = view.findViewById(R.id.add_commodity_prevBtn);
        line_addcommodity_one = view.findViewById(R.id.line_addcommodity_one);
        line_addcommodity_two = view.findViewById(R.id.line_addcommodity_two);
        addcommodity_two = view.findViewById(R.id.add_commodity_btnUploadtwo);
        addcommodity_one = view.findViewById(R.id.add_commodity_btnUpload);
        addcommodity_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new FormCommodity();
                dialogFragment.show(getChildFragmentManager(),"form Add Commodity");
            }
        });


        ButtonFunction();
        AddCommodity();
        Model_Commodity model_commodity = new Model_Commodity();
        Log.d("comodity", "----> " + model_commodity.getConsigne());

        return view;
    }

    private void CheckListing() {
        Bundle bundle = getArguments();
        consignee = bundle.getString("consigne");
        if (!consignee.toString().isEmpty()){
            line_addcommodity_one.setVisibility(View.GONE);
            line_addcommodity_two.setVisibility(View.VISIBLE);
        }
    }


    public void AddCommodity(){
        addcommodity_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new FormCommodity();
                dialogFragment.show(getChildFragmentManager(),"form Add Commodity");
            }
        });
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
                Fragment fragment = new VesselInformation();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
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
            holder.weight.setText(model_commodities.get(position).getWeight());
            holder.consigne.setText(model_commodities.get(position).getConsigne());
            holder.comodity.setText(model_commodities.get(position).getCommodity());
            holder.packages.setText(model_commodities.get(position).getPackages());
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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