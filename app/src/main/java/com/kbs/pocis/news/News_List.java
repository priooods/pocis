package com.kbs.pocis.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.adapter.AdapterNews;
import com.kbs.pocis.model.Model_News;
import com.kbs.pocis.profile.Profile_Menu;

import java.util.ArrayList;
import java.util.List;

public class News_List extends Fragment {


    RecyclerView list_news;
    ImageView showprofile;
    TextView title;

    View view;
    int typed;
    public News_List(int type){
        typed = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container,false);
        list_news = view.findViewById(R.id.list_allnews);
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


        switch (typed){
            case 0:
                title.setText("custumers news");
                listcustomer();
                break;
            case 1:
                title.setText("rewards news");
                listRewards();
                break;
        }

        return view;
    }

    void listcustomer(){
        List<Model_News> model_news = new ArrayList<>();
        model_news.add(new Model_News(null,"Lorem Ipsum dua tiga","01/12/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",1));
        model_news.add(new Model_News(null,"Lorem Ipsum tiga empat lima enam tujuh delapan","02/04/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",2));
        model_news.add(new Model_News(null,"Lorem Ipsum ini berita misalnya","06/02/2019","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",3));
        model_news.add(new Model_News(null,"Lorem Ipsum tiga empat lima enam tujuh delapan","02/04/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",2));
        model_news.add(new Model_News(null,"Lorem Ipsum ini berita misalnya","06/02/2019","Gak tau nih mau nulis apa",3));
        model_news.add(new Model_News(null,"Lorem Ipsum dua tiga","01/12/2020","Ini description dari news",1));
        model_news.add(new Model_News(null,"Lorem Ipsum tiga empat lima enam tujuh delapan","02/04/2020","",2));
        model_news.add(new Model_News(null,"Lorem Ipsum ini berita misalnya","06/02/2019","",3));
        AdapterNews adapterNews = new AdapterNews(getContext(), model_news,1,2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        list_news.setLayoutManager(layoutManager);
        list_news.setAdapter(adapterNews);
    }

    void listRewards(){
        List<Model_News> model_news = new ArrayList<>();
        model_news.add(new Model_News(null,"Yeey anonim nomer4 dapet hadiah mobil baru lohh","01/12/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",1));
        model_news.add(new Model_News(null,"Yeey anonim nomer5 dapet hadiah pesawat baru lohh","02/04/2020","",2));
        model_news.add(new Model_News(null,"Yeey anonim nomer6 dapet hadiah motor baru lohh","06/02/2019","",3));
        model_news.add(new Model_News(null,"Yeey anonim nomer4 dapet hadiah mobil baru lohh","01/12/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",1));
        model_news.add(new Model_News(null,"Yeey anonim nomer4 dapet hadiah mobil baru lohh","01/12/2020","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",1));
        model_news.add(new Model_News(null,"Yeey anonim nomer5 dapet hadiah pesawat baru lohh","02/04/2020","",2));
        model_news.add(new Model_News(null,"Yeey anonim nomer6 dapet hadiah motor baru lohh","06/02/2019","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",3));
        AdapterNews adapterNews = new AdapterNews(getContext(), model_news,1,3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        list_news.setLayoutManager(layoutManager);
        list_news.setAdapter(adapterNews);
    }
}
