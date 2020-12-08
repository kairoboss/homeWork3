package com.example.homework3.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherService {
    private static RetrofitApi retrofitApi;

    private WeatherService() {
    }

    public static RetrofitApi getInstance() {
        if (retrofitApi == null) {
            retrofitApi = buildRetrofit();
        }
        return retrofitApi;
    }

    private static RetrofitApi buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitApi.class);
    }
}
