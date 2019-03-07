package com.dginc.deepak.retrofitexample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "https://simplifiedcoding.net/demos/";

    @GET("marvel")    // this will make request url https://simplifiedcoding.net/demos/marvel
    Call<List<SuperHero>> getSuperHeros();
}
