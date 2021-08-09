package com.example.consumirapi.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.consumirapi.R;
import com.example.consumirapi.interfaces.UserService;
import com.example.consumirapi.models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    Button login;
    Button register;
    private EditText txtuser, txtpassword, txtemail, txtphone, txtname, txtlastname;
    private String username, password, email, phone, name, lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        login = (Button) findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        register = (Button) findViewById(R.id.btnRegister);
        txtuser = findViewById(R.id.txtuser);
        txtpassword = findViewById(R.id.txtpassword);
        txtemail = findViewById(R.id.txtemail);
        txtname = findViewById(R.id.txtname);
        txtlastname = findViewById(R.id.txtlastname);
        txtphone = findViewById(R.id.txtphone);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = txtuser.getText().toString();
                password = txtpassword.getText().toString();
                email = txtemail.getText().toString();
                name = txtname.getText().toString();
                lastname = txtlastname.getText().toString();
                phone = txtphone.getText().toString();

                startRegister(username, password, email, name, lastname, phone);
            }
        });
    }

    public void startRegister(String username, String password, String email, String name, String lastname, String phone){
        View focusView = null;
        boolean cancel = false;

        txtuser.setError(null);
        txtpassword.setError(null);
        txtname.setError(null);
        txtlastname.setError(null);
        txtemail.setError(null);
        txtphone.setError(null);

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

        if (TextUtils.isEmpty(email)){
            txtemail.setError(getString(R.string.error_field_required));
            focusView = txtemail;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)){
            txtname.setError(getString(R.string.error_field_required));
            focusView = txtname;
            cancel = true;
        }

        if (TextUtils.isEmpty(lastname)){
            txtlastname.setError(getString(R.string.error_field_required));
            focusView = txtlastname;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone)){
            txtphone.setError(getString(R.string.error_field_required));
            focusView = txtphone;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }else{

            name = name + " " + lastname;

            doRegister(username, password, email, name, phone);
        }
    }

    public void doRegister(String username, String password, String email, String name, String phone){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.138.216.127:3900/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);

        UserModel model = new UserModel(username, password, email, name, phone);

        Call<UserModel> call = userService.agregarUsuario(model);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()) {
                    if (response.body().getType() == "true"){
                        Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                    }else{
                        Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Register.this, "Ocurrio un Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private boolean isPasswordValid(String password){
        return password.length() >= 1;
    }
}
