package com.example.consumirapi.controllers;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.example.consumirapi.*;
import com.example.consumirapi.interfaces.PostService;
import com.example.consumirapi.models.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText edtPalabra;
    Button btnBuscar;
    TextView txtResultado;
    TextView txtBody;
    ArrayList<String> titles = new ArrayList<>();
    private List<Post> ProductList;
    ListView list;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtPalabra = findViewById(R.id.edtPalabra);
        btnBuscar = findViewById(R.id.btnBuscar);
        txtResultado = findViewById(R.id.txtResultado);
        txtBody = findViewById(R.id.txtBody);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);

        list = findViewById(R.id.list);
        list.setAdapter(arrayAdapter);

        toolbar=(Toolbar)findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtResultado.setText("");
                resultado(edtPalabra.getText().toString());
            }
        });
    }

    public void resultado(String q){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostService postService = retrofit.create(PostService.class);
        Call<List<Post>> call = postService.find(q);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                //Log.i("onResponse", "resultado = holaaaa"  + response.body());
                for(Post post: response.body()) {
                    txtResultado.append(post + "\n");
                }
                //JSONArray jsonArray = new JSONArray(response.body());
                //Log.i("main", "entro");
                //    try {
                //        for(int i = 0; i < jsonArray.length(); i++){
                //            JSONObject jsonObject = jsonArray.getJSONObject(i);
                //            txtResultado.setText(jsonObject.getString("title"));
                //            txtBody.setText(jsonObject.getString("body"));
                //        }
                //    } catch (JSONException e) {
                //       e.printStackTrace();
                //    }


                //arrayAdapter.notifyDataSetChanged();


                //adapter = new NewAdapter(getApplicationContext(), R.layout.news);

                /* List<Post> postList = response.body();
                ArrayList<Post> listaArray = new ArrayList<>();
                for(Post p: postList){
                    Post datosModelo = new Post();

                    String resultado = "";
                    resultado+= "Title: " + p.getTitle() + "\n";
                    resultado+= "Body: " + p.getBody() + "\n\n";

                    datosModelo.getUserId();
                    datosModelo.getId();
                    datosModelo.getTitle();
                    datosModelo.getBody();

                    listaArray.add(datosModelo);


                    txtResultado.append(resultado);

                } */

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                txtResultado.setText(t.getMessage());
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

        System.out.println(id);
        System.out.println(R.id.profile);
        System.out.println(R.id.team);

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

    @Override
    public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menu, mimenu);

        return true;
    }
}