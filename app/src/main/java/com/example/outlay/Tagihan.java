package com.example.outlay;

import androidx.annotation.Nullable;
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

    private static final int TAGIHAN_REQUEST = 1;

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
        startActivityForResult(intent, TAGIHAN_REQUEST);
    }

    public void onBackTagihan(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAGIHAN_REQUEST){
            if (resultCode == RESULT_OK){
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        }
    }
}
