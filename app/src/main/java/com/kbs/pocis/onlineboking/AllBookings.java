package com.kbs.pocis.onlineboking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.onlineboking.Adapter_AllBooking;
import com.kbs.pocis.filter.FilterFragment;
import com.kbs.pocis.model.onlineboking.Model_Bookings;
import com.kbs.pocis.service.onlinebooking.CallingData;
import com.kbs.pocis.service.UserData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AllBookings extends FilterFragment {

    Adapter_AllBooking adapter_allBooking;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    RelativeLayout  layout_kosong;
    ConstraintLayout bar;
    TextView kanan, kiri, kanan_banget, kiri_banget,
            index_list_allboking, all_index_allboking;
    NestedScrollView layout_ada;
    int total_item = 0;
    List<Model_Bookings> model_bookingsList;
    int booking_type;

    public AllBookings(int type) {
        booking_type = type;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (booking_type == 0) {
            view = inflater.inflate(R.layout.fragment_all_bookings, container, false);
            recyclerView = view.findViewById(R.id.recycle_Allbooking);
            kanan = view.findViewById(R.id.kanan);
            progressBar = view.findViewById(R.id.progress);
            kiri = view.findViewById(R.id.kiri);
            kanan_banget = view.findViewById(R.id.kanan_banget);
            kiri_banget = view.findViewById(R.id.kiri_banget);
            bar = view.findViewById(R.id.all_index_bar);
            index_list_allboking = view.findViewById(R.id.index_list_allboking);
            all_index_allboking = view.findViewById(R.id.all_index_allboking);
            layout_kosong = view.findViewById(R.id.lay_allbooking_kosong);
            layout_ada = view.findViewById(R.id.lay_allbooking_ada);
        } else {
            view = inflater.inflate(R.layout.fragment_cancel_booking, container, false);
            kanan = view.findViewById(R.id.kanan_cancel);
            kiri = view.findViewById(R.id.kiri_cancel);
            progressBar = view.findViewById(R.id.progress);
            kanan_banget = view.findViewById(R.id.kanan_banget_cancel);
            kiri_banget = view.findViewById(R.id.kiri_banget_cancel);
            bar = view.findViewById(R.id.all_index_bar_cancel);
            index_list_allboking = view.findViewById(R.id.index_list_cancelboking);
            all_index_allboking = view.findViewById(R.id.all_index_cancelboking);
            recyclerView = view.findViewById(R.id.recycle_Cancelbooking);
            layout_ada = view.findViewById(R.id.lay_cancelbooking_ada);
            layout_kosong = view.findViewById(R.id.lay_cancelbooking_kosong);
        }


        filter = null;
        max_list = 5;
        GenerateLists();
        Ganti();

        return view;
    }

    void ChangePage(int target_page) {
        if (Ready) {
            page_current = target_page;
            GenerateLists();
            Ready = false;
        } else {
            Log.w("all_booking", "Aggresive Touch/Command!");
        }
    }

    public void Ganti() {
        kanan.setOnClickListener(view -> ChangePage(page_current + 1));
        kanan_banget.setOnClickListener(view -> ChangePage(page_last));
        kiri_banget.setOnClickListener(view -> ChangePage(1));
        kiri.setOnClickListener(view -> ChangePage(page_current - 1));
    }

    @Override
    protected void LoadingBar(boolean stat){
        if (stat){
            progressBar.setVisibility(View.VISIBLE);
            layout_ada.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            layout_ada.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void Model_CheckorClear() {
        if (model_bookingsList == null)
            model_bookingsList = new ArrayList<>();
        else
            model_bookingsList.clear();
    }

    @Override
    protected void ShowAdapter() {
        if (model_bookingsList != null && model_bookingsList.size() > 0) {
            SetVisibility(kiri, page_current > 1);
            SetVisibility(kiri_banget, page_current > 2);
            SetVisibility(kanan, page_current < page_last);
            SetVisibility(kanan_banget, page_current + 1 < page_last);

            String of = page_current + "  Of  " + page_last;
            String show = "Showing " + (model_bookingsList.size()) + " of " + total_item + " results";
            index_list_allboking.setText(of);
            all_index_allboking.setText(show);

            adapter_allBooking = new Adapter_AllBooking(getContext(), model_bookingsList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter_allBooking);
        } else {
            layout_ada.setVisibility(View.GONE);
            layout_kosong.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void GenerateFilter(int page, int list) {
        Log.i("all_booking", "Call AllBooking page = " + page);
        Call<CallingData> call;
        if (booking_type == 0) {
            call = UserData.i.getService().getAllBooking(UserData.i.getToken(), String.valueOf(page));
        } else {
            call = UserData.i.getService().getAllCancel(UserData.i.getToken(), String.valueOf(page));
        }
        if (call == null) {
            Log.i("all_booking", "CallingData Post Method is Bad!");
        }
        assert call != null;
        call.enqueue(new Callback<CallingData>() {
            @Override
            public void onResponse(@NotNull Call<CallingData> call, @NotNull Response<CallingData> response) {
                CallingData respone = response.body();
                if (CallingData.TreatResponse(getContext(), "all_booking", respone)) {
                    if (!filtering) {
                        assert respone != null;
                        for (CallingData.Booking data : respone.data.book) {
                            model_bookingsList.add(data.getModel());
                        }
                        page_current = respone.data.current_page;
                        page_last = respone.data.last_page;
                        total_item = respone.data.total;
                        FinishFilter();
                    } else {
                        //This for Filter. Do not Editing Function. Edit Only model;
                        if (pmanager.loaded) {
                            Log.i("booking_load"," page = " + page);
                            assert respone != null;
                            CallingData.Booking[] data = respone.data.book;
                            for (int i = list; i < data.length; i++) {
                                if (filter.checkFilter(data[i])) {
                                    if (model_bookingsList.size() < pmanager.page_capacity) {
                                        model_bookingsList.add(data[i].getModel());
                                        Log.i("booking_load", data[i].readString());
                                    } else {
                                        page_last = pmanager.page_last;
                                        total_item = pmanager.total;
                                        load = false;
                                        FinishFilter();
                                        return;
                                    }
                                }
                            }
                            if (page < respone.data.last_page) {
                                GenerateFilter(page + 1, 0);
                            } else {
                                page_last = pmanager.page_last;
                                total_item = pmanager.total;
                                load = false;
                                FinishFilter();
                            }
                        } else {
                            Log.i("booking_load",  " page = " + page + " load = " + load);
                            int i = 0;
                            assert respone != null;
                            for (CallingData.Booking data : respone.data.book) {
                                if (filter.checkFilter(data)) {
                                    if (pmanager.addPack(page, i) && load) {
                                        model_bookingsList.add(data.getModel());
                                        Log.i("booking_load", data.readString());
                                    } else {
                                        load = false;
                                    }
                                }
                                i++;
                            }
                            if (page == respone.data.last_page) {
                                if (pmanager.pack > 0) {
                                    pmanager.finalPack(page, i - 1);
                                }
                                pmanager.finishLoad();
                                page_last = pmanager.page_last;
                                total_item = pmanager.total;
                                load = false;
                                layout_ada.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                ShowAdapter();
                            } else {
                                GenerateFilter(page + 1, 0);
                            }
                        }
                    }
                } else {
                    try {
                        Thread.sleep(4500, 0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        GenerateFilter(page, 0);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingData> call, @NotNull Throwable t) {
                Ready = true;
                Log.e("all_boking", "on Failure called!" + t);
            }
        });
        try {
            Thread.sleep(100, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void SetVisibility(android.widget.TextView comp, boolean condition){
        comp.setVisibility(condition?View.VISIBLE:View.INVISIBLE);
        comp.setEnabled(condition);
    }
}
