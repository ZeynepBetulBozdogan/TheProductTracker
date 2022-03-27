package com.example.myapplication.Client;

import android.util.Log;

import com.example.myapplication.Service.ApiService;
import com.example.myapplication.model.request.ItemRequest;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient2 {
    Retrofit retrofit;

    public static Retrofit getRetrofit(String token,String item) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                /*Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization",token)
                        .method("itemcode: ",itemcode
                                HttpRequest.BodyProcessor body))
                        .build();
                return chain.proceed(newRequest);*/

                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\r\n\"itemcode\":\""+item+"\"\r\n}");
                Request request = new Request.Builder()
                        .url("http://10.0.2.2:44327/api/Get")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization","Bearer "+ token)
                        .build();
                Response response = client.newCall(request).execute();
                return response;
            }
        };

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder().protocols(Util.immutableListOf(Protocol.HTTP_1_1));
        okHttpBuilder.addInterceptor(logger).addInterceptor(interceptor);
        OkHttpClient okHttpClient = okHttpBuilder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:44327/api/v1/Get")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }
    public static ApiService getApiService(String token,String itemcode) {
        return getRetrofit(token,itemcode).create(ApiService.class);
    }
}

