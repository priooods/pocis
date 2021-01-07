package com.kbs.pocis.progressbook;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.detailbooking.CallingDetail;
import com.kbs.pocis.service.onlinebooking.CallingData;
import com.xyz.step.FlowViewHorizontal;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Progress_Booking extends Fragment {

    ImageView icon_back;
    TextView tem_proj, date_isue, title_1,title_2,title_3,title_4, desc_1,desc_2,desc_3,desc_4;
    ImageView round_1,round_2,round_3,round_4;
    View line1,line2,line3;
    LinearLayout ln_pro;
    RelativeLayout lay_kosong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_booking, container, false);
        tem_proj = view.findViewById(R.id.item1);
        lay_kosong = view.findViewById(R.id.lay_kosong);
        ln_pro = view.findViewById(R.id.ln_pro);
        date_isue = view.findViewById(R.id.item2);
        title_1 = view.findViewById(R.id.title_1);
        title_2 = view.findViewById(R.id.title_2);
        title_3 = view.findViewById(R.id.title_3);
        title_4 = view.findViewById(R.id.title_4);
        desc_1 = view.findViewById(R.id.desc_book);
        desc_2 = view.findViewById(R.id.desc_ppj);
        desc_3 = view.findViewById(R.id.desc_bapj);
        desc_4 = view.findViewById(R.id.desc_invoice);
        round_1 = view.findViewById(R.id.round_book);
        round_2 = view.findViewById(R.id.round_ppj);
        round_3 = view.findViewById(R.id.round_bapj);
        round_4 = view.findViewById(R.id.round_invoice);
        line1 = view.findViewById(R.id.ln_book);
        line2 = view.findViewById(R.id.ln_ppj);
        line3 = view.findViewById(R.id.ln_bapj);

        icon_back = view.findViewById(R.id.icon_back);
        icon_back.setOnClickListener(v->requireActivity().onBackPressed());

        Model_Project data = Model_Project.mp;
        tem_proj.setText(data.no_booking);
        date_isue.setText(data.created);
        progresDetails();


        return view;
    }

    private void progresDetails(){
        Model_Project data = Model_Project.mp;
        Call<CallingDetail> call = UserData.i.getService().progressDetail(UserData.i.getToken(),data.no_booking);
        call.enqueue(new Callback<CallingDetail>() {
            @Override
            public void onResponse(@NotNull Call<CallingDetail> call, @NotNull Response<CallingDetail> response) {
                CallingDetail data = response.body();
                if (Calling.TreatResponse(getContext(),"progress_detail", data)){
                    assert data !=null;
                    Log.i(TAG, "onResponse: " + data.data.List.size());
                    if (data.data.List.size() > 0){
                        ln_pro.setVisibility(View.VISIBLE);
                        if (data.data.List.get(0) != null){
                            desc_1.setText(data.data.List.get(0).description);
                            line1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        }
                        // for dua
                        if (data.data.List.get(1) != null){
                            title_2.setTextColor(getResources().getColor(R.color.colorPrimary));
                            desc_2.setText(data.data.List.get(1).description);
                            line2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            round_2.setBackground(getResources().getDrawable(R.drawable.circle_bg_blue));
                        } else {
                            title_2.setTextColor(getResources().getColor(R.color.colorGrey));
                            desc_2.setVisibility(View.GONE);
                            line2.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                            round_2.setBackground(getResources().getDrawable(R.drawable.circle_bg_grey));
                        }

                        // for tiga
                        if (data.data.List.get(2) != null){
                            title_3.setTextColor(getResources().getColor(R.color.colorPrimary));
                            desc_3.setText(data.data.List.get(1).description);
                            line3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                            round_3.setBackground(getResources().getDrawable(R.drawable.circle_bg_blue));
                        } else {
                            title_3.setTextColor(getResources().getColor(R.color.colorGrey));
                            desc_3.setVisibility(View.GONE);
                            line3.setBackgroundColor(getResources().getColor(R.color.colorGrey));
                            round_3.setBackground(getResources().getDrawable(R.drawable.circle_bg_grey));
                        }

                        //for four
                        if (data.data.List.get(3) != null){
                            title_4.setTextColor(getResources().getColor(R.color.colorPrimary));
                            desc_4.setText(data.data.List.get(1).description);
                            round_4.setBackground(getResources().getDrawable(R.drawable.circle_bg_blue));
                        } else {
                            title_4.setTextColor(getResources().getColor(R.color.colorGrey));
                            desc_4.setVisibility(View.GONE);
                            round_4.setBackground(getResources().getDrawable(R.drawable.circle_bg_grey));
                        }
                    } else {
                        lay_kosong.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingDetail> call, @NotNull Throwable t) {
                Log.i("progres_detail", "onFailure: " + t);
            }
        });
    }
}
