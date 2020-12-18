package com.kbs.pocis.item;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.kbs.pocis.R;
import com.kbs.pocis.activity.CreateBooking;
import com.kbs.pocis.activity.HomePage;

public class Dialog_Cancel extends DialogFragment {

    TextInputLayout input_alasan;
    TextView title;
    ImageView bg;
    Button btn_close,btn_cancelBoking;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_cancelled,container,false);

        input_alasan=view.findViewById(R.id.onp);
        title = view.findViewById(R.id.tl);
        bg = view.findViewById(R.id.bc);
        btn_close = view.findViewById(R.id.btn_cancelclose);
        btn_cancelBoking = view.findViewById(R.id.btn_cancelbookinggo);
        input_alasan.setVisibility(View.GONE);
        bg.setImageResource(R.drawable.show_cancel);
        title.setText("Are you sure want to quit create booking?");
        btn_close.setText("Close");
        btn_close.setAllCaps(false);
        btn_cancelBoking.setText("Yes");
        btn_cancelBoking.setAllCaps(false);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_cancelBoking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UserData user = (UserData) getIntent().getParcelableExtra("user");
                Intent intent = new Intent(getContext(), HomePage.class);
                startActivity(intent);
                getActivity().finish();
            }
        });



        return view;
    }
}
