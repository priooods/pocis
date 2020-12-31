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

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_News;

public class News_Detail extends Fragment {

    View view;
    ImageView back;
    TextView title_news,formPage, news_date, news_desc;
    ImageView image_news;

    int typed;
    public News_Detail(int type){
        typed = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_detail, container, false);
        back = view.findViewById(R.id.btn_back_news);
        formPage = view.findViewById(R.id.formPage);
        news_date = view.findViewById(R.id.news_date);
        image_news = view.findViewById(R.id.image_news);
        news_desc = view.findViewById(R.id.news_desc);
        title_news = view.findViewById(R.id.title_news);
        back.setOnClickListener(v -> requireActivity().onBackPressed());

        switch (typed){
            case 0:
            case 2:
                formPage.setText(R.string.custumers_News);
                break;
            case 1:
            case 3:
                formPage.setText(R.string.rewards_News);
                break;
        }

        Model_News data = Model_News.mn;
        title_news.setText(data.title);
        news_date.setText(data.dates);
        news_desc.setText(data.desc);


        return view;
    }
}
