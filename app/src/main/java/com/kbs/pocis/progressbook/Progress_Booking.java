package com.kbs.pocis.progressbook;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.kbs.pocis.R;
import com.xyz.step.FlowViewHorizontal;

public class Progress_Booking extends Fragment {

    FlowViewHorizontal step;
    View view;
    ImageView icon_back;
    String[] progress;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.progress_booking, container, false);
        step = view.findViewById(R.id.step_top);
        icon_back = view.findViewById(R.id.icon_back);
        icon_back.setOnClickListener(v->requireActivity().onBackPressed());
        progress = new String[4];
        progress[0] = "Booking";
        progress[1] = "PPJ";
        progress[2] = "BAPJ";
        progress[3] = "Invoice";
        step.setProgress(2,4,progress,null);



        return view;
    }
}
