package com.kbs.pocis.progressbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.Adapter_Project;
import com.kbs.pocis.filter.Dialog_Filter;
import com.kbs.pocis.filter.FilterFragment;
import com.kbs.pocis.model.Model_Project;

import java.util.ArrayList;
import java.util.List;

public class Progress_List extends FilterFragment {

    View view;
    RecyclerView list;
    Adapter_Project adapter_project;
    List<Model_Project> model_projects;
    ImageView icon_back, search_icon;
    DialogFragment fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.progress_booking_list, container, false);
        list = view.findViewById(R.id.list_progress);
        icon_back = view.findViewById(R.id.icon_back);
        search_icon = view.findViewById(R.id.btn_search_project_list);
        icon_back.setOnClickListener(v->requireActivity().onBackPressed());

        search_icon.setOnClickListener(v -> {
            fragment = new Dialog_Filter(true,Progress_List.this);
            fragment.show(getChildFragmentManager(), "filter_online");
        });
        model_projects = new ArrayList<>();

        model_projects.add(new Model_Project("APPROVED", "K0001-2020-00097","BUANA INDAH GEMACA PT.","Agent","1 April 2020 13:02"));
        model_projects.add(new Model_Project("APPROVED", "K0001-2020-00098","BUANA INDAH GEMACA PT.","Agent","1 April 2020 13:02"));
        model_projects.add(new Model_Project("APPROVED", "K0001-2020-00099","BUANA INDAH GEMACA PT.","Agent","1 April 2020 13:02"));
        adapter_project = new Adapter_Project(requireContext(),model_projects,5);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter_project);


        return view;
    }



}
