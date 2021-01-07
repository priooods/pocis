package com.kbs.pocis.progressbook;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.activity.HomePage;
import com.kbs.pocis.adapter.Adapter_Project;
import com.kbs.pocis.filter.Dialog_Filter;
import com.kbs.pocis.filter.FilterFragment;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.detailbooking.CallingDetail;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Progress_List extends Fragment {

    View view;
    RecyclerView list;
    List<Model_Project> model_projects;
    ImageView icon_back, search_icon;
    RelativeLayout layout_kosong;
    ListProgress adapter_project;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_booking_list, container, false);
        list = view.findViewById(R.id.list_progress);
        icon_back = view.findViewById(R.id.icon_back);
        layout_kosong = view.findViewById(R.id.lay_kosong);
        icon_back.setOnClickListener(v->requireActivity().onBackPressed());
        search_icon = view.findViewById(R.id.btn_search_project_list);
        icon_back.setOnClickListener(v->requireActivity().onBackPressed());

        model_projects = new ArrayList<>();
        callList();

        return view;
    }

    private void callList(){
        Call<CallingDetail> detailCall = UserData.i.getService().progressBooking(UserData.i.getToken());
        detailCall.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail detail = response.body();
                if (Calling.TreatResponse(getContext(),"progress", detail)){
                    assert detail != null;
                    if (detail.data.List.size() > 0){
                        model_projects.addAll(detail.data.List);
                        adapter_project = new ListProgress(requireContext(),model_projects);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
                        list.setLayoutManager(layoutManager);
                        list.setAdapter(adapter_project);
                    } else {
                        layout_kosong.setVisibility(View.VISIBLE);
                    }
                } else {
                    layout_kosong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {

            }
        });
    }


    private static class ListProgress extends RecyclerView.Adapter<ListProgress.vHolder>{

        Context context;
        List<Model_Project> model_projects;

        public ListProgress(Context context, List<Model_Project> model_projects) {
            this.context = context;
            this.model_projects = model_projects;
        }

        @NonNull
        @Override
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_progress_booking, parent, false);
            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull vHolder holder, int position) {
            holder.title.setText(model_projects.get(position).no_booking);
            holder.dates.setText(model_projects.get(position).created);

            holder.gotoo.setOnClickListener(v -> {
                Model_Project.mp = model_projects.get(position);
                HomePage page = (HomePage)context;
                Fragment fragment = new Progress_Booking();
                FragmentManager fragmentManager = page.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            });
        }

        @Override
        public int getItemCount() {
            return model_projects.size();
        }

        public static class vHolder extends RecyclerView.ViewHolder{
            TextView title,dates;
            ConstraintLayout gotoo;
            public vHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                dates = itemView.findViewById(R.id.date);
                gotoo = itemView.findViewById(R.id.gotoo);
            }
        }
    }



}
