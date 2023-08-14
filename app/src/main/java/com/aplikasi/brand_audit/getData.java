package com.aplikasi.brand_audit;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class getData {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    final OkHttpClient client = new OkHttpClient();

    String Auth(String url, RequestBody formBody) throws IOException {
        Request request;
            request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    String getData(String url, RequestBody formBody, String sessionID) throws IOException {

        Request request = new Request.Builder()
                .header("session-id", sessionID)
                .url(url)
                .post(formBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    String pushData(String url, RequestBody formBody) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .put(formBody)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
