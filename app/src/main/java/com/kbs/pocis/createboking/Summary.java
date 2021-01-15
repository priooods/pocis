package com.kbs.pocis.createboking;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Commodity;
import com.kbs.pocis.model.createboking.Model_UploadDocument;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.createbooking.CallingSaveBok;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Summary extends Fragment {

    Button next, prev;
    TextView customer_type, related, contract, veselname, discharge,
            port, arival, departure, loading;

    ProgressBar progressBar;

    RecyclerView list_serviceInfo, list_comodityInfo, list_Document;
    ArrayList<Model_Commodity> summaryComodities ;
    ArrayList<Model_UploadDocument> uploadDocuments;
    List<BookingData.BookTemplate> modelShowTemplates;
    AdapterComodity adapterComodity;
    AdapterServices adapterServices;
    AdapterFile adapterFile;

    LinearLayout ln_sch_info, ln_doc, ln_com;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        progressBar = view.findViewById(R.id.progress);
        ln_sch_info = view.findViewById(R.id.b);
        ln_doc = view.findViewById(R.id.e);
        ln_com = view.findViewById(R.id.d);
        next = view.findViewById(R.id.summary_nextBtn);
        prev = view.findViewById(R.id.summary_prevBtn);

        customer_type = view.findViewById(R.id.veselinfo_customer_type);
        related = view.findViewById(R.id.veselinfo_related_vesel);
        contract = view.findViewById(R.id.veselinfo_contract);

        veselname = view.findViewById(R.id.veselinfo_veselname);
        discharge = view.findViewById(R.id.veselinfo_loading_discharge);
        port = view.findViewById(R.id.veselinfo_port);
        arival = view.findViewById(R.id.veselinfo_arrival);
        departure = view.findViewById(R.id.veselinfo_departure);
        loading = view.findViewById(R.id.veselinfo_loading);

        list_serviceInfo = view.findViewById(R.id.veselinfo_list);
        list_comodityInfo = view.findViewById(R.id.recycle_vesel_comodityInfo);
        list_Document = view.findViewById(R.id.veselinfo_documentList);

        BookingData data = BookingData.i;
        if (BookingData.i.checkVesselInfoSkip()){
            ln_sch_info.setVisibility(View.GONE);
        } else {
            Log.i(TAG, "vessel ada: ");
        }

        if (BookingData.i.commodity == null || BookingData.i.commodity.size() == 0) {
            Log.i(TAG, "commodity null: ");
            ln_com.setVisibility(View.GONE);
        } else {
            Log.i(TAG, "commodity ada: ");
        }

        if (BookingData.i.file == null){
            ln_doc.setVisibility(View.GONE);
            Log.i(TAG, "doc null: ");
        } else {
            Log.i(TAG, "doc ada: ");
        }

        if (!data.customerType.equals("AGENT") && !data.relatedVesel.equals("Y")) {
            discharge.setText(data.vessel.voyage_number);
        } else if (data.customerType.equals("GENERAL")){
            discharge.setText(data.vessel.voyage_number);
        } else {
            discharge.setText(null);
            data.vessel.id_voyage = 0;
        }

        customer_type.setText(data.customerType);
        related.setText(data.relatedVesel);
        contract.setText(data.contract);
        veselname.setText(data.vessel.vessel_name);
        port.setText(data.vessel.port_discharge);
        arival.setText(data.vessel.estimate_arival);
        departure.setText(data.vessel.estimate_departure);
        loading.setText(data.vessel.port_origin);

        ListingComodityList();
        ListingList();

        ButtonFunction();
        return view;
    }

    private void ListingList(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        modelShowTemplates = BookingData.i.template;
        adapterServices = new AdapterServices(getContext(), modelShowTemplates);
        list_serviceInfo.setLayoutManager(layoutManager);
        list_serviceInfo.setAdapter(adapterServices);

        if (BookingData.i.file != null) {
            uploadDocuments = BookingData.i.file;
            adapterFile = new AdapterFile(getContext(), uploadDocuments);
            list_Document.setLayoutManager(manager);
            list_Document.setAdapter(adapterFile);
        }
    }

    private void ListingComodityList() {
        summaryComodities = BookingData.i.commodity;
        adapterComodity = new AdapterComodity(getContext(), summaryComodities);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        list_comodityInfo.setLayoutManager(layoutManager);
        list_comodityInfo.setAdapter(adapterComodity);
    }

    public void ButtonFunction(){
        prev.setOnClickListener(v -> requireActivity().onBackPressed());
        next.setOnClickListener(v -> ShowDialogCancell(getContext()));
    }

    public void ShowDialogCancell (final Context context){
        View view  = LayoutInflater.from(context).inflate(R.layout.dialog_cancelled, null);
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(view);
        Objects.requireNonNull(dialogFragment.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputLayout input_alasan = view.findViewById(R.id.onp);
        input_alasan.setVisibility(View.GONE);
        TextView title = view.findViewById(R.id.tl);
        ImageView bg = view.findViewById(R.id.bc);
        bg.setImageResource(R.drawable.crb);
        title.setText(R.string.titledialog);
        title.setGravity(Gravity.CENTER);

        Button btn_close = view.findViewById(R.id.btn_cancelclose);
        btn_close.setText(R.string.no);
        btn_close.setAllCaps(false);
        Button btn_go = view.findViewById(R.id.btn_cancelbookinggo);
        btn_go.setText(R.string.yes);
        btn_go.setBackground(getResources().getDrawable(R.drawable.btn_green));
        btn_go.setAllCaps(false);

        btn_close.setOnClickListener(v -> dialogFragment.cancel());
        btn_go.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            SendDataBooking();
            dialogFragment.dismiss();
        });
        dialogFragment.show();
    }

    public RequestBody data_form(File req) {
        return RequestBody.create(req, MediaType.parse("multipart/form-data"));
    }

    public RequestBody input_form(String req) {
        return RequestBody.create(req, MediaType.parse("multipart/form-data"));
    }

    public void SendDataBooking(){
        BookingData data = BookingData.i;

        HashMap<String, RequestBody> Booking = new HashMap<>();
        Booking.put("Booking[m_customer_id]", input_form(UserData.i.getCustID()));
        Booking.put("Booking[t_map_customer_type_id]", input_form(String.valueOf(data.customerId)));
        Booking.put("Booking[customer_type_code]", input_form(data.customerType));
        Booking.put("Booking[flag_related_vessel]", input_form(data.relatedVesel));
        Booking.put("Booking[flag_contract]", input_form(data.contract));


        HashMap<String, RequestBody> BookingVessel = new HashMap<>();
        BookingVessel.put("BookingVessel[m_vessel_id]", input_form(String.valueOf(data.vessel.id_vessel)));
        BookingVessel.put("BookingVessel[voyage_no]", input_form(String.valueOf(data.vessel.voyage_number)));
        BookingVessel.put("BookingVessel[discharge_or_loading]", input_form(String.valueOf(data.vessel.discharge_loading)));
        BookingVessel.put("BookingVessel[estimate_arrival_date]", input_form(String.valueOf(data.vessel.est_value)));
        BookingVessel.put("BookingVessel[estimate_departure_date]", input_form(String.valueOf(data.vessel.depar_value)));
        BookingVessel.put("BookingVessel[port_of_loading_id]", input_form(String.valueOf(data.vessel.port_discharge_id)));
        BookingVessel.put("BookingVessel[m_port_cigading_id]", input_form(String.valueOf(data.vessel.port_origin_id)));

        Log.i(TAG, "check data: " + "estimate " + data.vessel.est_value + ", departure: " + data.vessel.depar_value);

        Booking.put("VesselSchedule[id]", input_form(String.valueOf(data.vessel.id_voyage)));

        int i = 0;
        if (data.commodity != null) {
            for (Model_Commodity com : data.commodity) {
                com.getMap(Booking, i);
                i++;
                Log.i(TAG, "commodity list: " + com);
            }
        }

        i = 0;
        for (BookingData.BookTemplate t : BookingData.i.template){
            for(BookingData.BookTemplate.BookTempList a : t.listCheck){
                a.getMap(Booking,i);
                i++;
            }
        }

        i = 0;
        if (BookingData.i.file != null) {
            for (Model_UploadDocument doc : BookingData.i.file){
                doc.getMap(Booking,i);
                i++;
                Log.i(TAG, "new data document: " + doc.path);
            }
        }

        i = 0;
        MultipartBody.Part[] fileToUpload = null;
        if (BookingData.i.file != null) {
            fileToUpload = new MultipartBody.Part[BookingData.i.file.size()];
            for (Model_UploadDocument document : BookingData.i.file) {
                fileToUpload[i] = MultipartBody.Part.createFormData("BookingDocument[file_name][" + i + "]", document.uri.getPath(), data_form(document.uri));
                Log.i(TAG, "SendDataBooking: => " + document.uri);
                i++;
            }
        }

        Log.i(TAG, "SendDataBooking: Files => " + Arrays.toString(fileToUpload));
        retrofit2.Call<CallingSaveBok> call = UserData.i.getService().saveBooking(
                UserData.i.getToken(),BookingVessel,
                Booking, fileToUpload
        );
        call.enqueue(new Callback<CallingSaveBok>() {
            @Override
            public void onResponse(@NotNull retrofit2.Call<CallingSaveBok> call, @NotNull Response<CallingSaveBok> response) {
                CallingSaveBok data = response.body();
                if (Calling.TreatResponse(getContext(),"create_booking",data)) {
                    assert data != null;
                    BookingDetailData detailData = data.data;
                    Log.i(TAG, "onResponse: => " + detailData.no_booking);

                    MDToast.makeText(requireContext(),data.desc + "\nnomer_boking : " + detailData.no_booking + "\nid : " + detailData.id, Toasty.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                    Fragment fragment = new Finish();
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.replace(R.id.frameCreate, fragment);
                    fragmentTransaction.commit();
                }else{
                    MDToast.makeText(requireContext(), "Booking Failure", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingSaveBok> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: " + t);
            }
        });
    }

    //Template Service Information TYPE
    public static class AdapterServices extends RecyclerView.Adapter<AdapterServices.vHolder>{

        Context context;
        List<BookingData.BookTemplate> model_showTemplates;

        public AdapterServices(Context context, List<BookingData.BookTemplate> model_showTemplates) {
            this.context = context;
            this.model_showTemplates = model_showTemplates;
        }

        @NonNull
        @Override
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_summary_serviceinfo_title, parent, false);
            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull vHolder holder, int position) {
            holder.title.setText(model_showTemplates.get(position).name);

            List<BookingData.BookTemplate.BookTempList> templatesAnak = model_showTemplates.get(position).listCheck;
            AdapterSubServices templateAnak = new AdapterSubServices(context, templatesAnak);
            holder.recyclerView_subType.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            holder.recyclerView_subType.setAdapter(templateAnak);
        }

        @Override
        public int getItemCount() {
            return model_showTemplates.size();
        }

        public static class vHolder extends RecyclerView.ViewHolder{

            TextView title, id ;
            RecyclerView recyclerView_subType;

            public vHolder(@NonNull View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.model_serviceinfo_title);
                id = itemView.findViewById(R.id.model_serviceinfo_code);
                recyclerView_subType = itemView.findViewById(R.id.recycle_subtype);
            }
        }

    }
    //Template Service Information subTYPE
    public static class AdapterSubServices extends RecyclerView.Adapter<AdapterSubServices.vHolder>{

        Context context;
        List<BookingData.BookTemplate.BookTempList> model_selectTemplates;

        public AdapterSubServices(Context context, List<BookingData.BookTemplate.BookTempList> model_selectTemplates) {
            this.context = context;
            this.model_selectTemplates = model_selectTemplates;
        }

        @NonNull
        @Override
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_summary_serviceinfo_subtitle, parent, false);
            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull vHolder holder, int position) {
            holder.title.setText(model_selectTemplates.get(position).name);
            holder.id.setText(model_selectTemplates.get(position).code);
        }

        @Override
        public int getItemCount() {
            return model_selectTemplates.size();
        }


        public static class vHolder extends RecyclerView.ViewHolder{

            TextView title, id ;

            public vHolder(@NonNull View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.name_subtipe);
                id = itemView.findViewById(R.id.id_subtipe);
            }
        }

    }

    public static class AdapterComodity extends RecyclerView.Adapter<AdapterComodity.vHolder>{

        Context context;
        List<Model_Commodity> comodityList;

        public AdapterComodity(Context context, List<Model_Commodity> comodityList) {
            this.context = context;
            this.comodityList = comodityList;
        }

        @NonNull
        @Override
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_vesel_comoditylist, parent, false);

            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull vHolder holder, int position) {
            holder.comodity.setText(comodityList.get(position).commodity.desc);
            holder.consigne.setText(comodityList.get(position).consigne.name);
            holder.packages.setText(comodityList.get(position).packages + " Package");
            holder.weight.setText(comodityList.get(position).weight + " Tonage");
        }

        @Override
        public int getItemCount() {
            return comodityList.size();
        }

        public static class vHolder extends RecyclerView.ViewHolder{

            TextView consigne, comodity, packages, weight;

            public vHolder(@NonNull View itemView) {
                super(itemView);

                comodity = itemView.findViewById(R.id.vesel_infovesel_comodity);
                consigne = itemView.findViewById(R.id.vesel_infovesel_consigne);
                packages = itemView.findViewById(R.id.vesel_infovesel_package);
                weight = itemView.findViewById(R.id.vesel_infovesel_weight);


            }
        }
    }

    public static class AdapterFile extends RecyclerView.Adapter<AdapterFile.vHolder>{

        Context context;
        List<Model_UploadDocument> uploadDocuments;

        public AdapterFile(Context context, List<Model_UploadDocument> uploadDocuments) {
            this.context = context;
            this.uploadDocuments = uploadDocuments;
        }

        @NonNull
        @Override
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_summary_documentlist, parent, false);

            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull vHolder holder, int position) {
            holder.filename.setText(uploadDocuments.get(position).getUsername());
        }

        @Override
        public int getItemCount() {
            return uploadDocuments.size();
        }

        public static class vHolder extends RecyclerView.ViewHolder{
            TextView filename;
            public vHolder(@NonNull View itemView) {
                super(itemView);

                filename = itemView.findViewById(R.id.filename_summary);


            }
        }
    }


}