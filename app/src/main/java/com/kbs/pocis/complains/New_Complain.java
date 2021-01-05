package com.kbs.pocis.complains;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.kbs.pocis.R;
import com.kbs.pocis.createboking.Finish;
import com.kbs.pocis.model.createboking.Model_UploadDocument;
import com.kbs.pocis.service.BookingData;
import com.kbs.pocis.service.BookingDetailData;
import com.kbs.pocis.service.Calling;
import com.kbs.pocis.service.UserData;
import com.kbs.pocis.service.createbooking.CallingSaveBok;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
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

import static android.app.Activity.RESULT_OK;
import static com.kbs.pocis.complains.New_Complain.FileUtils.TAG;

public class New_Complain extends Fragment {

    ImageView icon_back;
    TextInputEditText title,comment;
    AutoCompleteTextView type;
    Button btn_file,submit;
    LinearLayout linehide;
    RecyclerView list_file;
    Intent openFileManager;
    File files;
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
                        Log.i(ContentValues.TAG, "onResponse: complaint =>  " + data.get(i).reason_desc);
                        arr[i] = data.get(i).reason_desc;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.model_spiner, R.id.val_spiner, arr);
                    type.setAdapter(adapter);
                    type.setThreshold(1);

                    type.setOnItemClickListener((parent, view, position, id) -> {
                        // Get Customer Type ID
                        Log.i(ContentValues.TAG, "onItemClick in complaint: => " + data.get(position).id);
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
            Log.i(ContentValues.TAG, "send_Complain => " + document.uri);
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
                    Log.i(ContentValues.TAG, "onResponse: => " + detailData.id);
                    Toasty.success(requireContext(),"Success Created New Complaint"+ " \n status : " + detailData.status, Toasty.LENGTH_LONG, true).show();
                } else {
                    Toasty.error(requireContext(), "Booking Failure, Maximum size file 2 Mb", Toasty.LENGTH_LONG, true).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<CallingSaveBok> call, @NotNull Throwable t) {
                Log.e(ContentValues.TAG, "onFailure: " + t);
            }
        });
    }

    void OpenManager(){
        openFileManager = new Intent(Intent.ACTION_GET_CONTENT);
        openFileManager.setType("*/*");
        openFileManager.putExtra(Intent.EXTRA_MIME_TYPES,mimetypes);
        startActivityForResult(openFileManager, 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Uri path = data.getData();
                files = FileUtils.getFile(getContext(), path);

                assert files != null;
                String name = files.getName();
                int size = (int) files.length() / 1024;
                Log.i("tag", "check_file: " + files);
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
            holder.sizefile.setText(String.valueOf(modelUploadDocuments.get(position).getSize()) + " Kb");
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
