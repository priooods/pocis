package com.kbs.pocis.monitoring;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Monitoring;

public class List_Detail_Vessel extends Fragment {

    View view;
    int page;
    public List_Detail_Vessel(int type){
        page = type;
    }

    TextView item1,item2,item3,item4,item5,item6,item7,item8,item9,item10, item_info1,item_info2,item_info3,item_info4;
    TextView title1,title2,title3,title4,title5,title6,title7,title8,title9,title10;
    LinearLayout item_info0, ln6,ln7,ln8,ln9,ln10;
    CardView cardTime, cardTop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_list_vessel,container,false);

        cardTime = view.findViewById(R.id.card2);
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

    private void checkPage(){
        if (Model_Monitoring.isExist()){
            Model_Monitoring data = Model_Monitoring.mn;
            switch (page){
                case 0: // for jetty
                    title1.setText(R.string.jetty_code);
                    title2.setText(R.string.description);
                    title3.setText(R.string.length);
                    title4.setText(R.string.inapORT);
                    title5.setText(R.string.death_Weight);
                    title6.setText(R.string.lowest_water);
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
                    break;
                case 2: // for berth
                    title1.setText(R.string.schedule);
                    title2.setText(R.string.agent_name);
                    title3.setText(R.string.voyage);
                    title4.setText(R.string.route);
                    title5.setText(R.string.discharge_loading);
                    ln6.setVisibility(View.GONE);
                    ln7.setVisibility(View.GONE);
                    ln8.setVisibility(View.GONE);
                    ln9.setVisibility(View.GONE);
                    ln10.setVisibility(View.GONE);
                    item_info0.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    cardTop.setVisibility(View.GONE);
                    cardTime.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
