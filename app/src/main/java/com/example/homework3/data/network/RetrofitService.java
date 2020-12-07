package com.example.homework3.data.network;

import com.example.homework3.data.models.Post;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static RetrofitApi retrofitApi;

    private RetrofitService(){
    }

    public static RetrofitApi getInstance(){
        if (retrofitApi == null){
            retrofitApi = buildRetrofit();
        }
        return retrofitApi;
    }

    private static RetrofitApi buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://android-3-mocker.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitApi.class);
    }
}
