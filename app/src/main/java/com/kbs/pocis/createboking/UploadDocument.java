package com.kbs.pocis.createboking;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kbs.pocis.R;
import com.kbs.pocis.model.createboking.Model_UploadDocument;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.BookingDetailData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;
import static com.kbs.pocis.createboking.UploadDocument.FileUtils.TAG;

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
            if (CheckUriIsLoaded()){
                BookingData.i.file = model_uploadDocuments;
                Fragment fragment = new AddComodity();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                Toasty.error(requireContext(), "Please Add Your File", Toasty.LENGTH_SHORT, true).show();
            }
        });
    }
    boolean CheckUriIsLoaded() {
        if (model_uploadDocuments == null)
            return false;
        for (Model_UploadDocument mod : model_uploadDocuments) {
            if (mod.uri == null)
                return false;
        }
        return true;
    }

    void OpenManager(){
        openFileManager = new Intent(Intent.ACTION_GET_CONTENT);
        openFileManager.setType("application/pdf");
        startActivityForResult(openFileManager, 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Uri path = data.getData();
                files = FileUtils.getFile(getContext(), path);

                //TODO files itu udah automatis dalam format Files document.
                // jadi bisa langsung ke set(Files);

                assert files != null;
                String name = files.getName();
                int size = (int) files.length() / 1024;
                Log.i(TAG, "onActivityResult: " + files);
//                Model_UploadDocument.model_uploadDocuments.add(new Model_UploadDocument(files, name, size));
                model_uploadDocuments.get(LoadPosition).Update(files,name,size);

                //Setting Visibility Layout Upload
                statusList();
            }
        }
    }

    void statusList(){
        recyclerPDF = new RecyclerPDF(getContext(), model_uploadDocuments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        listPdf.setLayoutManager(layoutManager);
        listPdf.setAdapter(recyclerPDF);
    }

    //Function untuk Convert semua URI dan Jenis file untuk mendapatkan Nama
    public static class FileUtils {

        //replace this with your authority
        public static final String AUTHORITY = "com.ianhanniballake.localstorage.documents";


        private FileUtils() {
        } //private constructor to enforce Singleton pattern

        static final String TAG = "FileUtils";
        private static final boolean DEBUG = false; // Set to true to enable logging


        /**
         * @return Whether the URI is a local one.
         */
        public static boolean isLocal(String url) {
            if (url != null && !url.startsWith("http://") && !url.startsWith("https://")) {
                return true;
            }
            return false;
        }


        public static boolean isLocalStorageDocument(Uri uri) {
            return AUTHORITY.equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         * @author paulburke
         */
        public static boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         * @author paulburke
         */
        public static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         * @author paulburke
         */
        public static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         */
        public static boolean isGooglePhotosUri(Uri uri) {
            return "com.google.android.apps.photos.content".equals(uri.getAuthority());
        }

        /**
         * @param context       The context.
         * @param uri           The Uri to query.
         * @param selection     (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         * @author paulburke
         */
        public static String getDataColumn(Context context, Uri uri, String selection,
                                           String[] selectionArgs) {

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {
                    column
            };

            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                        null);
                if (cursor != null && cursor.moveToFirst()) {
                    if (DEBUG)
                        DatabaseUtils.dumpCursor(cursor);

                    final int column_index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(column_index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }

        /**
         * @param context The context.
         * @param uri     The Uri to query.
         * @author paulburke
         * @see #isLocal(String)
         * @see #getFile(Context, Uri)
         */
        public static String getPath(final Context context, final Uri uri) {
            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // LocalStorageProvider
                if (isLocalStorageDocument(uri)) {
                    // The path is the id
                    return DocumentsContract.getDocumentId(uri);
                }
                // ExternalStorageProvider
                else if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };

                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            return null;
        }

        /**
         * Convert Uri into File, if possible.
         *
         * @return file A local file that the Uri was pointing to, or null if the
         * Uri is unsupported or pointed to a remote resource.
         * @author paulburke
         * @see #getPath(Context, Uri)
         */
        public static File getFile(Context context, Uri uri) {
            if (uri != null) {
                String path = getPath(context, uri);
                if (path != null && isLocal(path)) {
                    return new File(path);
                }
            }
            return null;
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
        public vHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_list_pdf, parent, false);
            return new vHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull vHolder holder, int position) {
//            modelUploadDocuments = BookingDetailData.temp_document;
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
                holder.sizefile.setText(String.valueOf(modelUploadDocuments.get(position).getSize()) + " Kb");
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