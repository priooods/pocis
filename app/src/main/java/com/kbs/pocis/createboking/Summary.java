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

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Commodity;
import com.kbs.pocis.model.createboking.Model_UploadDocument;
import com.kbs.pocis.service.BookingData;

import java.util.ArrayList;
import java.util.List;

public class Summary extends Fragment {

    Button next, prev;
    TextView customer_type, related, contract, veselname, discharge,
            port, arival, departure, loading;

    RecyclerView list_serviceInfo, list_comodityInfo;
    ListView list_Document;
    ArrayList<Model_Commodity> summaryComodities ;
    AdapterComodity adapterComodity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);


        next = view.findViewById(R.id.summary_nextBtn);
        prev = view.findViewById(R.id.summary_prevBtn);

        customer_type = view.findViewById(R.id.veselinfo_customer_type);
        related = view.findViewById(R.id.veselinfo_related_vesel);
        contract = view.findViewById(R.id.veselinfo_contract);

        veselname = view.findViewById(R.id.veselinfo_veselname);
        discharge = view.findViewById(R.id.veselinfo_loading_discharge);
        port = view.findViewById(R.id.veselinfo_port);
        arival = view.findViewById(R.id.veselinfo_arrival);
        departure = view.findViewById(R.id.veselinfo_departure);
        loading = view.findViewById(R.id.veselinfo_loading);


        list_serviceInfo = view.findViewById(R.id.veselinfo_list);
        list_comodityInfo = view.findViewById(R.id.recycle_vesel_comodityInfo);
        list_Document = view.findViewById(R.id.veselinfo_documentList);

        BookingData data = BookingData.i;
        customer_type.setText(data.customerType);
        related.setText(data.relatedVesel);
        contract.setText(data.contract);
        discharge.setText(data.vessel.loading_shipcall + "/" + data.vessel.discharge_ship);
        veselname.setText(data.vessel.vessel_name);
        port.setText(data.vessel.port_discharge);
        arival.setText(data.vessel.estimate_arival);
        departure.setText(data.vessel.estimate_departure);
        loading.setText(data.vessel.port_origin);


        ListingAllList();


        ButtonFunction();
        return view;
    }

    private void ListingAllList() {
        summaryComodities = BookingData.i.commodity;
        adapterComodity = new AdapterComodity(getContext(), summaryComodities);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        list_comodityInfo.setLayoutManager(layoutManager);
        list_comodityInfo.setAdapter(adapterComodity);
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
                ShowDialogCancell(getContext());
            }
        });
    }

    public void ShowDialogCancell (final Context context){
        View view  = LayoutInflater.from(context).inflate(R.layout.dialog_cancelled, null);
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(view);
        dialogFragment.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputLayout input_alasan = view.findViewById(R.id.onp);
        input_alasan.setVisibility(View.GONE);
        TextView title = view.findViewById(R.id.tl);
        ImageView bg = view.findViewById(R.id.bc);
        bg.setImageResource(R.drawable.crb);
        title.setText("Are you sure want to create booking based on your provided information?");
        title.setGravity(Gravity.CENTER);

        Button btn_close = view.findViewById(R.id.btn_cancelclose);
        btn_close.setText("No");
        btn_close.setAllCaps(false);
        Button btn_go = view.findViewById(R.id.btn_cancelbookinggo);
        btn_go.setText("Yes");
        btn_go.setBackground(getResources().getDrawable(R.drawable.btn_green));
        btn_go.setAllCaps(false);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.cancel();
            }
        });
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.dismiss();
                Fragment fragment = new Finish();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameCreate, fragment);
                fragmentTransaction.commit();
            }
        });
        dialogFragment.show();
    }



    public class AdapterComodity extends RecyclerView.Adapter<AdapterComodity.vHolder>{

        Context context;
        List<Model_Commodity> comodityList;

        public AdapterComodity(Context context, List<Model_Commodity> comodityList) {
            this.context = context;
            this.comodityList = comodityList;
        }

        @NonNull
        @Override
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_vesel_comoditylist, parent, false);

            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull vHolder holder, int position) {
            holder.comodity.setText(comodityList.get(position).getCommodity());
            holder.consigne.setText(comodityList.get(position).getConsigne());
            holder.packages.setText(comodityList.get(position).getPackages());
            holder.weight.setText(comodityList.get(position).getWeight());
        }

        @Override
        public int getItemCount() {
            return comodityList.size();
        }

        public class vHolder extends RecyclerView.ViewHolder{
            TextView consigne, comodity, packages, weight;
            public vHolder(@NonNull View itemView) {
                super(itemView);

                comodity = itemView.findViewById(R.id.vesel_infovesel_comodity);
                consigne = itemView.findViewById(R.id.vesel_infovesel_consigne);
                packages = itemView.findViewById(R.id.vesel_infovesel_package);
                weight = itemView.findViewById(R.id.vesel_infovesel_weight);


            }
        }
    }

}