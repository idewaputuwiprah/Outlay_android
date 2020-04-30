package com.example.outlay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.outlay.adapter.AdapterPengeluaran;
import com.example.outlay.controller.DatabaseCtrl;
import com.example.outlay.model.ModelPemasukan;
import com.example.outlay.model.ModelPengeluaran;

import java.util.ArrayList;

public class Pengeluaran extends AppCompatActivity {

    private static final int PENGELUARAN_REQUEST = 1;
    
    RecyclerView recyclerView;
    AdapterPengeluaran adapterPengeluaran;
    Button back, add;
    DatabaseCtrl databaseCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pengeluaran);

        databaseCtrl = new DatabaseCtrl(this);
        back = findViewById(R.id.back_btn_pemasukan);
        add = findViewById(R.id.add_pemasukan);
        
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
                model.setNama(res.getString(1));
                model.setNominal(databaseCtrl.currencyConv(res.getInt(2)));
                model.setDate(res.getString(4));
                models.add(model);
            }
        }
        return models;
    }

    public void onAddPengeluaran(View view){
        Intent intent = new Intent(this, FormPengeluaran.class);
        startActivityForResult(intent, PENGELUARAN_REQUEST);
    }

    public void onBackPengeluaran(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PENGELUARAN_REQUEST){
            if(resultCode == RESULT_OK){
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        }
    }
}
