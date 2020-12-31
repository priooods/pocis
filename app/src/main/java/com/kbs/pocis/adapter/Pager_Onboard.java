package com.kbs.pocis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.kbs.pocis.R;
import com.kbs.pocis.item.Item_onboard;

import java.util.List;

public class Pager_Onboard extends PagerAdapter {

    Context mcontext;
    List<Item_onboard> item_onboards;

    public Pager_Onboard(Context mcontext, List<Item_onboard> item_onboards) {
        this.mcontext = mcontext;
        this.item_onboards = item_onboards;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.model_onboard, (ViewGroup)null);

        ImageView imageView = view.findViewById(R.id.model_img_onboard);
        TextView top = view.findViewById(R.id.onboard_top);
        TextView mid = view.findViewById(R.id.onboard_mid);
        TextView bot = view.findViewById(R.id.onboard_bottom);

        imageView.setImageResource(item_onboards.get(position).getImg());
        top.setText(item_onboards.get(position).getTop());
        mid.setText(item_onboards.get(position).getMid());
        bot.setText(item_onboards.get(position).getBot());

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return item_onboards.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
