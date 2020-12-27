package com.kbs.pocis.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.myproject.Detail_MyProject;
import com.kbs.pocis.progressbook.Progress_Booking;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Adapter_Project extends RecyclerView.Adapter<Adapter_Project.vHolder> implements Filterable {

    Context context;
    List<Model_Project> model_project;
    List<Model_Project> search;
    int value;

    public Adapter_Project(Context context, List<Model_Project> model_project, int value) {
        this.context = context;
        this.model_project = model_project;
        this.value = value;
        this.search = model_project;
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_project, parent, false);
        return new vHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
            switch (value) {
                case 0: //for project approved

                    holder.ln4.setVisibility(View.GONE);
                    holder.ln5.setVisibility(View.GONE);
                    holder.ln6.setVisibility(View.GONE);
                    holder.ln7.setVisibility(View.GONE);
                    holder.ln8.setVisibility(View.GONE);
                    holder.title_1.setText(R.string.customername);
                    holder.title_2.setText(R.string.ppjno);
                    holder.title_3.setText(R.string.vesselname);

                    holder.number.setText(model_project.get(position).no_booking);
                    holder.item3.setText(model_project.get(position).vessel_name);
                    holder.item1.setText(model_project.get(position).customer_name);
                    holder.item2.setText(model_project.get(position).ppj_no);

                    holder.status.setText(model_project.get(position).status_project);
                    holder.gotoo.setOnClickListener(v -> {
                        Intent intent = new Intent(context, Detail_MyProject.class);
                        Model_Project.Code = 0;
                        Model_Project.mp = model_project.get(position);
                        Log.i(TAG, "onBindViewHolder: => " + model_project.get(position).temp_project_no);
                        context.startActivity(intent);
                    });
                    break;
                case 1: //for project List
                    holder.ln_top1.setVisibility(View.VISIBLE);
                    holder.ln_top2.setVisibility(View.VISIBLE);
                    holder.title_top1.setText(R.string.date_isue);
                    holder.title_top2.setText(R.string.ppjno);
                    holder.title_1.setText(R.string.booking_no);
                    holder.title_2.setText(R.string.start_date);
                    holder.title_3.setText(R.string.schedule);
                    holder.title_4.setText(R.string.consigne);
                    holder.title_5.setText(R.string.tonage);
                    holder.title_6.setText(R.string.vesselname);
                    holder.title_7.setText(R.string.bilpay_no);
                    holder.title_8.setText(R.string.va_number);

                    holder.number.setText(model_project.get(position).temp_project_no);
                    holder.status.setText(model_project.get(position).status_project);
                    holder.item_top1.setText(model_project.get(position).date_project_issued);
                    holder.item_top2.setText(model_project.get(position).ppj_no);
                    holder.item1.setText(model_project.get(position).no_booking);
                    holder.item2.setText(model_project.get(position).start_date);
                    holder.item3.setText(model_project.get(position).schedule_code);
                    holder.item4.setText(model_project.get(position).customer_name);
                    holder.item5.setText(model_project.get(position).tonnage);
                    holder.item6.setText(model_project.get(position).vessel_name);
                    holder.item7.setText(model_project.get(position).bill_paymemt_number);
                    holder.item8.setText(model_project.get(position).va_number);
                    holder.gotoo.setOnClickListener(v -> {
                        Intent intent = new Intent(context, Detail_MyProject.class);
                        Model_Project.Code = 1;
                        Model_Project.mp = model_project.get(position);
                        context.startActivity(intent);
                    });
                    break;
                case 2: //for BPAJ
                    holder.ln6.setVisibility(View.GONE);
                    holder.ln7.setVisibility(View.GONE);
                    holder.ln8.setVisibility(View.GONE);

                    holder.title_1.setText(R.string.booking_no);
                    holder.title_2.setText(R.string.customername);
                    holder.title_3.setText(R.string.ppjno);
                    holder.title_4.setText(R.string.vesselname);
                    holder.title_5.setText(R.string.voyage);

                    holder.number.setText(model_project.get(position).temp_project_no);
                    holder.item4.setText(model_project.get(position).vessel_name);
                    holder.item1.setText(model_project.get(position).booking_no);
                    holder.item2.setText(model_project.get(position).customer_name);
                    holder.item3.setText(model_project.get(position).ppj_no);
                    holder.item5.setText(model_project.get(position).voyage_no);
                    holder.status.setText(model_project.get(position).status_bapj);
                    holder.gotoo.setOnClickListener(v -> {
                        Intent intent = new Intent(context, Detail_MyProject.class);
                        Model_Project.Code = 2;
                        Model_Project.mp = model_project.get(position);
                        context.startActivity(intent);
                    });
                    break;
                case 3: //for invoice
                    holder.ln6.setVisibility(View.GONE);
                    holder.ln7.setVisibility(View.GONE);
                    holder.ln8.setVisibility(View.GONE);

                    holder.title_1.setText(R.string.project_no);
                    holder.title_2.setText(R.string.customername);
                    holder.title_3.setText(R.string.booking_no);
                    holder.title_4.setText(R.string.vesselname);
                    holder.title_5.setText(R.string.cancel_stat);
                    holder.number.setText(model_project.get(position).invoice_no);
                    holder.item4.setText(model_project.get(position).vessel_name);
                    holder.item1.setText(model_project.get(position).project_no);
                    holder.item2.setText(model_project.get(position).customer_name);
                    holder.item3.setText(model_project.get(position).booking_no);
                    holder.item5.setText(model_project.get(position).status_cancel);
                    holder.status.setText(model_project.get(position).status_payment);
                    holder.gotoo.setOnClickListener(v -> {
                        Intent intent = new Intent(context, Detail_MyProject.class);
                        Model_Project.Code = 3;
                        Model_Project.mp = model_project.get(position);
                        context.startActivity(intent);
                    });
                    break;
                case 4: //for performa
                    holder.ln7.setVisibility(View.GONE);
                    holder.ln8.setVisibility(View.GONE);

                    holder.title_1.setText(R.string.temp_proj);
                    holder.title_2.setText(R.string.customername);
                    holder.title_3.setText(R.string.cancel_stat);
                    holder.title_4.setText(R.string.vesselname);
                    holder.title_5.setText(R.string.book_stat);
                    holder.title_6.setText(R.string.payment_type);
                    holder.number.setText(model_project.get(position).booking_no);
                    holder.item4.setText(model_project.get(position).vessel_name);
                    holder.item1.setText(model_project.get(position).temp_project_no);
                    holder.item2.setText(model_project.get(position).customer_name);
                    holder.item3.setText(model_project.get(position).status_cancel);
                    holder.item5.setText(model_project.get(position).status_booking);
                    holder.item6.setText(model_project.get(position).tipe_pembayaran);
                    holder.status.setText(model_project.get(position).status_payment);
                    holder.gotoo.setOnClickListener(v -> {
                        Intent intent = new Intent(context, Detail_MyProject.class);
                        Model_Project.Code = 4;
                        Model_Project.mp = model_project.get(position);
                        context.startActivity(intent);
                    });
                    break;
                case 5: // for Progress Booking
                    holder.ln4.setVisibility(View.GONE);
                    holder.ln5.setVisibility(View.GONE);
                    holder.ln6.setVisibility(View.GONE);
                    holder.ln7.setVisibility(View.GONE);
                    holder.ln8.setVisibility(View.GONE);
                    holder.title_1.setText(R.string.customername);
                    holder.title_2.setText("Customer Type");
                    holder.title_3.setText("Booking Date");

                    holder.number.setText(model_project.get(position).no_booking);
                    holder.item3.setText(model_project.get(position).customer_name);
                    holder.item1.setText(model_project.get(position).customer_type);
                    holder.item2.setText(model_project.get(position).booking_date);

                    holder.status.setText(model_project.get(position).status_project);
                    holder.gotoo.setOnClickListener(v -> {
                        HomePage page = (HomePage)context;
                        Fragment fragment = new Progress_Booking();
                        FragmentManager fragmentManager = page.getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                        fragmentTransaction.commit();
                    });
                    break;
            }

            switch (holder.status.getText().toString()){
                case "PREPARED":
                    holder.status.setTextColor(Color.parseColor("#00a1d1"));
                    break;
                case "CLOSED":
                    holder.status.setTextColor(Color.parseColor("#7B7B7B"));
                    break;
                case "Unpaid":
                    holder.status.setTextColor(Color.parseColor("#D41111"));
                    break;
                case "DRAFT":
                    holder.status.setTextColor(Color.parseColor("#1A2CD1"));
                    break;
            }

    }

    @Override
    public int getItemCount() {
        return model_project.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String value = constraint.toString();
                if (value.isEmpty()){
                    model_project = search;
                } else {
                    List<Model_Project> projects = new ArrayList<>();
                    for (Model_Project model_project : projects){
                        if (model_project.booking_no.toLowerCase().contains(value.toLowerCase()) ||
                                model_project.no_booking.toLowerCase().contains(value.toLowerCase()) ||
                                model_project.temp_project_no.toLowerCase().contains(value.toLowerCase()) ||
                                model_project.vessel_name.toLowerCase().contains(value.toLowerCase()) ||
                                model_project.project_no.toLowerCase().contains(value.toLowerCase())){
                            projects.add(model_project);
                        }
                    }

                    model_project = projects;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = model_project;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                model_project = (List<Model_Project>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class vHolder extends RecyclerView.ViewHolder{
        LinearLayout ln_top1, ln_top2,ln4,ln5,ln6,ln7,ln8;
        TextView number, status, item_top1,item_top2, item1, item3, item2, item5, item4, item6,item7,item8,
            title_top1,title_top2,title_1,title_2,title_3,title_4,title_5, title_6,title_7,title_8;
        RelativeLayout gotoo;
        public vHolder(@NonNull View itemView) {
            super(itemView);
            item_top1 = itemView.findViewById(R.id.item_top1);
            item_top2 = itemView.findViewById(R.id.item_top2);
            item1 = itemView.findViewById(R.id.item1);
            item3 = itemView.findViewById(R.id.item3);
            item2 = itemView.findViewById(R.id.item2);
            item5 = itemView.findViewById(R.id.item5);
            item4 = itemView.findViewById(R.id.item4);
            item6 = itemView.findViewById(R.id.item6);
            item7 = itemView.findViewById(R.id.item7);
            item8 = itemView.findViewById(R.id.item8);
            title_top1 = itemView.findViewById(R.id.title_top1);
            title_top2 = itemView.findViewById(R.id.title_top2);
            title_1 = itemView.findViewById(R.id.title1);
            title_2 = itemView.findViewById(R.id.title2);
            title_3 = itemView.findViewById(R.id.title3);
            title_4 = itemView.findViewById(R.id.title4);
            title_5 = itemView.findViewById(R.id.title5);
            title_6 = itemView.findViewById(R.id.title6);
            title_7 = itemView.findViewById(R.id.title7);
            title_8 = itemView.findViewById(R.id.title8);
            ln_top1 = itemView.findViewById(R.id.ln_top1);
            ln_top2 = itemView.findViewById(R.id.ln_top2);
            ln4 = itemView.findViewById(R.id.ln4);
            ln5 = itemView.findViewById(R.id.ln5);
            ln6 = itemView.findViewById(R.id.ln6);
            ln7 = itemView.findViewById(R.id.ln7);
            ln8 = itemView.findViewById(R.id.ln8);
            gotoo = itemView.findViewById(R.id.layout_project);
            number = itemView.findViewById(R.id.model_number);
            status = itemView.findViewById(R.id.model_status);
        }
    }
}
