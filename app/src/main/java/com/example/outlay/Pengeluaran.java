package com.example.outlay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.outlay.adapter.AdapterPengeluaran;
import com.example.outlay.model.ModelPengeluaran;

import java.util.ArrayList;

public class Pengeluaran extends AppCompatActivity {
    
    RecyclerView recyclerView;
    AdapterPengeluaran adapterPengeluaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran);
        
        recyclerView = findViewById(R.id.recyclerPengeluaran);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        adapterPengeluaran = new AdapterPengeluaran(this, getList());
    }

    private ArrayList<ModelPengeluaran> getList() {
        ArrayList<ModelPengeluaran> models = new ArrayList<>();

//        get data from database

//        looping selama cursor masih ada
        ModelPengeluaran model = new ModelPengeluaran();
//        set item pada model

        return models;
    }
}
