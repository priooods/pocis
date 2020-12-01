package com.kbs.pocis.onlineboking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.onlineboking.Adapter_TarifApproved;
import com.kbs.pocis.api.UserService;
import com.kbs.pocis.model.onlineboking.Model_TariffAprove;
import com.kbs.pocis.service.onlinebooking.CallingData;
import com.kbs.pocis.service.UserData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class TarifApprove extends Fragment {

    RecyclerView recyclerView;
    Adapter_TarifApproved adapter_tarifApproved;
    RelativeLayout layout_ada, layout_kosong;
    ImageView btn_back;
    ConstraintLayout bar;
    TextView kanan, kiri, kanan_banget, kiri_banget,
            index_list_allboking, all_index_allboking;
    List<Model_TariffAprove> model_tariffAproveList;
    NestedScrollView nestdall;

    int page_current = 1, page_last = 2;
    boolean Ready;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tarif_approve, container,false);

        btn_back = view.findViewById(R.id.btn_back_tarif_approve);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        kanan = view.findViewById(R.id.kanan_tarifapprove);
        kiri = view.findViewById(R.id.kiri_tarifapprove);
        kanan_banget = view.findViewById(R.id.kanan_banget_tarifapprove);
        kiri_banget = view.findViewById(R.id.kiri_banget_tarifapprove);
        bar = view.findViewById(R.id.all_index_bar);
        index_list_allboking = view.findViewById(R.id.index_list_tarifapprove);
        all_index_allboking = view.findViewById(R.id.all_index_tarifapprove);
        nestdall = view.findViewById(R.id.nest_tarif);
        layout_kosong = view.findViewById(R.id.lay_tarifapprove_kosong);
        layout_ada = view.findViewById(R.id.lay_tarifapprove_ada);
        recyclerView = view.findViewById(R.id.tarif_approve_recycleview);


        GenerateList();
        ganti();

        return view;
    }

    void ScrolltoTop(){
        nestdall.fullScroll(View.FOCUS_UP);
        nestdall.smoothScrollTo(0,0);
    }

    void ChangePage(int target_page){
        if (Ready) {
            page_current = target_page;
            GenerateList();
            Ready = false;
        }else{
            Log.w("tarif_aprrove","Aggresive Touch/Command!");
        }
    }

    void ganti(){
        kanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePage(page_current + 1);
            }
        });
        kanan_banget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePage(page_last);
            }
        });
        kiri_banget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePage(1);
            }
        });
        kiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePage(page_current-1);
            }
        });
    }

    /// Fungsi untuk membuat list
    void GenerateList(){
        UserData user = UserData.i;
        Log.i(TAG, "tarif_aprrove: token "+ user.getToken());
        UserService service = user.getService();
        if (service == null) {
            Log.e("tarif_aprrove","ERROR SERVICE");
        }
        Call<CallingData> call = service.getTariffAprove(user.getToken(),String.valueOf(page_current));
        if (call == null) {
            Log.i("tarif_aprrove","CallingData Post Method is Bad!");
        }
        call.enqueue(new Callback<CallingData>() {
            @Override
            public void onResponse(Call<CallingData> call, Response<CallingData> response) {
                Ready = true;
                CallingData respone = (CallingData) response.body();
                if (CallingData.TreatResponse(getContext(), "tarif_aprrove", respone)) {
                    List<Model_TariffAprove> list = new ArrayList<>();

                    for (CallingData.Booking datas : respone.data.book) {
                        list.add(datas.getTariff());
                    }

                    model_tariffAproveList = list;
                    page_current = respone.data.current_page;
                    page_last = respone.data.last_page;

                    //region KONTROL VISIBILITAS KOMPONEN
                    bar.setVisibility(View.VISIBLE);
                    all_index_allboking.setVisibility(View.VISIBLE);

                    ScrolltoTop();
                    SetVisibility(kiri, page_current>1);
                    SetVisibility(kiri_banget, page_current>2);
                    SetVisibility(kanan, page_current<page_last);
                    SetVisibility(kanan_banget, page_current+1<page_last);

                    //endregion
                    index_list_allboking.setText(page_current + " of " + page_last);
                    all_index_allboking.setText("Showing "+ (respone.data.to_page - respone.data.from_page + 1) + " of " + respone.data.total + " results");

                    if (model_tariffAproveList !=null ? model_tariffAproveList.size()>0 : false) {
                        layout_ada.setVisibility(View.VISIBLE);
                        layout_kosong.setVisibility(View.GONE);
                        adapter_tarifApproved = new Adapter_TarifApproved(getContext(), model_tariffAproveList);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//                        adapter_tarifApproved.getFilter().filter(user.filter);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter_tarifApproved);
                    } else {
                        layout_ada.setVisibility(View.GONE);
                        layout_kosong.setVisibility(View.VISIBLE);
                        Log.e("TAG", "onResponse: " + " Layout nya kosong" );
                    }
                    return;
                } else {
                    Log.e("tarif_aprrove", "Failed : \n Error " + respone.error + " : " + respone.desc);
                }
                bar.setVisibility(View.INVISIBLE);
                all_index_allboking.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onFailure(Call<CallingData> call, Throwable t) {
                Ready = true;
                Log.e("tarif_aprrove", "on Failure called!"+ t);
            }
        });
    }

    private void SetVisibility(android.widget.TextView comp, boolean condition){
        comp.setVisibility(condition?View.VISIBLE:View.INVISIBLE);
        comp.setEnabled(condition);
    };
}
