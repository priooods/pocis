package com.kbs.pocis.createboking;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.FileUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.kbs.pocis.R;
import com.kbs.pocis.api.UserService;
import com.kbs.pocis.model.Model_Commodity;
import com.kbs.pocis.model.createboking.Model_SelectTemplate;
import com.kbs.pocis.model.createboking.Model_ShowTemplate;
import com.kbs.pocis.model.createboking.Model_UploadDocument;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.BookingList;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.createbooking.CallingList;
import com.kbs.pocis.service.createbooking.CallingSaveBok;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Summary extends Fragment {

    Button next, prev;
    TextView customer_type, related, contract, veselname, discharge,
            port, arival, departure, loading;

    RecyclerView list_serviceInfo, list_comodityInfo, list_Document;
    ArrayList<Model_Commodity> summaryComodities ;
    ArrayList<Model_UploadDocument> uploadDocuments;
    List<BookingData.BookTemplate> modelShowTemplates;
    AdapterComodity adapterComodity;
    AdapterServices adapterServices;
    AdapterFile adapterFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

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
        customer_type.setText(data.customerType);
        related.setText(data.relatedVesel);
        contract.setText(data.contract);
        discharge.setText(data.vessel.voyage_number);
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

        uploadDocuments = BookingData.i.file;
        adapterFile = new AdapterFile(getContext(), uploadDocuments);
        list_Document.setLayoutManager(manager);
        list_Document.setAdapter(adapterFile);
    }

    private void ListingComodityList() {
        summaryComodities = BookingData.i.commodity;
        adapterComodity = new AdapterComodity(getContext(), summaryComodities);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        list_comodityInfo.setLayoutManager(layoutManager);
        list_comodityInfo.setAdapter(adapterComodity);
    }

    public void ButtonFunction(){
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogCancell(getContext());
            }
        });
    }

    public void ShowDialogCancell (final Context context){
        View view  = LayoutInflater.from(context).inflate(R.layout.dialog_cancelled, null);
        final Dialog dialogFragment = new Dialog(context);
        dialogFragment.setCancelable(true);
        dialogFragment.setContentView(view);
        dialogFragment.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextInputLayout input_alasan = view.findViewById(R.id.onp);
        input_alasan.setVisibility(View.GONE);
        TextView title = view.findViewById(R.id.tl);
        ImageView bg = view.findViewById(R.id.bc);
        bg.setImageResource(R.drawable.crb);
        title.setText("Are you sure want to create booking based on your provided information?");
        title.setGravity(Gravity.CENTER);

        Button btn_close = view.findViewById(R.id.btn_cancelclose);
        btn_close.setText("No");
        btn_close.setAllCaps(false);
        Button btn_go = view.findViewById(R.id.btn_cancelbookinggo);
        btn_go.setText("Yes");
        btn_go.setBackground(getResources().getDrawable(R.drawable.btn_green));
        btn_go.setAllCaps(false);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragment.cancel();
            }
        });
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BookingData.i.Upload();
                SendDataBooking();
