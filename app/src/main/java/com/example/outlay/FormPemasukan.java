package com.example.outlay;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.outlay.controller.DatabaseCtrl;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FormPemasukan extends AppCompatActivity {

    Spinner spinner;
    EditText nama, nominal, tanggal;
    ImageView imageView;
    DatabaseCtrl databaseCtrl;
    Button back, submit;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        tanggal.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.form);

        databaseCtrl = new DatabaseCtrl(this);

        bindVariable();

        nama.setHint("nama pemasukan");
        imageView.setImageResource(R.drawable.income_color);
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FormPemasukan.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
//                String date = databaseCtrl.getDate(hari.getText().toString().trim(), bulan.getText().toString().trim(), tahun.getText().toString().trim());
                String date = tanggal.getText().toString().trim();
                int uang = Integer.parseInt(nominal.getText().toString().trim());
                boolean status = databaseCtrl.insertPemasukanCtrl(nama.getText().toString().trim(), date, uang, spinner.getSelectedItem().toString().trim());

                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormPemasukan.super.onBackPressed();
            }
        });
    }

    private void bindVariable(){
        nama = findViewById(R.id.nama);
        nominal = findViewById(R.id.jumlah_uang);
        tanggal = findViewById(R.id.tanggal);
        submit = findViewById(R.id.submit_form);
        back = findViewById(R.id.back_form);
        imageView = findViewById(R.id.imageForm);
        spinner = findViewById(R.id.spinnerKategori);
    }
}
