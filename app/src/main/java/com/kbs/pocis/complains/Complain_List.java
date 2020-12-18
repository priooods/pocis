package com.kbs.pocis.complains;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.Adapter_Complain;
import com.kbs.pocis.adapter.Adapter_Monitoring;
import com.kbs.pocis.model.Model_Complain;

import java.util.ArrayList;
import java.util.List;

public class Complain_List extends Fragment {

    int status;
    public Complain_List(int type){
        status=type;
    }

    View view;
    RecyclerView recyclerView;
    List<Model_Complain> model_complains;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.complain_list,container,false);
        recyclerView = view.findViewById(R.id.complain_list);
        model_complains = new ArrayList<>();
        checkStatus();
        Adapter_Complain adapter_project_list = new Adapter_Complain(getContext(), model_complains);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_project_list);

        return view;
    }

    public void checkStatus(){
        switch (status){
            case 0:
                model_complains.add(new Model_Complain("OPEN","1","18 March 2020 22:00","DERMAGA 2","","Kebersihan Gudang",""));
                model_complains.add(new Model_Complain("OPEN","2","19 March 2020 22:00","DERMAGA 1","","Kebersihan Gudang",""));
                model_complains.add(new Model_Complain("OPEN","3","20 March 2020 22:00","DERMAGA 3","","Kebersihan Gudang",""));
                break;
            case 1:
                model_complains.add(new Model_Complain("CLOSED","1","18 March 2020 22:00","DERMAGA 2","","Kebersihan Dermaga",""));
                model_complains.add(new Model_Complain("CLOSED","2","19 March 2020 22:00","DERMAGA 1","","Kebersihan Dermaga",""));
                model_complains.add(new Model_Complain("CLOSED","3","20 March 2020 22:00","DERMAGA 3","","Kebersihan Dermaga",""));
                model_complains.add(new Model_Complain("CLOSED","2","19 March 2020 22:00","DERMAGA 1","","Kebersihan Dermaga",""));
                model_complains.add(new Model_Complain("CLOSED","3","20 March 2020 22:00","DERMAGA 3","","Kebersihan Dermaga",""));
                break;
        }
    }
}
