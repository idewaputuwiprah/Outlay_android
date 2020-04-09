package com.example.outlay;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.outlay.controller.DatabaseCtrl;

public class FormTagihan extends AppCompatActivity {

    Spinner spinner;
    EditText nama;
    ImageView imageView;
    DatabaseCtrl databaseCtrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);

        databaseCtrl = new DatabaseCtrl(this);

        nama = findViewById(R.id.nama);
        nama.setHint("nama tagihan/hutang");

        imageView = findViewById(R.id.imageForm);
        imageView.setImageResource(R.drawable.debt_color);

        spinner = findViewById(R.id.spinnerKategori);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.my_spinner_text){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) return false;
                else return true;
            }
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                if (position == 0){
                    tv.setBackgroundColor(Color.BLACK);
                    tv.setTextColor(Color.WHITE);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Cursor res = databaseCtrl.getqueryKategori();
        adapter.add("Kategori");
        if (res.moveToFirst()){
            do {
                adapter.add(res.getString(1));
            }while (res.moveToNext());
        }

        spinner.setAdapter(adapter);
    }
}
