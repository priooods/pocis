package com.kbs.pocis.news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.AdapterNews;
import com.kbs.pocis.filter.FilterFragment;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.profile.Profile_Menu;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.PublicList.PublicList;
import com.kbs.pocis.service.UserData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class News_List extends FilterFragment {

    RecyclerView recyclerView;
    List<Model_Project> model_project_s;
    RelativeLayout layout_kosong;
    NestedScrollView nested;
    TextView kanan, kiri, kanan_banget, kiri_banget,
            index_list_invoice, all_index_invoice;
    int total_item = 0;

    ImageView showprofile;
    TextView title;

    int typed;
    public News_List(int type){
        typed = type;
    }
//fragment_news
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container,false);
        recyclerView = view.findViewById(R.id.list_invoice);
        showprofile = view.findViewById(R.id.iconprofile);
        showprofile.setOnClickListener(v -> {
            Fragment fragment;
            fragment = new Profile_Menu();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
            fragmentTransaction.commit();
        });
        title = view.findViewById(R.id.title);

        model_project_s = new ArrayList<>();
        nested = view.findViewById(R.id.nested);
        layout_kosong = view.findViewById(R.id.lay_invoice_kosong);
        kanan = view.findViewById(R.id.kanan);
        kiri = view.findViewById(R.id.kiri);
        kanan_banget = view.findViewById(R.id.kanan_banget);
        kiri_banget = view.findViewById(R.id.kiri_banget);
        index_list_invoice = view.findViewById(R.id.index_list_invoice);
        all_index_invoice = view.findViewById(R.id.all_index_invoice);
        recyclerView = view.findViewById(R.id.list_invoice);

        filter = null;
        max_list = 5;
        GenerateLists();
        Ganti();


        switch (typed){
            case 0:
                title.setText(R.string.custumers_News);
//                listcustomer();
                break;
            case 1:
                title.setText(R.string.rewards_News);
//                listRewards();
                break;
        }

        return view;
    }
