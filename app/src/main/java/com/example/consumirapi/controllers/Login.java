package com.example.consumirapi.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.consumirapi.R;
import com.example.consumirapi.instance.RetrofitClientInstance;
import com.example.consumirapi.interfaces.UserService;
import com.example.consumirapi.models.LoginModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    Button register;
    Button login;
    private EditText txtuser, txtpassword;
    private String user, password;
    UserService loginservice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        txtuser = findViewById(R.id.txtuser);
        txtpassword = findViewById(R.id.txtpassword);
        loginservice = RetrofitClientInstance.getRetrofit().create(UserService.class);

        register = (Button) findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        login = (Button) findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                user = txtuser.getText().toString();
                password = txtpassword.getText().toString();

                startLogin(user, password);
                //startActivity(new Intent(Login.this, MainActivity.class));
            }
        });

    }

    private void startLogin(String username, String password){

        View focusView = null;
        boolean cancel = false;

        txtuser.setError(null);
        txtpassword.setError(null);

        if (TextUtils.isEmpty(username)){
            txtuser.setError(getString(R.string.error_field_required));
            focusView = txtuser;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)){
            txtpassword.setError(getString(R.string.error_field_required));
            focusView = txtpassword;
            cancel = true;
        } else if(!isPasswordValid(password)) {
            txtpassword.setError(getString(R.string.error_invalid_password));
            focusView = txtpassword;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }else{
            doLogin( username, password);
        }

    }

    private void doLogin(final String username, final String password){
        //Log.i("login", username);
        // LoginModel model = new LoginModel(username, password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.138.216.127:3900/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);

        LoginModel model = new LoginModel(username, password);

        Call<LoginModel> call = userService.login(model);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.isSuccessful()) {
                    if (response.body().getType() == "true"){
                        saveData(response.body().getUserId(), response.body().getToken());
                        startActivity(new Intent(Login.this, MainActivity.class));
                    }else{
                        Toast.makeText(Login.this, "The user or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Ocurrio un Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        /* model.enqueue(new Callback<LoginModel> {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()) {
                    System.out.println(response.body());
                    if (response.body().equals("true")){
                        startActivity(new Intent(Login.this, MainActivity.class));
                    }else{
                        Toast.makeText(Login.this, "The user or password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Login.this, "Error intenta de nuevo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void saveData(String userId, String token){
        SharedPreferences myPreferences = getSharedPreferences("Save", 0);
        SharedPreferences.Editor editor = myPreferences.edit();

        editor.putString("userId", userId);
        editor.putString("token", token);
        editor.apply();
    }

    private boolean isPasswordValid(String password){
        return password.length() >= 1;
    }

}
