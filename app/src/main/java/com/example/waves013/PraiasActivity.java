package com.example.waves013;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class PraiasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    DatabaseHelper databaseHelper;

    ArrayList<Pico> picosList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praias);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        databaseHelper = new DatabaseHelper(this);
        picosList = databaseHelper.getData();

        recyclerView = findViewById(R.id.rv_lista);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new PicosAdapter(picosList, PraiasActivity.this);
        recyclerView.setAdapter(recyclerAdapter);

    }

    public void voltarHome(View view){
        finish();
    }

}