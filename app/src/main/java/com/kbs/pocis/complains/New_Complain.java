package com.kbs.pocis.complains;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.activity.CreateBooking;
import com.kbs.pocis.item.RealPathUtil;
import com.kbs.pocis.model.createboking.Model_UploadDocument;
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.createbooking.CallingSaveBok;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class New_Complain extends Fragment {

    ImageView icon_back;
    TextInputEditText title,comment;
    AutoCompleteTextView type;
    Button btn_file,submit;
    LinearLayout linehide;
    RecyclerView list_file;
    Intent openFileManager;
    public static int REQUEST_SETTING = 168;
    File files;
    public static int PRIVATE_CODE = 1;
    RecyclerPDF recyclerPDF;
    ArrayList<Model_UploadDocument> model_uploadDocuments;
    int idComplaintType;

    //support file
    String[] mimetypes = {
            "application/pdf",
            "image/*",
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.complain_new,container,false);
        title = view.findViewById(R.id.input_complain_title);
        type = view.findViewById(R.id.input_complain_type);
        comment = view.findViewById(R.id.input_complain_comment);
        model_uploadDocuments = new ArrayList<>();
        btn_file = view.findViewById(R.id.btn_file_complain);
        btn_file.setOnClickListener(v -> OpenManager());
        submit = view.findViewById(R.id.btn_submit_complain);
        linehide = view.findViewById(R.id.ln_file);
        list_file = view.findViewById(R.id.list_file_complain);

        getMyComplaintType();
        icon_back = view.findViewById(R.id.btn_back_newcomplain);
        icon_back.setOnClickListener(v -> requireActivity().onBackPressed());

        submit.setOnClickListener(v->{
            if (idComplaintType == 0){
                Toasty.error(requireContext(),"Complaint Type Not Valid !", Toasty.LENGTH_SHORT, true).show();
            } else {
                saveNewComplaint();
            }
        });
        Permission();

        return view;
    }

    private void  getMyComplaintType(){
        Call<List<BookingDetailData>> call = UserData.i.getService().getMyComplaintType();
        call.enqueue(new Callback<List<BookingDetailData>>() {
            @Override
            public void onResponse(@NotNull Call<List<BookingDetailData>> call, @NotNull Response<List<BookingDetailData>> response) {
                List<BookingDetailData> data = response.body();
                assert data != null;
                if (data.size() > 0){
                    String[] arr = new String[data.size()];
                    for (int i = 0; i < arr.length; i++) {
                        Log.i(TAG, "onResponse: complaint =>  " + data.get(i).reason_desc);
                        arr[i] = data.get(i).reason_desc;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.model_spiner, R.id.val_spiner, arr);
                    type.setAdapter(adapter);
                    type.setThreshold(1);

                    type.setOnItemClickListener((parent, view, position, id) -> {
                        // Get Customer Type ID
                        Log.i(TAG, "onItemClick in complaint: => " + data.get(position).id);
                        idComplaintType = data.get(position).id;
                    });

                    //click box input_type, show suggestions
                    type.setOnTouchListener(new View.OnTouchListener() {
                        @SuppressLint("ClickableViewAccessibility")
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (data.size() > 0){
                                if (!type.getText().toString().equals("")) adapter.getFilter().filter(null);
                                type.showDropDown();
                            }
                            return false;
                        }
                    });
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<BookingDetailData>> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    public RequestBody data_form(File req) {
        return RequestBody.create(req, MediaType.parse("multipart/form-data"));
    }

    public RequestBody input_form(String req) {
        return RequestBody.create(req, MediaType.parse("multipart/form-data"));
    }

    public void saveNewComplaint(){

        HashMap<String, Integer> ComplaintReason = new HashMap<>();
        ComplaintReason.put("m_complain_reason_id", idComplaintType);

        HashMap<String, RequestBody> Complain = new HashMap<>();
        Complain.put("complain_desc", input_form(Objects.requireNonNull(comment.getText()).toString()));
        Complain.put("complain_title", input_form(Objects.requireNonNull(title.getText()).toString()));

        int i = 0;
        MultipartBody.Part[] fileToUpload = new MultipartBody.Part[model_uploadDocuments.size()];
        for (Model_UploadDocument document : model_uploadDocuments){
            fileToUpload[i] = MultipartBody.Part.createFormData("ComplaintDocument[file_name]["+i+"]", document.uri.getPath(), data_form(document.uri));
            Log.i(TAG, "send_Complain => " + document.uri);
            i++;
        }

        retrofit2.Call<CallingSaveBok> call = UserData.i.getService().saveComplaint(
                UserData.i.getToken(),ComplaintReason,Complain,fileToUpload
        );
        call.enqueue(new Callback<CallingSaveBok>() {
            @Override
            public void onResponse(@NotNull retrofit2.Call<CallingSaveBok> call, @NotNull Response<CallingSaveBok> response) {
                CallingSaveBok data = response.body();
                if (Calling.TreatResponse(getContext(),"create_complain",data)) {
                    assert data != null;
                    BookingDetailData detailData = data.data;
                    Log.i(TAG, "onResponse: => " + detailData.id);
                    Toasty.success(requireContext(),"Success Created New Complaint"+ " \n status : " + detailData.status, Toasty.LENGTH_LONG, true).show();
                } else {
                    Toasty.error(requireContext(), "Booking Failure, Maximum size file 2 Mb", Toasty.LENGTH_LONG, true).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingSaveBok> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure: " + t);
            }
        });
    }

    public void Permission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Log.i("home", "Permission Storage: " + true);
            } else if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
                MDToast.makeText(requireContext(), "You Must Have Enable Permission", MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING).show();
                openSetting();
            } else {
                Storage();
            }
        }
    }

    public void Storage(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(requireContext()).setTitle("Permission Required")
                    .setMessage("You must give document opening permission to upload files")
                    .setPositiveButton("Yes", (dialog, which) -> ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRIVATE_CODE))
                    .setNegativeButton("No", (dialog, which) ->{
                        dialog.dismiss();
                        requireActivity().onBackPressed();
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PRIVATE_CODE);
        }
    }

    public void OpenManager(){
        openFileManager = new Intent(Intent.ACTION_GET_CONTENT);
        openFileManager.setType("*/*");
        openFileManager.putExtra(Intent.EXTRA_MIME_TYPES,mimetypes);
        startActivityForResult(openFileManager, 10);
    }

    public void openSetting(){
        Intent setting = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:"+ requireActivity().getPackageName()));
        setting.addCategory(Intent.CATEGORY_DEFAULT);
        setting.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(setting,REQUEST_SETTING);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PRIVATE_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.i(TAG, "onRequestPermissionsResult: " + true);
            }
            else {
                requireActivity().onBackPressed();
                requireActivity().finish();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Uri path = data.getData();
                String filePath = RealPathUtil.getRealPathFromURI_API19(requireContext(), path);
                assert filePath != null;
                files = new File(filePath);

                String name = files.getName();
                int size = (int) files.length() / 1024;
                model_uploadDocuments.add(new Model_UploadDocument(files, name, size));
                if (size >= 2000) {
                    Toasty.error(requireContext(), "Maximum File Size 2Mb", Toasty.LENGTH_LONG, true).show();
                } else {
                    statusList(model_uploadDocuments);
                }
            }
        }
    }

    void statusList(ArrayList<Model_UploadDocument> document){
        if (document != null && document.size() > 0){
            linehide.setVisibility(View.VISIBLE);
            recyclerPDF = new RecyclerPDF(getContext(), model_uploadDocuments);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            list_file.setLayoutManager(layoutManager);
            list_file.setAdapter(recyclerPDF);
            recyclerPDF.notifyDataSetChanged();
        }
    }

    public class RecyclerPDF extends RecyclerView.Adapter<RecyclerPDF.vHolder>{

        Context context;
        List<Model_UploadDocument> modelUploadDocuments;

        public RecyclerPDF(Context context, List<Model_UploadDocument> modelUploadDocuments) {
            this.context = context;
            this.modelUploadDocuments = modelUploadDocuments;
        }

        @NonNull
        @Override
        public RecyclerPDF.vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_list_pdf, parent, false);
            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull vHolder holder, int position) {
            String name = model_uploadDocuments.get(position).getUsername().substring(model_uploadDocuments.get(position).username.length() -3);
            Log.i(TAG, "onBindViewHolder: " + name);
            if (name.equals("jpg")){
                holder.folderimage.setImageResource(R.drawable.jpg_image);
                holder.folderimage.setScaleType(ImageView.ScaleType.CENTER);
            } else if(name.equals("png")){
                holder.folderimage.setImageResource(R.drawable.png_image);
                holder.folderimage.setScaleType(ImageView.ScaleType.CENTER);
            }

            holder.nama.setText(modelUploadDocuments.get(position).getUsername());
            holder.sizefile.setText((modelUploadDocuments.get(position).getSize()) + " Kb");
            holder.deletefile.setOnClickListener(v -> {
                modelUploadDocuments.remove(position);
                notifyItemRemoved(position);
                notifyItemChanged(position, modelUploadDocuments.size());
                if (modelUploadDocuments.size() == 0){
                    linehide.setVisibility(View.GONE);
                }
            });

        }

        @Override
        public int getItemCount() {
            return modelUploadDocuments.size();
        }

        public class vHolder extends RecyclerView.ViewHolder{

            TextView nama, deletefile, sizefile;
            ImageView folderimage;

            public vHolder(@NonNull View itemView) {
                super(itemView);//pdfimg

                folderimage = itemView.findViewById(R.id.pdfimg);
                nama = itemView.findViewById(R.id.model_uploadpdf_name);
                deletefile = itemView.findViewById(R.id.delete_files);
                sizefile = itemView.findViewById(R.id.model_uploadpdf_size);
            }
        }
    }
}
