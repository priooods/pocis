package com.kbs.pocis.myproject.detail;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.createboking.UploadDocument;
import com.kbs.pocis.model.Model_Project;
import com.kbs.pocis.model.createboking.Model_UploadDocument;

import java.util.ArrayList;
import java.util.List;

public class Documents extends Fragment {

    RecyclerView recyclerView;
    List<Model_UploadDocument> documents;
    TextView title;
    RelativeLayout line;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_document,container,false);
        documents = new ArrayList<>();
        recyclerView = view.findViewById(R.id.list_document);
        title = view.findViewById(R.id.title_doc);
        line = view.findViewById(R.id.line);

        if(Model_Project.isExist()){
            Model_Project data = Model_Project.mp;
            switch (Model_Project.Code){
                case 3:
                    if (data.dokumen_faktur_pajak == null && data.dokumen_kwitans == null && data.dokumen_tanda_tangan_invoice == null &&
                            data.dokumen_tanda_terima == null){
                        title.setVisibility(View.VISIBLE);
                        line.setVisibility(View.GONE);
                    } else if (data.dokumen_faktur_pajak == null && data.dokumen_kwitans == null && data.dokumen_tanda_tangan_invoice == null){
                        documents.add(new Model_UploadDocument(null, data.dokumen_tanda_terima, 2));
                    } else if (data.dokumen_faktur_pajak == null && data.dokumen_kwitans == null){
                        documents.add(new Model_UploadDocument(null, data.dokumen_tanda_tangan_invoice, 2));
                        documents.add(new Model_UploadDocument(null, data.dokumen_tanda_terima, 2));
                    } else if (data.dokumen_faktur_pajak == null){
                        documents.add(new Model_UploadDocument(null, data.dokumen_kwitans, 2));
                        documents.add(new Model_UploadDocument(null, data.dokumen_tanda_tangan_invoice, 2));
                        documents.add(new Model_UploadDocument(null, data.dokumen_tanda_terima, 2));
                    } else {
                        documents.add(new Model_UploadDocument(null, data.dokumen_faktur_pajak, 2));
                        documents.add(new Model_UploadDocument(null, data.dokumen_kwitans, 2));
                        documents.add(new Model_UploadDocument(null, data.dokumen_tanda_tangan_invoice, 2));
                        documents.add(new Model_UploadDocument(null, data.dokumen_tanda_terima, 2));
                    }
                    break;
                case 4:
                    if (data.document_ppj == null && data.document_proforma_invoice == null){
                        title.setVisibility(View.VISIBLE);
                        line.setVisibility(View.GONE);
                    } else if (data.document_ppj == null){
                        documents.add(new Model_UploadDocument(null, data.document_proforma_invoice, 2));
                    } else {
                        documents.add(new Model_UploadDocument(null, data.document_ppj, 2));
                        documents.add(new Model_UploadDocument(null, data.document_proforma_invoice, 2));
                    }
                    break;
            }

        }

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
            holder.sizefile.setText(String.valueOf(modelUploadDocuments.get(position).getSize() + " Mb"));
            holder.line.setVisibility(View.GONE);
            holder.deletefile.setVisibility(View.GONE);

        }

        @Override
        public int getItemCount() {
            return modelUploadDocuments.size();
        }

        public static class vHolder extends RecyclerView.ViewHolder{

            TextView nama, deletefile, sizefile;
            View line;

            public vHolder(@NonNull View itemView) {
                super(itemView);
                line = itemView.findViewById(R.id.ln);
                nama = itemView.findViewById(R.id.model_uploadpdf_name);
                deletefile = itemView.findViewById(R.id.delete_files);
                sizefile = itemView.findViewById(R.id.model_uploadpdf_size);
            }
        }
    }
}
