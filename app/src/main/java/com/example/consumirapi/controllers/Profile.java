package com.example.consumirapi.controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.consumirapi.R;
import com.example.consumirapi.interfaces.UserService;
import com.example.consumirapi.models.LoginModel;
import com.example.consumirapi.models.Post;
import com.example.consumirapi.models.UserModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;

public class Profile extends AppCompatActivity {

    private Toolbar toolbar;
    Button btnUpdate;

    private EditText txtpuser, txtpemail, txtpphone, txtpname;
    private String username, email, phone, name;
    TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        toolbar=(Toolbar)findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        txtResultado = findViewById(R.id.txtResultado);

        getDataUser();

        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = txtpuser.getText().toString();
                email = txtpemail.getText().toString();
                name = txtpname.getText().toString();
                phone = txtpphone.getText().toString();

                startUpdate(username, email, name, phone);
            }
        });

    }

    public void startUpdate(String username, String email, String name, String phone){
        View focusView = null;
        boolean cancel = false;

        txtpuser.setError(null);
        txtpname.setError(null);
        txtpemail.setError(null);
        txtpphone.setError(null);

        if (TextUtils.isEmpty(username)){
            txtpuser.setError(getString(R.string.error_field_required));
            focusView = txtpuser;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)){
            txtpemail.setError(getString(R.string.error_field_required));
            focusView = txtpemail;
            cancel = true;
        }

        if (TextUtils.isEmpty(name)){
            txtpname.setError(getString(R.string.error_field_required));
            focusView = txtpname;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone)){
            txtpphone.setError(getString(R.string.error_field_required));
            focusView = txtpphone;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }else{

            doUpdate(username, email, name, phone);
        }
    }

    public void doUpdate(String username, String email, String name, String phone){

        String Userid = getData("userId");
        String token = getData("token");

        String finalToken = "Bearer " + token;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.138.216.127:3900/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);

        UserModel model = new UserModel(username, email, name, phone);

        Call<UserModel> call = userService.editarUsuario(Userid, model, finalToken);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(Profile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    txtResultado.setText(response.body().getMessage());
                }else {
                    Toast.makeText(Profile.this, "Ocurrio un Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    public void ejecutar_profile(View view){
        Intent i = new Intent(this, Profile.class);

        startActivity(i);
    }

    public void ejecutar_team(View view){
        Intent i = new Intent(this, Team.class);

        startActivity(i);
    }

    public void ejecutar_rankings(View view){
        Intent i = new Intent(this, Rankings.class);

        startActivity(i);
    }

    public void ejecutar_tournaments(View view){
        Intent i = new Intent(this, Tournaments.class);

        startActivity(i);
    }

    public void ejecutar_inicio(View view){
        Intent i = new Intent(this, MainActivity.class);

        startActivity(i);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem opcion_menu){
        int id = opcion_menu.getItemId();

        if(id==R.id.profile){
            ejecutar_profile(null);
            return true;
        }

        if(id==R.id.team){
            ejecutar_team(null);
            return true;
        }

        if(id==R.id.rankings){
            ejecutar_rankings(null);
            return true;
        }

        if(id==R.id.tournaments){
            ejecutar_tournaments(null);
            return true;
        }

        if(id==R.id.activity_main){
            ejecutar_inicio(null);
            return true;
        }

        return super.onOptionsItemSelected(opcion_menu);
    }

    private String getData(String key){
        SharedPreferences sharedPreferences = getSharedPreferences("Save", 0);
        if (sharedPreferences.contains(key)){
            String data = sharedPreferences.getString(key, null);
            return data;
        }else{
            return null;
        }
    }

    private String getDataUser(){
        String Userid = getData("userId");
        String token = getData("token");

        String finalToken = "Bearer " + token;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.138.216.127:3900/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);
        Call<JsonObject> call = userService.getUser(Userid, finalToken);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JsonObject dataUser = response.body().getAsJsonObject("data");
                String username = dataUser.get("username").toString();
                String email = dataUser.get("email").toString();
                String name = dataUser.get("name").toString();
                String phone = dataUser.get("phone").toString();

                txtpuser = findViewById(R.id.txtpuser);
                txtpuser.append(username.replaceAll("\"", ""));

                txtpemail = findViewById(R.id.txtpemail);
                txtpemail.append(email.replaceAll("\"", ""));

                txtpname = findViewById(R.id.txtpname);
                txtpname.append(name.replaceAll("\"", ""));

                txtpphone = findViewById(R.id.txtpphone);
                txtpphone.setText(phone.replaceAll("\"", ""));

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menu, mimenu);

        return true;
    }
}
