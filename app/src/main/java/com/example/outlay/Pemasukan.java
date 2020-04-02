package com.example.outlay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.outlay.adapter.AdapterPemasukan;
import com.example.outlay.model.ModelPemasukan;

import java.util.ArrayList;

public class Pemasukan extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterPemasukan adapterPemasukan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasukan);

        recyclerView = findViewById(R.id.recyclerPemasukan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterPemasukan = new AdapterPemasukan(this, getList());
    }

    private ArrayList<ModelPemasukan> getList() {

        ArrayList<ModelPemasukan> models = new ArrayList<>();

//        query from database

//        loop until cursor is empty
        ModelPemasukan model = new ModelPemasukan();
//        set content pada model

        return models;
    }
}
