package com.kbs.pocis.myproject.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Project;

public class Informations extends Fragment {

    //title change
    TextView title1,title2,title3,title4,title5,title6,title7,title8,title9,title10,
            item1,item2,item3,item4,item5,item6,item7,item8,item9,item10,
            depart_group, billpayment, payment_type, va_number;

    LinearLayout ln5,ln6,ln7,ln8,ln9,ln10, ln_bottom;
    View line_1,line_2;
    ConstraintLayout ln_middle;
    CardView cardView;
    ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_information, container, false);

        cardView = view.findViewById(R.id.b);
        progressBar = view.findViewById(R.id.progress);

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
        ln5 = view.findViewById(R.id.ln5);
        ln6 = view.findViewById(R.id.ln6);
        ln7 = view.findViewById(R.id.ln7);
        ln8 = view.findViewById(R.id.ln8);
        ln9 = view.findViewById(R.id.ln9);
        ln10 = view.findViewById(R.id.ln10);

        ln_middle = view.findViewById(R.id.cc);
        line_1 = view.findViewById(R.id.bb);
        line_2 = view.findViewById(R.id.dd);
        ln_bottom = view.findViewById(R.id.ee);

        billpayment = view.findViewById(R.id.detail_project_bilpayment);
        payment_type = view.findViewById(R.id.detail_project_paymenttype);
        va_number = view.findViewById(R.id.detail_project_number);
        depart_group = view.findViewById(R.id.depart_group);

        MyProject();

        return view;
    }

    public void MyProject(){
        Model_Project data = Model_Project.mp;
        switch (Model_Project.Code){
            case 0: //for approval
                title1.setText(R.string.customername);
                title3.setText(R.string.vesselname);
                title5.setText(R.string.related_vesel);
                title6.setText(R.string.payment_type);
                title7.setText(R.string.flag);
                title8.setText(R.string.tonage);
                title9.setText(R.string.voyage);
                ln10.setVisibility(View.GONE);

                item1.setText(Model_Project.Information.get(0).customer_name);
                item2.setText(Model_Project.Information.get(0).start_date);
                item3.setText(Model_Project.Information.get(0).vessel_name);
                item4.setText(Model_Project.Information.get(0).vessel_name);
                item5.setText(Model_Project.Information.get(0).related_vessel);
                item6.setText(Model_Project.Information.get(0).payment_type);
                item7.setText(Model_Project.Information.get(0).flag_compound);
                item8.setText(Model_Project.Information.get(0).tonnage);
                item9.setText(Model_Project.Information.get(0).voyage_no);
                depart_group.setText(Model_Project.Information.get(0).department_group);
                break;
            case 2: //for bpaj
                title1.setText(R.string.customername);
                title3.setText(R.string.vesselname);
                title5.setText(R.string.related_vesel);
                title6.setText(R.string.payment_type);
                title7.setText(R.string.flag);
                title8.setText(R.string.tonage);
                title9.setText(R.string.voyage);
                ln10.setVisibility(View.GONE);

                item1.setText(Model_Project.Information.get(0).customer_name);
                item2.setText(Model_Project.Information.get(0).start_date);
                item3.setText(Model_Project.Information.get(0).vessel_name);
                item4.setText(Model_Project.Information.get(0).end_date);
                item5.setText(Model_Project.Information.get(0).related_vessel);
                item6.setText(Model_Project.Information.get(0).payment_type);
                item7.setText(Model_Project.Information.get(0).flag_compound);
                item8.setText(Model_Project.Information.get(0).tonnage);
                item9.setText(Model_Project.Information.get(0).voyage_no);
                depart_group.setText(Model_Project.Information.get(0).department_group_name);
                break;
            case 1: //for list
                title1.setText(R.string.booking_no);
                title3.setText(R.string.schedule);
                title6.setText(R.string.customername);
                item1.setText(data.no_booking);
                item2.setText(data.start_date);
                item3.setText(data.schedule_code);
                item4.setText(data.end_date);
                item5.setText(Model_Project.Information.get(0).tonnage);
                item6.setText(data.customer_name);
                item7.setText(Model_Project.Information.get(0).related_vessel);
                item8.setText(data.vessel_name);
                item9.setText(Model_Project.Information.get(0).flag_compound);
                item10.setText(Model_Project.Information.get(0).voyage_no);

                billpayment.setText(Model_Project.Information.get(0).bill_paymemt_number);
                ln_middle.setVisibility(View.VISIBLE);
                line_1.setVisibility(View.VISIBLE);
                payment_type.setText(Model_Project.Information.get(0).payment_type);
                va_number.setText(Model_Project.Information.get(0).va_number);
                depart_group.setText(Model_Project.Information.get(0).department_group_name);
                break;
            case 3: //for invoice
                title1.setText(R.string.project_no);
                title2.setText(R.string.customername);
                title3.setText(R.string.booking_no);
                title4.setText(R.string.vesselname);
                title5.setText(R.string.voyage);
                ln6.setVisibility(View.GONE);
                ln7.setVisibility(View.GONE);
                ln8.setVisibility(View.GONE);
                ln9.setVisibility(View.GONE);
                ln10.setVisibility(View.GONE);

                item1.setText(data.project_no);
                item2.setText(data.customer_name);
                item3.setText(data.booking_no);
                item4.setText(data.vessel_name);
                item5.setText(Model_Project.InformationAndDocument.get(0).voyage_no);

                ln_middle.setVisibility(View.GONE);
                line_1.setVisibility(View.GONE);
                line_2.setVisibility(View.GONE);
                ln_bottom.setVisibility(View.GONE);
                break;
            case 4: //for proforma
                title1.setText(R.string.temp_proj);
                title2.setText(R.string.customername);
                title3.setText(R.string.tonage);
                title4.setText(R.string.vesselname);
                title5.setText(R.string.voyage);
                title6.setText(R.string.related_vesel);
                title7.setText(R.string.start_date);
                title8.setText(R.string.flag);
                title9.setText(R.string.end_date);
                title10.setText(R.string.cancel_stat);

                item1.setText(data.temp_project_no);
                item2.setText(data.customer_name);
                item3.setText(Model_Project.InformationAndDocument.get(0).tonnage);
                item4.setText(Model_Project.InformationAndDocument.get(0).vessel_name);
                item5.setText(Model_Project.InformationAndDocument.get(0).voyage_no);
                item6.setText(Model_Project.InformationAndDocument.get(0).related_vessel);
                item7.setText(Model_Project.InformationAndDocument.get(0).start_date);
                item8.setText(Model_Project.InformationAndDocument.get(0).flag_compound);
                item9.setText(Model_Project.InformationAndDocument.get(0).end_date);
                item10.setText(data.status_cancel);
                depart_group.setText(Model_Project.InformationAndDocument.get(0).department_group);
                break;
        }
    }
}
