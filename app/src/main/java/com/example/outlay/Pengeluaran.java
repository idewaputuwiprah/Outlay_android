package com.example.outlay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.outlay.adapter.AdapterPengeluaran;
import com.example.outlay.controller.DatabaseCtrl;
import com.example.outlay.model.ModelPemasukan;
import com.example.outlay.model.ModelPengeluaran;

import java.util.ArrayList;

public class Pengeluaran extends AppCompatActivity {
    
    RecyclerView recyclerView;
    AdapterPengeluaran adapterPengeluaran;
    DatabaseCtrl databaseCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran);

        databaseCtrl = new DatabaseCtrl(this);
        
        recyclerView = findViewById(R.id.recyclerPengeluaran);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        adapterPengeluaran = new AdapterPengeluaran(this, getList());
        recyclerView.setAdapter(adapterPengeluaran);
    }

    private ArrayList<ModelPengeluaran> getList() {

        ArrayList<ModelPengeluaran> models = new ArrayList<>();
        Cursor res;
        ModelPengeluaran model;

        res = databaseCtrl.getqueryPengeluaran();

        if(res.getCount() != 0){
            while (res.moveToNext()) {
                model = new ModelPengeluaran();
                Toast.makeText(this, databaseCtrl.currencyConv(res.getInt(2)), Toast.LENGTH_SHORT).show();
                model.setNama(res.getString(1));
                model.setNominal(databaseCtrl.currencyConv(res.getInt(2)));
                model.setDate(res.getString(4));
                models.add(model);
            }
        }
        return models;
    }
}
