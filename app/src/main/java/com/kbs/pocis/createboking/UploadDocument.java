package com.kbs.pocis.createboking;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kbs.pocis.R;
import com.kbs.pocis.model.createboking.Model_SelectTemplate;
import com.kbs.pocis.model.createboking.Model_UploadDocument;
import com.kbs.pocis.onlineboking.CancelBooking;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.MODE_WORLD_READABLE;

public class UploadDocument extends Fragment {

    Button next, prev;
    TextView coba;
    RecyclerView listPdf;
    Button addfile_one, addfile_two;
    LinearLayout line_one;
    RelativeLayout line_two;
    ArrayList<Model_UploadDocument> model_uploadDocuments = new ArrayList<>();
    RecyclerPDF recyclerPDF;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_document, container, false);

        next = view.findViewById(R.id.upload_document_nextBtn);
        prev = view.findViewById(R.id.upload_document_prevBtn);
        line_one = view.findViewById(R.id.document_upload_layoutone);
        line_two = view.findViewById(R.id.document_upload_layouttwo);
        listPdf = view.findViewById(R.id.document_upload_recyclerpdf);
        addfile_one = view.findViewById(R.id.document_upload_btnUpload);
        addfile_two = view.findViewById(R.id.document_upload_btnUploadtwo);
        coba = view.findViewById(R.id.xx);

        ButtonFunction();
        line_one.setVisibility(View.GONE);
        line_two.setVisibility(View.VISIBLE);
        ButtonAddFile();

        return view;
    }

    public void ButtonAddFile(){
        addfile_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePickerBuilder.getInstance()
                        .enableSelectAll(false)
                        .setMaxCount(8)
                        .pickFile(UploadDocument.this);
            }
        });
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
                Fragment fragment = new AddComodity();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameCreate, fragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
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
            holder.nama.setText(modelUploadDocuments.get(position).getUsername());
            holder.sizefile.setText(String.valueOf(modelUploadDocuments.get(position).getSize()));

        }

        @Override
        public int getItemCount() {
            return modelUploadDocuments.size();
        }

        public class vHolder extends RecyclerView.ViewHolder{

            TextView nama, deletefile, sizefile;

            public vHolder(@NonNull View itemView) {
                super(itemView);

                nama = itemView.findViewById(R.id.model_uploadpdf_name);
                deletefile = itemView.findViewById(R.id.delete_files);
                sizefile = itemView.findViewById(R.id.model_uploadpdf_size);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case FilePickerConst.REQUEST_CODE_DOC:
                if(resultCode== Activity.RESULT_OK && data!=null)
                 {

                    ArrayList<Uri> docPaths = new ArrayList<>();

                    docPaths.addAll(data.<Uri>getParcelableArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                    model_uploadDocuments = new ArrayList<>(docPaths.size());
                    for (Uri pats : docPaths){
                        FileUtils.getFile(getContext(), pats);
                        File files = FileUtils.getFile(getContext(), pats);
                        model_uploadDocuments.add(new Model_UploadDocument(pats,files.getName(), (int)files.length() / 1024));

                        Log.i("Name", "onActivityResult: " + pats);
                        Log.i("Size document", "--> " + files.length() / 1024);


                        recyclerPDF = new RecyclerPDF(getContext(), model_uploadDocuments);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        listPdf.setLayoutManager(layoutManager);
                        listPdf.setAdapter(recyclerPDF);

//                        ArrayAdapter<String> arrayAdapter =
//                                new ArrayAdapter<String>(getContext(),R.layout.model_list_pdf, R.id.model_uploadpdf_name, liis);
//                        listPdf.setAdapter(arrayAdapter);
                    }

                }
                break;
        }
    }

    public static class FileUtils {

        //replace this with your authority
        public static final String AUTHORITY = "com.ianhanniballake.localstorage.documents";


        private FileUtils() {
        } //private constructor to enforce Singleton pattern

        /**
         * TAG for log messages.
         */
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
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.
         *
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
         * Get a file path from a Uri. This will get the the path for Storage Access
         * Framework Documents, as well as the _data field for the MediaStore and
         * other file-based ContentProviders.<br>
         * <br>
         * Callers should check whether the path is local before assuming it
         * represents a local file.
         *
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

                    // TODO handle non-primary volumes
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

}