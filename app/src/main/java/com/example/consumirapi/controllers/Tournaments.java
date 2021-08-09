package com.example.consumirapi.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.consumirapi.R;

public class Tournaments extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tournaments);
        toolbar=(Toolbar)findViewById(R.id.tool_bar);

        setSupportActionBar(toolbar);
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
    public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menu, mimenu);

        return true;
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
}
