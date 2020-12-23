package com.kbs.pocis.api;
import android.os.Build;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
//import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static final String BASE_URL = "http://cigading.ptkbs.co.id:9280/v1/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            /* ConnectionSpec.MODERN_TLS is the default value */
            List tlsSpecs = Arrays.asList(ConnectionSpec.MODERN_TLS);

            /* providing backwards-compatibility for API lower than Lollipop: */
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                tlsSpecs = Arrays.asList(ConnectionSpec.COMPATIBLE_TLS);
            }

            OkHttpClient client = new OkHttpClient.Builder()
//                    .connectionSpecs(tlsSpecs)
                    .readTimeout(100,TimeUnit.SECONDS)
                    .connectTimeout(100,TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