//
//    void listcustomer(){
//        List<Model_News> model_news = new ArrayList<>();
//        model_news.add(new Model_News(null,"Lorem Ipsum dua tiga","01/12/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",1));
//        model_news.add(new Model_News(null,"Lorem Ipsum tiga empat lima enam tujuh delapan","02/04/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",2));
//        model_news.add(new Model_News(null,"Lorem Ipsum ini berita misalnya","06/02/2019","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",3));
//        model_news.add(new Model_News(null,"Lorem Ipsum tiga empat lima enam tujuh delapan","02/04/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",2));
//        model_news.add(new Model_News(null,"Lorem Ipsum ini berita misalnya","06/02/2019","Gak tau nih mau nulis apa",3));
//        model_news.add(new Model_News(null,"Lorem Ipsum dua tiga","01/12/2020","Ini description dari news",1));
//        model_news.add(new Model_News(null,"Lorem Ipsum tiga empat lima enam tujuh delapan","02/04/2020","",2));
//        model_news.add(new Model_News(null,"Lorem Ipsum ini berita misalnya","06/02/2019","",3));
//        AdapterNews adapterNews = new AdapterNews(getContext(), model_news,1,2);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//        list_news.setLayoutManager(layoutManager);
//        list_news.setAdapter(adapterNews);
//    }
//
//    void listRewards(){
//        List<Model_News> model_news = new ArrayList<>();
//        model_news.add(new Model_News(null,"Yeey anonim nomer4 dapet hadiah mobil baru lohh","01/12/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",1));
//        model_news.add(new Model_News(null,"Yeey anonim nomer5 dapet hadiah pesawat baru lohh","02/04/2020","",2));
//        model_news.add(new Model_News(null,"Yeey anonim nomer6 dapet hadiah motor baru lohh","06/02/2019","",3));
//        model_news.add(new Model_News(null,"Yeey anonim nomer4 dapet hadiah mobil baru lohh","01/12/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",1));
//        model_news.add(new Model_News(null,"Yeey anonim nomer4 dapet hadiah mobil baru lohh","01/12/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",1));
//        model_news.add(new Model_News(null,"Yeey anonim nomer5 dapet hadiah pesawat baru lohh","02/04/2020","",2));
//        model_news.add(new Model_News(null,"Yeey anonim nomer6 dapet hadiah motor baru lohh","06/02/2019","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",3));
//        AdapterNews adapterNews = new AdapterNews(getContext(), model_news,1,3);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
//        list_news.setLayoutManager(layoutManager);
//        list_news.setAdapter(adapterNews);
//    }
    public void Ganti() {
    kanan.setOnClickListener(view -> ChangePage(page_current + 1));
    kanan_banget.setOnClickListener(view -> ChangePage(page_last));
    kiri_banget.setOnClickListener(view -> ChangePage(1));
    kiri.setOnClickListener(view -> ChangePage(page_current - 1));
}

    void ChangePage(int target_page) {
        if (Ready) {
            page_current = target_page;
            GenerateLists();
            Ready = false;
        } else {
            Log.w("invoice", "Aggresive Touch/Command!");
        }
    }

    void scrollNested(){
        nested.fullScroll(View.FOCUS_UP);
        nested.smoothScrollTo(0,0);
    }

    @Override
    protected void ShowAdapter() {
        if (model_project_s != null && model_project_s.size() > 0) {
            scrollNested();
            SetVisibility(kiri, page_current > 1);
            SetVisibility(kiri_banget, page_current > 2);
            SetVisibility(kanan, page_current < page_last);
            SetVisibility(kanan_banget, page_current + 1 < page_last);

            String of = page_current + "  Of  " + page_last;
            String show = "Showing " + (model_project_s.size()) + " of " + total_item + " results";
            index_list_invoice.setText(of);
            all_index_invoice.setText(show);

            if (typed == 0){
                AdapterNews adapterNews = new AdapterNews(getContext(), model_project_s,1,2);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapterNews);
            } else {
                AdapterNews adaterReward = new AdapterNews(getContext(), model_project_s,1,3);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adaterReward);
            }
        } else {
            nested.setVisibility(View.GONE);
            layout_kosong.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void Model_CheckorClear() {
        if (model_project_s == null)
            model_project_s = new ArrayList<>();
        else
            model_project_s.clear();
    }

    @Override
    protected void GenerateFilter(int page, int list){
        Log.i("frag_invoice", "Call Invoice page = " + page);
        Call<PublicList> call;
        if (UserData.isExists()) {
            if (typed == 0) {
                call = UserData.i.getService().customerNews(UserData.i.getToken());
            } else {
                call = UserData.i.getService().customerRewards(UserData.i.getToken());
            }
            call.enqueue(new Callback<PublicList>() {
                @Override
                public void onResponse(@NotNull Call<PublicList> call, @NotNull Response<PublicList> response) {
                    PublicList respone = response.body();
                    if (Calling.TreatResponse(getContext(), "invoice", respone)) {
                        if (!filtering) {
                            assert respone != null;
                            model_project_s = respone.data.model;
                            page_current = respone.data.current_page;
                            page_last = respone.data.last_page;
                            total_item = respone.data.total;
                            FinishFilter();
                        } else {
//                            if (pmanager.loaded) {
////                            Log.i("booking_load", "pmanager.load"+pmanager.loaded+" page = "+page);
//                                assert respone != null;
//                                List<Model_Project> data = respone.data.model;
//                                for (int i = list; i < data.size(); i++) {
//                                    if (filter.checkFilter(data.get(i))) {
//                                        if (model_project_s.size() < pmanager.page_capacity) {
//                                            model_project_s.add(data.get(i));
//                                            Log.i("frag_invoice", data.get(i).status_payment);
//                                        } else {
//                                            page_last = pmanager.page_last;
//                                            total_item = pmanager.total;
//                                            load = false;
//                                            FinishFilter();
//                                            return;
//                                        }
//                                    }
//                                }
//                                if (page < respone.data.last_page && pmanager.getLastPage()) {
//                                    GenerateFilter(pmanager.getNextPage(), 0);
//                                } else {
//                                    page_last = pmanager.page_last;
//                                    total_item = pmanager.total;
//                                    load = false;
//                                    FinishFilter();
//                                }
//                            } else {
////                            Log.i("booking_load", "pmanager.load"+pmanager.loaded+" page = "+page+" load = "+load);
//                                int i = 0;
//                                assert respone != null;
//                                for (Model_Project data : respone.data.model) {
//                                    if (filter.checkFilter(data)) {
//                                        if (pmanager.addPack(page, i) && load) {
//                                            model_project_s.add(data);
//                                            Log.i("booking_load", data.status_payment);
//                                        } else {
//                                            load = false;
//                                        }
//                                    }
//                                    i++;
//                                }
//                                if (page == respone.data.last_page) {
//                                    pmanager.finishLoad();
//                                    page_last = pmanager.page_last;
//                                    total_item = pmanager.total;
//                                    load = false;
//                                    nested.setVisibility(View.VISIBLE);
//                                    FinishFilter();
//                                } else {
//                                    GenerateFilter(page + 1, 0);
//                                }
//                            }
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
                public void onFailure(@NotNull Call<PublicList> call, @NotNull Throwable t) {
                    Ready = true;
                    Log.e("frag_invoice", "on Failure called!" + t);
                }
            });
            try {
                Thread.sleep(100, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void SetVisibility(android.widget.TextView comp, boolean condition){
        comp.setVisibility(condition?View.VISIBLE:View.INVISIBLE);
        comp.setEnabled(condition);
    }
}
