package com.example.consumirapi.interfaces;

import com.example.consumirapi.models.LoginModel;
import com.example.consumirapi.models.UserModel;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UserService {

    @POST("login2")
    Call<LoginModel> login(@Body LoginModel loginModel);

    @POST("registro")
    Call<UserModel> agregarUsuario(
            @Body UserModel userModel
    );

    @GET("user2/{id}")
    Call<JsonObject> getUser(@Path("id") String id, @Header("Authorization") String authHeader);

    @PUT("registro/{id}")
    Call<UserModel> editarUsuario(@Path("id") String id, @Body UserModel userModel, @Header("Authorization") String authHeader);

}
