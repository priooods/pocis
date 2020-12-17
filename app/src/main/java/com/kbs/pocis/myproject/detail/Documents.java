package com.kbs.pocis.myproject.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.createboking.UploadDocument;
import com.kbs.pocis.model.createboking.Model_UploadDocument;

import java.util.ArrayList;
import java.util.List;

public class Documents extends Fragment {

    RecyclerView recyclerView;
    List<Model_UploadDocument> documents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_document,container,false);

        recyclerView = view.findViewById(R.id.list_document);

        documents = new ArrayList<>();
        documents.add(new Model_UploadDocument(null, "Document File Name", 1800));
        documents.add(new Model_UploadDocument(null, "Document File Dua", 1000));
        documents.add(new Model_UploadDocument(null, "Document File Tiga", 1200));
        RecyclerPDF recyclerPDF = new RecyclerPDF(getContext(), documents);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerPDF);
        return view;
    }

    public static class RecyclerPDF extends RecyclerView.Adapter<RecyclerPDF.vHolder>{

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
        public void onBindViewHolder(@NonNull RecyclerPDF.vHolder holder, int position) {
            holder.nama.setText(modelUploadDocuments.get(position).getUsername());
            holder.sizefile.setText(String.valueOf(modelUploadDocuments.get(position).getSize() + R.string.size));
            holder.deletefile.setVisibility(View.GONE);

        }

        @Override
        public int getItemCount() {
            return modelUploadDocuments.size();
        }

        public static class vHolder extends RecyclerView.ViewHolder{

            TextView nama, deletefile, sizefile;

            public vHolder(@NonNull View itemView) {
                super(itemView);

                nama = itemView.findViewById(R.id.model_uploadpdf_name);
                deletefile = itemView.findViewById(R.id.delete_files);
                sizefile = itemView.findViewById(R.id.model_uploadpdf_size);
            }
        }
    }
}
