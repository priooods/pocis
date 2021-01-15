package com.kbs.pocis.myproject.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.Model_Project;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Documents extends Fragment {

    RecyclerView recyclerView;
    List<Model_Project> documents;
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

        if (Model_Project.isExist()) {
            switch (Model_Project.Code) {
                case 2:
                    if (Model_Project.Documents.size() > 0) {
                        Log.i(TAG, "onCreateView: " + "ada");
                        documents.addAll(Model_Project.Documents);
                        RecyclerPDF listbapj = new RecyclerPDF(getContext(), documents, 1);
                        LinearLayoutManager mangerbapj = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(mangerbapj);
                        recyclerView.setAdapter(listbapj);
                    } else {
                        title.setVisibility(View.VISIBLE);
                        line.setVisibility(View.GONE);
                    }
                    break;
                case 3:
                    if (Model_Project.InformationAndDocument.get(0).dokumen_faktur_pajak == null
                            && Model_Project.InformationAndDocument.get(0).dokumen_kwitans == null
                            && Model_Project.InformationAndDocument.get(0).dokumen_tanda_tangan_invoice == null &&
                            Model_Project.InformationAndDocument.get(0).dokumen_tanda_terima == null) {
                        title.setVisibility(View.VISIBLE);
                        line.setVisibility(View.GONE);
                    }
                    if (Model_Project.InformationAndDocument.get(0).dokumen_tanda_tangan_invoice != null) {
                        documents.add(new Model_Project(Model_Project.InformationAndDocument.get(0).dokumen_tanda_tangan_invoice));
                    }
                    if (Model_Project.InformationAndDocument.get(0).dokumen_tanda_terima != null) {
                        documents.add(new Model_Project(Model_Project.InformationAndDocument.get(0).dokumen_tanda_terima));
                    }
                    if (Model_Project.InformationAndDocument.get(0).dokumen_faktur_pajak != null) {
                        documents.add(new Model_Project(Model_Project.InformationAndDocument.get(0).dokumen_faktur_pajak));//dokumen_kwitans
                    }
                    if (Model_Project.InformationAndDocument.get(0).dokumen_kwitans != null) {
                        documents.add(new Model_Project(Model_Project.InformationAndDocument.get(0).dokumen_kwitans));
                    }
                    RecyclerPDF listinvoice = new RecyclerPDF(getContext(), documents, 0);
                    LinearLayoutManager managerinvoice = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(managerinvoice);
                    recyclerView.setAdapter(listinvoice);
                    break;
                case 4:
                    if (Model_Project.InformationAndDocument.get(0).document_ppj == null && Model_Project.InformationAndDocument.get(0).document_proforma_invoice == null) {
                        title.setVisibility(View.VISIBLE);
                        line.setVisibility(View.GONE);
                    } else if (Model_Project.InformationAndDocument.get(0).document_ppj == null) {
                        documents.add(new Model_Project(Model_Project.InformationAndDocument.get(0).document_proforma_invoice));
                    } else {
                        documents.add(new Model_Project(Model_Project.InformationAndDocument.get(0).document_ppj));
                        documents.add(new Model_Project(Model_Project.InformationAndDocument.get(0).document_proforma_invoice));
                    }
                    RecyclerPDF listproforma = new RecyclerPDF(getContext(), documents, 2);
                    LinearLayoutManager mangerproforma = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(mangerproforma);
                    recyclerView.setAdapter(listproforma);
                    break;
            }
        }


        return view;
    }

    public static class RecyclerPDF extends RecyclerView.Adapter<RecyclerPDF.vHolder>{

        Context context;
        List<Model_Project> model_document;
        int type;

        public RecyclerPDF(Context context, List<Model_Project> model_document, int type) {
            this.context = context;
            this.model_document = model_document;
            this.type = type;
        }

        @NonNull
        @Override
        public RecyclerPDF.vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_list_pdf, parent, false);
            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerPDF.vHolder holder, int position) {
            switch (type){
                case 0://for invoice
                    holder.sizefile.setVisibility(View.GONE);
                    holder.line.setVisibility(View.GONE);
                    holder.deletefile.setVisibility(View.GONE);
                    String[] srr = model_document.get(position).dokumen_faktur_pajak.split("\\|");
                    holder.tap.setOnClickListener(v->{
                        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse(srr[1]));
                        this.context.startActivity(web);
                    });
                    holder.nama.setText(srr[0]);
                    break;
                case 2: //& proforma
                    holder.nama.setText(model_document.get(position).dokumen_faktur_pajak);
                    holder.sizefile.setVisibility(View.GONE);
                    holder.line.setVisibility(View.GONE);
                    holder.deletefile.setVisibility(View.GONE);
//                    String[] srr = model_document.get(position).dokumen_faktur_pajak.split("|", 2);
//                    holder.tap.setOnClickListener(v->{
//                        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse(model_document.get(position).file_link));
//                        this.context.startActivity(web);
//                    });
                    break;
                case 1: //for bapj
                    holder.nama.setText(model_document.get(position).file_name);
                    holder.sizefile.setVisibility(View.GONE);
                    holder.line.setVisibility(View.GONE);
                    holder.deletefile.setVisibility(View.GONE);
                    holder.tap.setOnClickListener(v->{
                        Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse(model_document.get(position).file_link));
                        this.context.startActivity(web);
                    });
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return model_document.size();
        }

        public static class vHolder extends RecyclerView.ViewHolder{

            ConstraintLayout tap;
            TextView nama, deletefile, sizefile;
            View line;

            public vHolder(@NonNull View itemView) {
                super(itemView);
                line = itemView.findViewById(R.id.ln);
                tap = itemView.findViewById(R.id.tap);
                nama = itemView.findViewById(R.id.model_uploadpdf_name);
                deletefile = itemView.findViewById(R.id.delete_files);
                sizefile = itemView.findViewById(R.id.model_uploadpdf_size);
            }
        }
    }
}
