package com.kbs.pocis.createboking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kbs.pocis.R;
import com.kbs.pocis.item.RealPathUtil;
import com.kbs.pocis.model.createboking.Model_UploadDocument;
import com.kbs.pocis.service.BookingData;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class UploadDocument extends Fragment {

    Button next, prev;

    RelativeLayout line_two;

    RecyclerView listPdf;
    RecyclerPDF recyclerPDF;
    ArrayList<Model_UploadDocument> model_uploadDocuments;

    Intent openFileManager;
    File files;
    int LoadPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_document, container, false);

        next = view.findViewById(R.id.upload_document_nextBtn);
        prev = view.findViewById(R.id.upload_document_prevBtn);
        line_two = view.findViewById(R.id.document_upload_layouttwo);
        listPdf = view.findViewById(R.id.document_upload_recyclerpdf);

        if (BookingData.isExist()){
            if (BookingData.i.file != null){
                // Already Opened
                model_uploadDocuments = BookingData.i.file;
            }else{
                model_uploadDocuments = new ArrayList<>();
            }
        }
        if (model_uploadDocuments != null) {
            for(Model_UploadDocument mod : model_uploadDocuments) {
                mod.getString();
            }
        }
        statusList();
        ButtonFunction();
        return view;
    }

    public void ButtonFunction(){
        prev.setOnClickListener(v -> {
            BookingData.i.file = model_uploadDocuments;
            requireActivity().onBackPressed();
        });

        next.setOnClickListener(v -> {
            if(CheckUriIsLoaded()) {
                if (BookingData.i.checkVesselInfoSkip()) {
                    BookingData.i.vessel = new BookingData.VesselData();
                    BookingData.i.commodity = null;
                    BookingData.i.file = model_uploadDocuments;
                    Fragment fragment = new Summary();
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    BookingData.i.file = model_uploadDocuments;
                    Fragment fragment = new AddComodity();
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                    fragmentTransaction.commit();
                }
            } else {
                MDToast.makeText(requireContext(), "Please Add Your File", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
            }
        });
    }

    //Check Size list Document
    boolean CheckUriIsLoaded() {
        if (model_uploadDocuments == null)
            return false;
        for (Model_UploadDocument mod : model_uploadDocuments) {
            if (mod.uri == null)
                return false;
        }
        return true;
    }

    public void OpenManager(){
        openFileManager = new Intent(Intent.ACTION_GET_CONTENT);
        openFileManager.setType("application/pdf");
        startActivityForResult(openFileManager, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Uri path = data.getData();

                String filePath = RealPathUtil.getRealPathFromURI_API19(requireContext(), path);
                assert filePath != null;
                files = new File(filePath);

                String name = files.getName();
                int size = (int) files.length() / 1024;
                Log.i(TAG, "file: " + "name = " + name + " size = " + size);
                model_uploadDocuments.get(LoadPosition).Update(files,name,size);
                statusList();
            }
        }
    }

    public void statusList(){
        listPdf.setHasFixedSize(true);
        recyclerPDF = new RecyclerPDF(getContext(), model_uploadDocuments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        listPdf.setLayoutManager(layoutManager);
        listPdf.setAdapter(recyclerPDF);
        recyclerPDF.notifyDataSetChanged();
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
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_list_pdf, parent, false);
            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull vHolder holder, int position) {
            holder.titles_documents_temp.setText(modelUploadDocuments.get(position).description);
            if (modelUploadDocuments.get(position).uri == null) {
                holder.titles_documents_temp.setVisibility(View.VISIBLE);
                holder.btnUpload.setVisibility(View.VISIBLE);
                holder.cs_ln.setVisibility(View.GONE);
            } else {
                holder.titles_documents_temp.setVisibility(View.VISIBLE);
                holder.btnUpload.setVisibility(View.GONE);
                holder.cs_ln.setVisibility(View.VISIBLE);
                holder.nama.setText(modelUploadDocuments.get(position).getUsername());
                holder.sizefile.setText(modelUploadDocuments.get(position).getSize() + " Kb");
            }
            holder.deletefile.setOnClickListener(v -> {
                model_uploadDocuments.get(position).uri = null;
                notifyDataSetChanged();
            });

            holder.btnUpload.setOnClickListener(v->{
                OpenManager();
                LoadPosition = position;
            });
        }

        @Override
        public int getItemCount() {
            return modelUploadDocuments.size();
        }

        public class vHolder extends RecyclerView.ViewHolder{

            TextView nama, deletefile, sizefile, titles_documents_temp;
            Button btnUpload;
            ConstraintLayout cs_ln;

            public vHolder(@NonNull View itemView) {
                super(itemView);

                nama = itemView.findViewById(R.id.model_uploadpdf_name);
                cs_ln = itemView.findViewById(R.id.cs_ln);
                deletefile = itemView.findViewById(R.id.delete_files);
                titles_documents_temp = itemView.findViewById(R.id.titles_documents_temp);
                btnUpload = itemView.findViewById(R.id.btnUpload);
                sizefile = itemView.findViewById(R.id.model_uploadpdf_size);
            }
        }
    }

}