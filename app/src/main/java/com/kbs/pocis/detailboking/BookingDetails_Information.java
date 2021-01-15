package com.kbs.pocis.detailboking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Project;

import java.util.ArrayList;
import java.util.List;

public class BookingDetails_Information extends Fragment {

    //Box Booking Information
    TextView  nomerContract, flagVessel, customerName, no_data,
            vesselName, customerType, bookingDate, bookingTime, flagContract;
    //Box Schedule Information
    TextView detail_info_schedule_booking_vesselName, schedule_est_arival, schedule_est_departure,
        schedule_port_ship, schedule_port_cigd, schedule_loading;

    ConstraintLayout layout_schedule;
    RecyclerView ln_document;
    LinearLayout ln_schedule;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.booking_details_information, container, false);

        ln_schedule = view.findViewById(R.id.ln_schedule);
        ln_document = view.findViewById(R.id.ln_document);
        no_data = view.findViewById(R.id.nodata);
        nomerContract = view.findViewById(R.id.detail_info_booking_contractNo);
        flagVessel = view.findViewById(R.id.detail_info_booking_flagvessel);
        customerName = view.findViewById(R.id.detail_info_booking_customerName);
        vesselName = view.findViewById(R.id.detail_info_booking_vesselName);
        customerType = view.findViewById(R.id.detail_info_booking_customerType);
        bookingDate = view.findViewById(R.id.detail_info_booking_bookingDate);
        bookingTime = view.findViewById(R.id.detail_info_booking_bookingTime);
        flagContract = view.findViewById(R.id.detail_info_booking_flagContract);
        detail_info_schedule_booking_vesselName = view.findViewById(R.id.detail_info_schedule_booking_vesselName);
        schedule_est_arival = view.findViewById(R.id.detail_info_schedule_booking_estArival);
        schedule_est_departure = view.findViewById(R.id.detail_info_schedule_booking_departure);
        schedule_port_ship = view.findViewById(R.id.detail_info_schedule_booking_discharge);
        schedule_port_cigd = view.findViewById(R.id.detail_info_schedule_booking_port);
        schedule_loading = view.findViewById(R.id.detail_info_schedule_booking_loading);
        layout_schedule = view.findViewById(R.id.go_detail_booking);

        nomerContract.setText(Model_Project.Information.get(0).contract_no);
        flagVessel.setText(Model_Project.Information.get(0).flag_related_vessel);
        customerName.setText(Model_Project.Information.get(0).customer_name);
        vesselName.setText(Model_Project.Information.get(0).vessel_name);
        customerType.setText(Model_Project.Information.get(0).customer_type_code);
        bookingDate.setText(Model_Project.Information.get(0).booking_date);
        bookingTime.setText(Model_Project.Information.get(0).booking_time);
        flagContract.setText(Model_Project.Information.get(0).flag_contract);

        if (Model_Project.Information.get(0).vessel_name == null){
            ln_schedule.setVisibility(View.GONE);
        } else {
            detail_info_schedule_booking_vesselName.setText(Model_Project.Information.get(0).vessel_name);
            schedule_est_arival.setText(Model_Project.Information.get(0).estimate_arrival);
            schedule_port_cigd.setText(Model_Project.Information.get(0).port_of_cigading);
            schedule_port_ship.setText(Model_Project.Information.get(0).voyage_no);
            schedule_loading.setText(Model_Project.Information.get(0).port_of_loading);
            schedule_est_departure.setText(Model_Project.Information.get(0).estimate_departure);
        }

        if (Model_Project.Documents.size() > 0){
            List<Model_Project> documents = new ArrayList<>(Model_Project.Documents);
            RecyclerPDF adpter = new RecyclerPDF(getContext(),documents);
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            ln_document.setLayoutManager(manager);
            ln_document.setAdapter(adpter);
        } else {
            no_data.setVisibility(View.VISIBLE);
        }

        return view;
    }


    public static class RecyclerPDF extends RecyclerView.Adapter<RecyclerPDF.vHolder>{

        Context context;
        List<Model_Project> model_document;

        public RecyclerPDF(Context context, List<Model_Project> model_document) {
            this.context = context;
            this.model_document = model_document;
        }

        @NonNull
        @Override
        public RecyclerPDF.vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_list_pdf, parent, false);
            return new RecyclerPDF.vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerPDF.vHolder holder, int position) {
            holder.sizefile.setVisibility(View.GONE);
            holder.line.setVisibility(View.GONE);
            holder.deletefile.setVisibility(View.GONE);
            holder.tap.setOnClickListener(v->{
                Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse(model_document.get(position).link));
                this.context.startActivity(web);
            });
            holder.nama.setText(model_document.get(position).document_name);
        }

        @Override
        public int getItemCount() {
            return model_document.size();
        }

        public static class vHolder extends RecyclerView.ViewHolder{

            ConstraintLayout tap;
            TextView nama, deletefile, sizefile;
            View line;

            public vHolder(@NonNull View itemView) {
                super(itemView);
                line = itemView.findViewById(R.id.ln);
                tap = itemView.findViewById(R.id.tap);
                nama = itemView.findViewById(R.id.model_uploadpdf_name);
                deletefile = itemView.findViewById(R.id.delete_files);
                sizefile = itemView.findViewById(R.id.model_uploadpdf_size);
            }
        }
    }
}
