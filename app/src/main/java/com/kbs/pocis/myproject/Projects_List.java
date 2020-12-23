package com.kbs.pocis.myproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.adapter.Adapter_Project;
import com.kbs.pocis.filter.FilterFragment;
import com.kbs.pocis.model.Model_Project;

import java.util.ArrayList;

public class Projects_List extends FilterFragment {

    RecyclerView recyclerView;
    TextView title;
    ArrayList<Model_Project> model_project_s;

    int list_status;
    public Projects_List(int type){
        list_status = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);

        title = view.findViewById(R.id.title);
        recyclerView = view.findViewById(R.id.list_project_open);
        CheckByStatus();
        return view;
    }

    public void CheckByStatus(){
        switch (list_status){
            case 0:
                ListOpen();
                break;
            case 1:
                ListClosed();
                break;
            case 2:
                ListAll();
                break;
        }
    }

    void ListClosed(){
        title.setText("Showing all list of closed projects. Tap to see details.");
        model_project_s = new ArrayList<>();
        model_project_s.add(new Model_Project("P0019-2020-00021","CLOSED","PPJ/P0019-288/01","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",1,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        model_project_s.add(new Model_Project("P0019-2020-00022","CLOSED","PPJ/P0019-288/02","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",1,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        Adapter_Project adapter_project_list = new Adapter_Project(getContext(), model_project_s,1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_project_list);
    }

    void ListOpen(){
        title.setText("Showing all list of open projects. Tap to see details.");
        model_project_s = new ArrayList<>();
        model_project_s.add(new Model_Project("P0019-2020-00021","OPEN","PPJ/P0019-288/01","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",1,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        model_project_s.add(new Model_Project("P0019-2020-00022","OPEN","PPJ/P0019-288/02","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",1,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        model_project_s.add(new Model_Project("P0019-2020-00023","OPEN","PPJ/P0019-288/03","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",1,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        model_project_s.add(new Model_Project("P0019-2020-00021","OPEN","PPJ/P0019-288/01","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",1,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        model_project_s.add(new Model_Project("P0019-2020-00022","OPEN","PPJ/P0019-288/02","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",1,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        model_project_s.add(new Model_Project("P0019-2020-00023","OPEN","PPJ/P0019-288/03","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",1,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        Adapter_Project adapter_project_list = new Adapter_Project(getContext(), model_project_s,1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_project_list);
    }

    void ListAll(){
        title.setVisibility(View.GONE);
        model_project_s = new ArrayList<>();
        model_project_s.add(new Model_Project("P0019-2020-00021","CLOSED","PPJ/P0019-288/01","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",1,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        model_project_s.add(new Model_Project("P0019-2020-00022","OPEN","PPJ/P0019-288/02","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",1,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        model_project_s.add(new Model_Project("P0019-2020-00023","OPEN","PPJ/P0019-288/03","H9JY01002","PPJ-2020/15550",
                "16 September 2020 13:02","20 September 2020 13:02","Not Available Yet","5 September 2020 11:52","No","Full Payment","No",
                "39,977.600","1145D","0216055983811","8923716055987308","Subdit Operasi Kepelabuhanan (Operasi Internal)","IDR 14,200","MV. BBC Congo",
                "02518/BAPJ-JPL/III/2020",1,"BUA01-000295","PPJ-2020/01495","PT Krakatau Steel","YES","Total Payment","19 March 2020 00:00","Bill Payment",
                "YES","2020-11-27","Quay/Dermaga"));
        Adapter_Project adapter_project_list = new Adapter_Project(getContext(), model_project_s,1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_project_list);

    }

}