package com.example.ezycommerce_2201816783.fragment;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    private static String BASE_URL = "https://u73olh7vwg.execute-api.ap-northeast-2.amazonaws.com/staging/";

    public static Retrofit getRetrofit (){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
