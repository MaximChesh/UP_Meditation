package com.example.madmeditation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("user/login")
    Call<MaskaUser> createU(@Body MaskaLogin maskaLogin);
}
