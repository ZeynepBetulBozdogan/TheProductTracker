package com.example.myapplication.Client;

import com.example.myapplication.Service.ApiService;
import com.example.myapplication.model.response.LoginResponse;
import com.journeyapps.barcodescanner.Util;

import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
        public static Retrofit getRetrofit() {

            HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
            logger.setLevel(HttpLoggingInterceptor.Level.HEADERS);

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logger).build();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("http://10.0.2.2:44327/api/").client(client).build();

            return retrofit;
        }
    public static ApiService getApiService() {
            return getRetrofit().create(ApiService.class);
        }
    }

