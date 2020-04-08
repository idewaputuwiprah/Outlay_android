package com.example.outlay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.outlay.adapter.AdapterPemasukan;
import com.example.outlay.model.ModelPemasukan;

import java.util.ArrayList;

public class Pemasukan extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterPemasukan adapterPemasukan;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasukan);

        dbHandler = new DBHandler(this);

        recyclerView = findViewById(R.id.recyclerPemasukan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterPemasukan = new AdapterPemasukan(this, getList());
        recyclerView.setAdapter(adapterPemasukan);
    }

    private String currencyConv(String nominal) {
        String number="";
        int temp = 0;

        for (int i=nominal.length()-1; i>=0; i--){
            number = number + nominal.charAt(i);
            temp++;
            if (temp==3){
                number = number + ".";
                temp = 0;
            }
        }
        String res = new StringBuffer(number).reverse().toString();
        res = "Rp " + res;
        return res;
    }

    private ArrayList<ModelPemasukan> getList() {

        ArrayList<ModelPemasukan> models = new ArrayList<>();
        Cursor res;
        ModelPemasukan model;

        res = dbHandler.queryPemasukan();

        if(res.getCount() != 0){
            while (res.moveToNext()) {
                model = new ModelPemasukan();
                Toast.makeText(this, currencyConv(res.getString(2)), Toast.LENGTH_SHORT).show();
                model.setNama(res.getString(1));
                model.setNominal(currencyConv(res.getString(2)));
                model.setDate(res.getString(4));
                models.add(model);
            }
        }
        return models;
    }
}