//                dialogFragment.dismiss();
//                Fragment fragment = new Finish();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.frameCreate, fragment);
//                fragmentTransaction.commit();
            }
        });
        dialogFragment.show();
    }

    public void SendDataBooking(){
        BookingData data = BookingData.i;
//        CallingList list


//        ArrayList<HashMap<String, String>> DataBooking = new ArrayList<>();
        HashMap<String, String> Booking = new HashMap<>();
//        Booking.put("flag_related_vessel", data.relatedVesel);
//        Booking.put("t_map_customer_type_id", data.customerId);
//        Booking.put("customer_type_code", data.customerType);
//        Booking.put("m_customer_id", "55");
//        Booking.put("flag_contract", data.contract);
        //DataBooking.add(Booking);

        Log.i(TAG, "SendDataBooking: Booking => " + Booking);

        ArrayList<HashMap<String, String>> DataVesel = new ArrayList<>();
        HashMap<String, String> BookingVessel = new HashMap<>();
//        BookingVessel.put("m_vessel_id", data.vessel.vessel_name);
//        BookingVessel.put("voyage_no", data.vessel.voyage_number);
//        BookingVessel.put("estimate_arrival_date", data.vessel.estimate_arival);
//        BookingVessel.put("estimate_departure_date", data.vessel.estimate_departure);
//        BookingVessel.put("port_of_loading_id", data.vessel.port_discharge);
//        BookingVessel.put("m_port_cigading_id", data.vessel.port_origin);
        DataVesel.add(BookingVessel);

        Log.i(TAG, "SendDataBooking: " + DataVesel);


        ArrayList<HashMap<String, String>> DataVeselSchedule = new ArrayList<>();
//        HashMap<String, String> VesselSchedule = new HashMap<>();
//        VesselSchedule.put("id", String.valueOf(data.vessel.id_voyage));
//        DataVeselSchedule.add(VesselSchedule);

        Log.i(TAG, "SendDataBooking: " + DataVeselSchedule);

        int i = 0;
        ArrayList<HashMap<Integer, ArrayList<Map<String, String>>>> comodity = new ArrayList<>();
//        HashMap<Integer, ArrayList<Map<String, String>>> CommodityBooking = new HashMap<>(data.commodity.size());
//        ArrayList<Map<String, String>> anak1 = new ArrayList<>();
//        for (Model_Commodity com : data.commodity) {
//            anak1.add(com.getMap());
//            CommodityBooking.put(i,anak1);
//            i++;
//        }
//        comodity.add(CommodityBooking);
        Log.i(TAG, "SendDataBooking: " + comodity);



        i = 0;
//        ArrayList<HashMap<Integer, ArrayList<Map<String,String>>>> Services = new ArrayList<>();
        HashMap<Integer, ArrayList<Map<String,String>>> service = new HashMap<>();
//        ArrayList<Map<String,String>> anak = new ArrayList<>();
//
//        for (BookingData.BookTemplate t : BookingData.i.template){
//            for(BookingData.BookTemplate.BookTempList a : t.listCheck){
//                anak.add(a.getMap());
//                service.put(i, anak);
//                i++;
//            }
//        }
//        Services.add(service);
        Log.i(TAG, "SendDataBooking: " + service);



        int a = 0;
        ArrayList<HashMap<String, ArrayList<HashMap<Integer, MultipartBody.Part>>>> DataDocument = new ArrayList<>();
//        HashMap<String, ArrayList<HashMap<Integer, MultipartBody.Part>>> file_name = new HashMap<>();
//        ArrayList<HashMap<Integer, MultipartBody.Part>> anakdocument = new ArrayList<>();
//        HashMap<Integer, MultipartBody.Part> dt = new HashMap<>();
//        for (Model_UploadDocument document : BookingData.i.file){
//            RequestBody requestBody = RequestBody.create(document.getUri(),MediaType.parse("application/pdf"));
//            MultipartBody.Part.create(requestBody);
//            dt.put(a, MultipartBody.Part.createFormData("file",document.getUsername(),requestBody));
//            a++;
//        }
//        anakdocument.add(dt);
//        file_name.put("file_name", anakdocument);
//        DataDocument.add(file_name);
        Log.i(TAG, "SendDataBooking: " + DataDocument);

        Call<CallingSaveBok> call = UserData.i.getService().saveBooking(
                UserData.i.getToken()
//                Booking
//                DataVesel,
//                DataVeselSchedule,
//                comodity,
//                service,
//                DataDocument
                );
        call.enqueue(new Callback<CallingSaveBok>() {
            @Override
            public void onResponse(Call<CallingSaveBok> call, Response<CallingSaveBok> response) {
                CallingSaveBok data = response.body();
//                Log.i(TAG, "onResponse: => " + data.desc);
                if (Calling.TreatResponse(getContext(),"create_booking",data)) {
                    BookingDetailData detailData = data.data;
                    Log.i(TAG, "onResponse: => " + detailData.no_booking);
                }
            }

            @Override
            public void onFailure(Call<CallingSaveBok> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t);
//                Log.i(TAG, "onFailure: => " + call.toString());
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