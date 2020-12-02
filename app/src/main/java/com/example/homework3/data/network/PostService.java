package com.example.homework3.data.network;

import com.example.homework3.data.models.Post;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostService {
    private static PostApi postApi;

    private PostService(){
    }

    public static PostApi getInstance(){
        if (postApi == null){
            postApi = buildRetrofit();
        }
        return postApi;
    }

    private static PostApi buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://android-3-mocker.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(PostApi.class);
    }
}
