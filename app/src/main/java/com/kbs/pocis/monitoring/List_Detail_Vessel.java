package com.kbs.pocis.monitoring;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Project;

import static android.content.ContentValues.TAG;

public class List_Detail_Vessel extends Fragment {

    View view;
    int page;
    public List_Detail_Vessel(int type){
        page = type;
    }

    TextView item1,item2,item3,item4,item5,item6,item7,item8,item9,item10, item_info1,item_info2,item_info3,item_info4;
    TextView title1,title2,title3,title4,title5,title6,title7,title8,title9,title10;
    TextView i1,i2,i3,i4,i5,i6,i7;
    LinearLayout item_info0, ln6,ln7,ln8,ln9,ln10, progress;
    ConstraintLayout ln_time7;
    CardView cardTime, cardTop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_list_vessel,container,false);

        cardTime = view.findViewById(R.id.card2);
        progress = view.findViewById(R.id.progress);
        i1 = view.findViewById(R.id.item_time1);
        i2 = view.findViewById(R.id.item_time2);
        i3 = view.findViewById(R.id.item_time3);
        i4 = view.findViewById(R.id.item_time4);
        i5 = view.findViewById(R.id.item_time5);
        i6 = view.findViewById(R.id.item_time6);
        i7 = view.findViewById(R.id.item_time7);
        ln_time7 = view.findViewById(R.id.ln_time7);
        cardTop = view.findViewById(R.id.card);
        item1 = view.findViewById(R.id.item1);
        item2 = view.findViewById(R.id.item2);
        item3 = view.findViewById(R.id.item3);
        item4 = view.findViewById(R.id.item4);
        item5 = view.findViewById(R.id.item5);
        item6 = view.findViewById(R.id.item6);
        item7 = view.findViewById(R.id.item7);
        item8 = view.findViewById(R.id.item8);
        item9 = view.findViewById(R.id.item9);
        item10 = view.findViewById(R.id.item10);
        title1 = view.findViewById(R.id.title1);
        title2 = view.findViewById(R.id.title2);
        title3 = view.findViewById(R.id.title3);
        title4 = view.findViewById(R.id.title4);
        title5 = view.findViewById(R.id.title5);
        title6 = view.findViewById(R.id.title6);
        title7 = view.findViewById(R.id.title7);
        title8 = view.findViewById(R.id.title8);
        title9 = view.findViewById(R.id.title9);
        title10 = view.findViewById(R.id.title10);
        item_info0 = view.findViewById(R.id.ln_info0);
        item_info1 = view.findViewById(R.id.item_info1);
        item_info2 = view.findViewById(R.id.item_info2);
        item_info3 = view.findViewById(R.id.item_info3);
        item_info4 = view.findViewById(R.id.item_info4);

        ln6 = view.findViewById(R.id.ln6);
        ln7 = view.findViewById(R.id.ln7);
        ln8 = view.findViewById(R.id.ln8);
        ln9 = view.findViewById(R.id.ln9);
        ln10 = view.findViewById(R.id.ln10);

        checkPage();

        return view;
    }

    public void checkPage(){
        Model_Project data = Model_Project.mp;
        switch (page){
            case 0: // for jetty
                title1.setText(R.string.jetty_code);
                title2.setText(R.string.description);
                title3.setText(R.string.length);
                title4.setText(R.string.inapORT);
                title5.setText(R.string.death_Weight);
                title6.setText(R.string.lowest_water);
                item1.setText(data.jetty_code);
                item2.setText(data.jetty_name);
                item3.setText(data.jetty_length);
                item4.setText(data.jetty_code_inaport);
                item5.setText(data.jetty_dwt);
                item6.setText(data.lowes_water_spring);
                ln7.setVisibility(View.GONE);
                ln8.setVisibility(View.GONE);
                ln9.setVisibility(View.GONE);
                ln10.setVisibility(View.GONE);
                break;
            case 1: //for vessel
                title1.setText(R.string.call_sign);
                title2.setText(R.string.mMSI);
                title3.setText(R.string.name);
                title4.setText(R.string.gross_Tonnage);
                title5.setText(R.string.length_overall);
                title6.setText(R.string.death_Weight);
                title7.setText(R.string.draft);
                title8.setText(R.string.vessel_type);
                title9.setText(R.string.year);
                title10.setText(R.string.vessel_registered);
                item1.setText(data.vessel_call_sign);
                item2.setText(data.vessel_mmsi_code);
                item3.setText(data.vessel_name);
                item4.setText(data.vessel_gt);
                item5.setText(data.vessel_length_overall);
                item6.setText(data.vessel_dwt);
                item7.setText(data.vessel_draft);
                item8.setText(data.vessel_type);
                item9.setText(data.vessel_year);
                item10.setText(data.vessel_registered_no);
                break;
            case 2: // for berth
                title1.setText(R.string.schedule);
                title2.setText(R.string.agent_name);
                title3.setText(R.string.voyage);
                title4.setText(R.string.route);
                title5.setText(R.string.discharge_loading);
                item1.setText(data.schedule_code);
                item2.setText(data.agent_name);
                item3.setText(data.voyage_no);
                item4.setText(data.rute);
                item5.setText(data.discharge_loading);
                ln6.setVisibility(View.GONE);
                ln7.setVisibility(View.GONE);
                ln8.setVisibility(View.GONE);
                ln9.setVisibility(View.GONE);
                ln10.setVisibility(View.GONE);
                item_info0.setVisibility(View.VISIBLE);
                item_info1.setText(data.origin_port_name);
                item_info2.setText(data.next_port_name);
                item_info3.setText(data.dest_port_name);
                item_info4.setText(data.last_port_name);
                break;
            case 3:
                cardTop.setVisibility(View.GONE);
                cardTime.setVisibility(View.VISIBLE);
                i1.setText(data.est_arrival);
                i2.setText(data.est_anchorage);
                i3.setText(data.est_berthing);
                i4.setText(data.est_start_work);
                i5.setText(data.est_end_work);
                i6.setText(data.est_departure);
                ln_time7.setVisibility(View.GONE);
                break;
        }
    }
}
