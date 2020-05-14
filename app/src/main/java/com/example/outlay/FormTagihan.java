package com.example.outlay;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.outlay.controller.DatabaseCtrl;

public class FormTagihan extends AppCompatActivity {

    Spinner spinner;
    EditText nama, nominal, hari, bulan, tahun;
    ImageView imageView;
    DatabaseCtrl databaseCtrl;
    Button back, submit;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.form);

        bindVarble();

        databaseCtrl = new DatabaseCtrl(this);
        context = this;

        nama.setHint("nama tagihan/hutang");
        imageView.setImageResource(R.drawable.debt_color);

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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = databaseCtrl.getDate(hari.getText().toString().trim(), bulan.getText().toString().trim(), tahun.getText().toString().trim());
                int uang = Integer.parseInt(nominal.getText().toString().trim());

                boolean status = databaseCtrl.insertTagihanCtrl(nama.getText().toString().trim(), date, uang, spinner.getSelectedItem().toString().trim());
//                Toast.makeText(FormTagihan.this, status+"", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Tagihan.class);
//                setResult(RESULT_OK, intent);
//                finish();
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormTagihan.super.onBackPressed();
            }
        });
    }

    private void bindVarble(){
        nama = findViewById(R.id.nama);
        nominal = findViewById(R.id.jumlah_uang);
        hari = findViewById(R.id.hari);
        bulan = findViewById(R.id.bulan);
        tahun = findViewById(R.id.tahun);
        submit = findViewById(R.id.submit_form);
        back = findViewById(R.id.back_form);
        imageView = findViewById(R.id.imageForm);
        spinner = findViewById(R.id.spinnerKategori);
    }
}
