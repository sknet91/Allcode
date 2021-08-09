package com.example.consumirapi.interfaces;

import com.example.consumirapi.models.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface PostService {

    @GET("posts")
    Call<List<Post>> find(@Query("q") String q);

}
