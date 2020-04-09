package com.example.outlay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.outlay.adapter.AdapterHutang;
import com.example.outlay.controller.DatabaseCtrl;
import com.example.outlay.model.ModelHutang;

import java.util.ArrayList;

public class Tagihan extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterHutang adapterHutang;
    Button back, add;
    DatabaseCtrl databaseCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan);

        databaseCtrl = new DatabaseCtrl(this);
        back = findViewById(R.id.back_btn_pemasukan);
        add = findViewById(R.id.add_pemasukan);

        recyclerView = findViewById(R.id.recyclerTagihan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterHutang = new AdapterHutang(this, getList());
        recyclerView.setAdapter(adapterHutang);
    }

    private ArrayList<ModelHutang> getList() {
        ArrayList<ModelHutang> models = new ArrayList<>();
        Cursor res;
        ModelHutang model;

        res = databaseCtrl.getqueryHutang();

        if(res.getCount() != 0){
            while (res.moveToNext()) {
                model = new ModelHutang();
                Toast.makeText(this, databaseCtrl.currencyConv(res.getInt(2)), Toast.LENGTH_SHORT).show();
                model.setNama(res.getString(1));
                model.setNominal(databaseCtrl.currencyConv(res.getInt(2)));
                model.setDate(res.getString(4));
                models.add(model);
            }
        }
        return models;
    }

    public void onAddTagihan(View view){
        Intent intent = new Intent(this, FormTagihan.class);
        startActivity(intent);
    }

    public void onBackTagihan(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
