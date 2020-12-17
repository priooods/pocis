package com.kbs.pocis.invoice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.Adapter_Project;
import com.kbs.pocis.model.Model_Project;

import java.util.ArrayList;
import java.util.List;

public class Performa extends Fragment {

    RecyclerView recyclerView;
    List<Model_Project> model_project_s;
    ImageView search_icon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_performa, container, false);

        search_icon = view.findViewById(R.id.btn_search_performa);
        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                OpenFilter(getContext());
            }
        });
        recyclerView = view.findViewById(R.id.list_performa);
        model_project_s = new ArrayList<>();
        model_project_s.add(new Model_Project("P0019-2020-00021","PAID","PPJ/P0019-288/01","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",1,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        model_project_s.add(new Model_Project("P0019-2020-00022","UNPAID","PPJ/P0019-288/02","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",2,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        model_project_s.add(new Model_Project("P0019-2020-00023","UNPAID","PPJ/P0019-288/03","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",3,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));

        Adapter_Project adapter_project_list = new Adapter_Project(getContext(), model_project_s, 4);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_project_list);

        return view;
    }
}
