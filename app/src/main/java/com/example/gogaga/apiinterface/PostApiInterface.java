package com.example.gogaga.apiinterface;

import com.example.gogaga.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PostApiInterface {

    // https://jsonplaceholder.typicode.com/posts?_limit=10&_start=34
    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("_start") String start,
            @Query("_limit") String limit
    );
}
