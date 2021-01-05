package com.kbs.pocis.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.complains.Complain_Dasar;
import com.kbs.pocis.complains.Detail_Complain;
import com.kbs.pocis.model.Model_Complain;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.progressbook.Progress_Booking;

import java.util.List;

public class Adapter_Complain extends RecyclerView.Adapter<Adapter_Complain.vHolder> {

    Context context;
    List<Model_Project> model_complains;

    public Adapter_Complain(Context context, List<Model_Project> model_complains) {
        this.context = context;
        this.model_complains = model_complains;
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_project,parent,false);
        return new vHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        holder.number.setTextSize(11);
        holder.status.setTextSize(11);
        holder.ln3.setVisibility(View.GONE);
        holder.ln4.setVisibility(View.GONE);
        holder.ln5.setVisibility(View.GONE);
        holder.ln6.setVisibility(View.GONE);
        holder.ln7.setVisibility(View.GONE);
        holder.ln8.setVisibility(View.GONE);
        holder.title1.setText(R.string.complaint_Date);
        holder.title2.setText(R.string.person_In_Charge);
        holder.item1.setText(model_complains.get(position).created);
        holder.item2.setText(model_complains.get(position).reason_name);
        holder.number.setText(model_complains.get(position).complain_title);
        holder.status.setText(model_complains.get(position).status);
        holder.godetail.setOnClickListener(v->{
            Complain_Dasar page = (Complain_Dasar)context;
            Model_Project.mp = model_complains.get(position);
            Fragment fragment = new Detail_Complain();
            FragmentManager fragmentManager = page.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framecomplain, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });
        if ("CLOSED".equals(holder.status.getText().toString())) {
            holder.status.setTextColor(Color.parseColor("#7d7d7d"));
        }
    }

    @Override
    public int getItemCount() {
        return model_complains.size();
    }

    public static class vHolder extends RecyclerView.ViewHolder{
        LinearLayout ln3,ln4,ln5,ln6,ln7,ln8;
        TextView title1,title2,item1,item2,number,status;
        RelativeLayout godetail;
        public vHolder(@NonNull View itemView) {
            super(itemView);
            ln3 = itemView.findViewById(R.id.ln3);//layout_project
            ln4 = itemView.findViewById(R.id.ln4);
            ln5 = itemView.findViewById(R.id.ln5);
            ln6 = itemView.findViewById(R.id.ln6);
            ln7 = itemView.findViewById(R.id.ln7);
            ln8 = itemView.findViewById(R.id.ln8);
            title1 = itemView.findViewById(R.id.title1);
            title2 = itemView.findViewById(R.id.title2);
            item1 = itemView.findViewById(R.id.item1);
            item2 = itemView.findViewById(R.id.item2);
            number = itemView.findViewById(R.id.model_number);
            status = itemView.findViewById(R.id.model_status);
            godetail = itemView.findViewById(R.id.layout_project);
        }
    }
}
