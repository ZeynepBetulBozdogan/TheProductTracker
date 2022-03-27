package com.example.myapplication.Service;


import com.example.myapplication.model.request.ItemRequest;
import com.example.myapplication.model.request.LoginRequest;
import com.example.myapplication.model.response.ItemResponse;
import com.example.myapplication.model.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("token")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @POST("Get")
    Call<ItemResponse> item(@Header("Authorization") String authtoken ,@Body ItemRequest itemRequest);

}