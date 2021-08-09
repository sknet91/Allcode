package com.example.consumirapi.instance;

import com.example.consumirapi.interfaces.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static String API_BASE_URL = "http://3.138.216.127:3900/api/";
    private static Retrofit retrofit;
    private static Gson gson;

    public static Retrofit getRetrofit(){
        if (retrofit == null) {

            gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            retrofit.create(UserService.class);

        }

        return retrofit;

    }

}
