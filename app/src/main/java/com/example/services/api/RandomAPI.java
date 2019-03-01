package com.example.services.api;

import com.example.services.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomAPI {

    @GET("api")
    Call<ApiResponse> getRandomUser();

    @GET // BASE_ULR?gender=
    Call<ApiResponse> getRandomUser(@Query("gender") String gender);

    @GET // BASE_URL?results=
    Call<ApiResponse> getRandomUsers(@Query("results") int userCount);

}
