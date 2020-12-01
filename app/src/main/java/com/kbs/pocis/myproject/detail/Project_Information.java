package com.kbs.pocis.myproject.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.kbs.pocis.R;

import static android.content.ContentValues.TAG;

public class Project_Information extends Fragment {

    String consigne, vesel, tonag;

    //title change
    TextView title_consigne_or_bok, title_vesel_or_schedule, title_related_or_tonage,
        title_payment_or_consigne, title_flagcompond_or_related, title_tonage_or_vesel,
        title_voyage_or_flagcompond;

    //title value
    TextView  consigne_or_bokingNo, vesel_or_schedule, related_or_tonage, payment_or_consinge,
            flagcompond_or_related, tonage_or_vesel, voyage_or_flagcompond, startDate, endDate,
            department_group, bill_payment, payment_type, vAnumber;

    //layout when id change
    LinearLayout  layout_voyage_hiden;
    View line_hidden;
    ConstraintLayout layout_info_bottom_hiden;

    //getCode Tempate
    int codetemplate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myproject_information, container, false);

        /* Code Template = 1 . for template project Approval
        * Code Template = 2 . for template project List */
        codetemplate = getActivity().getIntent().getIntExtra("get", 0);
        Log.i(TAG, "id in information: => " + codetemplate);

        //Please don't change
        //introduction value global. inside aproval and projectList
        startDate = view.findViewById(R.id.detail_project_info_startDate);
        endDate = view.findViewById(R.id.detail_project_info_endDate);
        department_group = view.findViewById(R.id.detail_project_info_department);
        bill_payment = view.findViewById(R.id.detail_project_bilpayment);
        payment_type = view.findViewById(R.id.detail_project_paymenttype);
        vAnumber = view.findViewById(R.id.detail_project_number);

        //Please don't change
        //introduction title change
        title_consigne_or_bok = view.findViewById(R.id.title_consigne_and_bookNo);
        title_vesel_or_schedule = view.findViewById(R.id.title_schedule_and_veselname);
        title_related_or_tonage = view.findViewById(R.id.title_related_and_tonage);
        title_payment_or_consigne = view.findViewById(R.id.title_payment_and_consigne);
        title_flagcompond_or_related = view.findViewById(R.id.title_flag_and_related);
        title_tonage_or_vesel = view.findViewById(R.id.title_tonage_and_vesel);
        title_voyage_or_flagcompond = view.findViewById(R.id.title_voyage_and_flagcompond);

        //Please don't change
        //introduction Layout Visible by codeTemplate
        line_hidden = view.findViewById(R.id.xx);
        layout_info_bottom_hiden = view.findViewById(R.id.xc);
        layout_voyage_hiden = view.findViewById(R.id.ln10);

        //Please don't change
        //introduction value change
        consigne_or_bokingNo = view.findViewById(R.id.detail_project_info_consigne_and_bookNo);
        vesel_or_schedule = view.findViewById(R.id.detail_project_info_schedule_and_veselname);
        related_or_tonage = view.findViewById(R.id.detail_project_info_related_and_tonage);
        payment_or_consinge = view.findViewById(R.id.detail_project_info_payment_and_consigne);
        flagcompond_or_related = view.findViewById(R.id.detail_project_info_flag_and_related);
        tonage_or_vesel = view.findViewById(R.id.detail_project_info_tonage_and_vesel);
        voyage_or_flagcompond = view.findViewById(R.id.detail_project_info_voyage_and_flagcompond);


        //Getting API data in here

        //function change title and value
        SettingTitleChange(codetemplate);


        return view;
    }

    void SettingTitleChange(int code){
        if (code == 1){
            //setting default title when id template = project approval
            title_consigne_or_bok.setText("Consigne Name");
            title_vesel_or_schedule.setText("Vessel Name");
            title_related_or_tonage.setText("Related Vessel");
            title_payment_or_consigne.setText("Payment Type");
            title_flagcompond_or_related.setText("Flag Compound");
            title_tonage_or_vesel.setText("Tonage");
            title_voyage_or_flagcompond.setText("Voyage No.");
            layout_voyage_hiden.setVisibility(View.GONE);

            //setting value title when id template = project approval
            consigne_or_bokingNo.setText("PT Krakatu Steel");
            vesel_or_schedule.setText("MV. Sea Master");
            related_or_tonage.setText("No");
            payment_or_consinge.setText("Full Payment");
            flagcompond_or_related.setText("No");
            tonage_or_vesel.setText("39,977.600");
            voyage_or_flagcompond.setText("1145D");
        } else if (code == 2){

            line_hidden.setVisibility(View.VISIBLE);
            layout_info_bottom_hiden.setVisibility(View.VISIBLE);

            title_consigne_or_bok.setText("Booking No.");
            title_vesel_or_schedule.setText("Schedule Code");
            title_related_or_tonage.setText("Tonage");
            title_payment_or_consigne.setText("Consignee Name");
            title_flagcompond_or_related.setText("Related Vessel");
            title_tonage_or_vesel.setText("Vessel Name");
            title_voyage_or_flagcompond.setText("Flag Compound");
        }
    }
}
