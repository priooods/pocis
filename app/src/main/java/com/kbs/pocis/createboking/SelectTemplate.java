package com.kbs.pocis.createboking;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kbs.pocis.R;
import com.kbs.pocis.model.createboking.Model_SelectTemplate;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.createbooking.CallingSelectTemp;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SelectTemplate extends Fragment {

    CardView boxesCheckAll;
    CheckBox checkAll;
    Button prev, next;
    RecyclerView recyclerView_FeePas;
    ListingFeePas listingFeePas;
    ArrayList<Model_ShowTemplate> model;
    ArrayList<SelectTemplate.AdapterTemplateAnak.VHolder> button = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_template, container, false);
        recyclerView_FeePas = view.findViewById(R.id.list_template);
        boxesCheckAll = view.findViewById(R.id.e);
        checkAll = view.findViewById(R.id.select_template_checkAll);

        //Button go & backn
        prev = view.findViewById(R.id.select_template_prevBtn);
        next = view.findViewById(R.id.select_template_nextBtn);

        FunctionButton();
        ListingData();
        checkAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            for(AdapterTemplateAnak.VHolder b : button){
                b.checkBox.setChecked(isChecked);
            }
        });
        return view;
    }

    public void ListingData () {
        if (BookingData.isExist() && BookingData.i.template!=null) {
            ArrayList<String> query = new ArrayList<>();
            for(BookingData.BookTemplate temp:BookingData.i.template){
                query.add(String.valueOf(temp.id));
            }
            Log.i("select_template",query.toString());
            Call<CallingSelectTemp> call = UserData.i.getService().getSelectTemplate(UserData.i.getToken(), query);
            call.enqueue(new Callback<CallingSelectTemp>() {
                @Override
                public void onResponse(@NotNull Call<CallingSelectTemp> call, @NotNull Response<CallingSelectTemp> response) {
                    if (Calling.TreatResponse(getContext(), "tag", response.body())) {
                        assert response.body() != null;
                        CallingSelectTemp.SelTemp data = response.body().data;
                        if (data == null || data.list == null) {
                            MDToast.makeText(requireContext(), "No Data", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
                        } else {
                            if (data.temp_document != null) {
                                Log.i("model_select", "length request = " + data.temp_document.size());
                                BookingData.i.file = data.temp_document;
                                Log.i(TAG, "document_id: "+ BookingData.i.file.get(0).m_document_id);
                            }else {
                                if (BookingData.i.file != null)
                                    BookingData.i.file.clear();
                            }
                            List<BookingData.BookTemplate.BookTempList> listcheck = null;
                            BookingData.BookTemplate temp = null;
                            Model_ShowTemplate stemp = null;
                            model = new ArrayList<>(BookingData.i.template.size());
                            for (BookingData.BookTemplate st : BookingData.i.template) {
                                model.add(st.getModel());
                            }
                            int f;
                            for (Model_SelectTemplate mod : data.list) {
                                int header_id = Integer.parseInt(mod.header_id);
                                if (temp == null || temp.id != header_id) {
                                    for (int i = 0; i < model.size(); i++) {
                                        temp = BookingData.i.template.get(i);
                                        if (temp.id == header_id) {
                                            stemp = model.get(i);
                                            listcheck = temp.listCheck;
                                            if (listcheck != null) {
                                                for(BookingData.BookTemplate.BookTempList l:listcheck){
                                                    Log.i("checked!",l.code+" "+l.name+" "+l.id+ " " + l.m_service_code_id);
                                                }
                                            }else{
                                                Log.i("checked!",temp.id+"is null!");
                                            }
                                            break;
                                        } else
                                            stemp = null;
                                    }
                                }
                                if (stemp != null) {
                                    if (listcheck!=null) {
                                        Log.w("checked!","start check = "+listcheck.size());
                                        for (f = 0; f < listcheck.size(); f += 1) {
                                            Log.w("checked!","start check "+mod.id+"="+listcheck.get(f).id);
                                            if (mod.id == listcheck.get(f).id) {
                                                mod.checked = true;
                                                break;
                                            }
                                        }
                                    }
                                    stemp.list.add(mod);
                                    Log.i("model_select", mod.header_id + " " + mod.m_service_code_id + " " + mod.code + " " + mod.desc+" "+mod.checked);
                                }
                            }
                            for (Model_ShowTemplate st : model) {
                                Log.i("model_select",st.id+" = "+st.list.size());
                            }
                            listingFeePas = new ListingFeePas(requireContext(), model);
                            recyclerView_FeePas.setHasFixedSize(true);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
                            recyclerView_FeePas.setLayoutManager(layoutManager);
                            recyclerView_FeePas.setAdapter(listingFeePas);
                            listingFeePas.notifyDataSetChanged();
                        }
                    }
                }


                @Override
                public void onFailure(@NotNull Call<CallingSelectTemp> call, @NotNull Throwable t) {
                    Log.i(TAG, "onFailure: => " + t);
                }
            });
        }
    }

    public void FunctionButton(){
        prev.setOnClickListener(v -> {
            BookingData.i.SelectBookUpdate(model);
            requireActivity().onBackPressed();
            Log.i(TAG, "m_customer_id: => " + BookingData.i.customerId);
        });
        next.setOnClickListener(v -> GoToNext());
    }

    public void GoToNext(){
        if (isOneChecked()) {
            for (Model_ShowTemplate mod : model) {
                for (Model_SelectTemplate sel : mod.list) {
                    Log.i("out", sel.header_id + " | " + sel.m_service_code_id + " " + sel.desc + " - " + sel.checked);
                }
            }

            if (BookingData.i.file != null && BookingData.i.file.size() > 0) {
                BookingData.i.SelectBookUpdate(model);
                Fragment fragment = new UploadDocument();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }else{
                if (BookingData.i.checkVesselInfoSkip()){
                    BookingData.i.SelectBookUpdate(model);
                    BookingData.i.vessel = new BookingData.VesselData();
                    BookingData.i.commodity = null;
                    Fragment fragment = new Summary();
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    // Goto Commodity
                    BookingData.i.SelectBookUpdate(model);
                    //reset file if user change template and nothing file upload
                    BookingData.i.file = null;
                    Fragment fragment = new AddComodity();
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        } else {
            MDToast.makeText(requireContext(), "Please Select Template !", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
        }
    }

    //Show status dari subType Template apakah tidak ada yg true di Type Template
    boolean isOneChecked(){
        boolean checked;
        for (Model_ShowTemplate mod : model) {
            checked = false;
            for (Model_SelectTemplate sel : mod.list) {
                if (sel.checked){
                    checked = true;
                    break;
                }
            }
            if (!checked){
                return false;
            }
        }
        return true;
    }

    //Ini untuk Listing ID dan NAME dari setiap type Template
    public class ListingFeePas extends RecyclerView.Adapter<ListingFeePas.vHolder>{
        Context context;
        List<Model_ShowTemplate> model;

        public ListingFeePas(Context context, List<Model_ShowTemplate> model_selectTemplates) {
            this.context = context;
            this.model = model_selectTemplates;
        }

        @NonNull
        @Override
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_checkbox, parent,false);
            return new vHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull final vHolder holder, final int position) {
            holder.idtitle.setText(model.get(position).code);
            holder.nametitle.setText(model.get(position).display_desc_header);
            Glide.with(context)
                    .load("http://cigading.ptkbs.co.id/pocis/img/template/"+ model.get(position).image_file)
                    .placeholder(R.color.colorGrey)
                    .error(R.drawable.icon_silang)
                    .override(200, 200)
                    .centerCrop()
                    .into(holder.img);

            //Untuk mempermudah pengembangan selanjutnya dari setiap pilihan sub Template.
            // maka disini dibuat kan list didalam list
            AdapterTemplateAnak templateAnak = new AdapterTemplateAnak(context, model.get(position).list);
            holder.listRecycle.setHasFixedSize(true);
            holder.listRecycle.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            holder.listRecycle.setAdapter(templateAnak);
            templateAnak.notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return model.size();
        }

        public class vHolder extends RecyclerView.ViewHolder {
            ImageView img;
            RecyclerView listRecycle;
            TextView nametitle, idtitle;

            public vHolder(@NonNull View itemView) {
                super(itemView);

                img = itemView.findViewById(R.id.imgshowtemplate);
                nametitle = itemView.findViewById(R.id.name_showtemplate);
                idtitle = itemView.findViewById(R.id.idshowtemplate);
                listRecycle = itemView.findViewById(R.id.list_anakselect);
            }
        }
    }

    //Ini untuk Listing ID dan NAME dari setiap subtype Template
    public  class AdapterTemplateAnak extends RecyclerView.Adapter<AdapterTemplateAnak.VHolder>{
        Context context;
        List<Model_SelectTemplate> selectTemplates;

        public AdapterTemplateAnak(Context context, List<Model_SelectTemplate> selectTemplates) {
            this.context = context;
            this.selectTemplates = selectTemplates;
        }

        @NonNull
        @Override
        public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_select_template, parent,false);
            return new VHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VHolder holder, int position) {
            holder.name.setText(selectTemplates.get(position).desc);
            holder.id.setText(selectTemplates.get(position).code);
            holder.checkBox.setChecked(selectTemplates.get(position).checked);
            button.add(holder);
            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                holder.checkBox.setChecked(isChecked);
                selectTemplates.get(position).checked = isChecked;
            });

        }


        @Override
        public int getItemCount() {
            return selectTemplates.size();
        }

        public class VHolder extends RecyclerView.ViewHolder{
            TextView id, name;
            CheckBox checkBox;
            public VHolder(@NonNull View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.idselecttemplate);
                name = itemView.findViewById(R.id.nameselecttemplate);
                checkBox = itemView.findViewById(R.id.modelcheck_selecttemplate);
            }
        }
    }

}