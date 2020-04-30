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

import com.example.outlay.adapter.AdapterPemasukan;
import com.example.outlay.controller.DatabaseCtrl;
import com.example.outlay.model.ModelPemasukan;

import java.util.ArrayList;

public class Pemasukan extends AppCompatActivity {

    private static final int PEMASUKAN_REQUEST = 1;

    RecyclerView recyclerView;
    AdapterPemasukan adapterPemasukan;
    Button back, add;
    DatabaseCtrl databaseCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pemasukan);

        databaseCtrl = new DatabaseCtrl(this);
        back = findViewById(R.id.back_btn_pemasukan);
        add = findViewById(R.id.add_pemasukan);

        recyclerView = findViewById(R.id.recyclerPemasukan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterPemasukan = new AdapterPemasukan(this, getList());
        recyclerView.setAdapter(adapterPemasukan);
    }

    private ArrayList<ModelPemasukan> getList() {

        ArrayList<ModelPemasukan> models = new ArrayList<>();
        Cursor res;
        ModelPemasukan model;

        res = databaseCtrl.getqueryPemasukan();

        if(res.getCount() != 0){
            while (res.moveToNext()) {
                model = new ModelPemasukan();
                model.setNama(res.getString(1));
                model.setNominal(databaseCtrl.currencyConv(res.getInt(2)));
                model.setDate(res.getString(4));
                models.add(model);
            }
        }
        return models;
    }

    public void onAddPemasukan(View view){
        Intent intent = new Intent(this, FormPemasukan.class);
        startActivityForResult(intent, PEMASUKAN_REQUEST);
    }

    public void onBackPemasukan(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PEMASUKAN_REQUEST){
            if(resultCode == RESULT_OK){
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        }
    }
}
