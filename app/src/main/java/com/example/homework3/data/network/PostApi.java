package com.example.homework3.data.network;

import android.service.autofill.AutofillService;

import com.example.homework3.data.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostApi {

    @GET("posts/{postId}")
    Call<Post> getPost(
            @Path("postId") Integer postId
    );
    @GET("posts")
    Call<List<Post>> getAllPosts();

    @POST("posts")
    Call<Post> newPost(@Body Post post);

    @DELETE("posts/{postId}")
    Call<Post> deletePost(
            @Path("postId") Integer postId
    );
    @FormUrlEncoded
    @PUT("posts/{postId}")
            Call<Post> updatePost(
            @Path("postId") Integer roomId,
            @Field("title") String title,
            @Field("content") String content,
            @Field("user") Integer user,
            @Field("group") Integer group
    );
}
