package com.kbs.pocis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_News;
import com.kbs.pocis.news.News_Detail;

import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.vHolder> {

    Context context;
    List<Model_News> model_news;
    int mode, type;

    public AdapterNews(Context context, List<Model_News> model_news, int mode,int type) {
        this.context = context;
        this.model_news = model_news;
        this.mode = mode;
        this.type = type;
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (mode == 0 ){
            view = LayoutInflater.from(context).inflate(R.layout.model_news_front,parent,false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.model_news_back,parent,false);
        }
        return new vHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        if (mode == 0) {
            holder.imagefront.setBackgroundResource(R.color.colorGrey);
            holder.titlefront.setText(model_news.get(position).title);
        } else {
            holder.imageback.setBackgroundResource(R.color.colorGrey);
            holder.date.setText(model_news.get(position).dates);
            holder.titleback.setText(model_news.get(position).title);
        }

        switch (type){
            case 0:
                holder.frontgpdetail.setOnClickListener(v -> {
                    Model_News.mn = model_news.get(position);
                    Fragment fragment;
                    fragment = new News_Detail(0);
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                });
                break;
            case 1:
                holder.frontgpdetail.setOnClickListener(v -> {
                    Model_News.mn = model_news.get(position);
                    Fragment fragment;
                    fragment = new News_Detail(1);
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                });
                break;
            case 2:
                holder.backgodetail.setOnClickListener(v -> {
                    Model_News.mn = model_news.get(position);
                    Fragment fragment;
                    fragment = new News_Detail(2);
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                });
                break;
            case 3:
                holder.backgodetail.setOnClickListener(v -> {
                    Model_News.mn = model_news.get(position);
                    Fragment fragment;
                    fragment = new News_Detail(3);
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framehomepage, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return model_news.size();
    }

    public static class vHolder extends RecyclerView.ViewHolder{
        TextView date, titlefront,titleback;
        ImageView imagefront,imageback;
        LinearLayout backgodetail;
        RelativeLayout frontgpdetail;
        public vHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date_news);
            titlefront = itemView.findViewById(R.id.title_news);
            imagefront = itemView.findViewById(R.id.image_news);
            frontgpdetail = itemView.findViewById(R.id.godetail_front);
            titleback = itemView.findViewById(R.id.title_news_back);
            imageback = itemView.findViewById(R.id.image_news_back);
            backgodetail = itemView.findViewById(R.id.backgodetail);
        }
    }
}
