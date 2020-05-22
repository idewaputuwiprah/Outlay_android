package com.example.outlay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.outlay.adapter.AdapterPemasukan;
import com.example.outlay.controller.DatabaseCtrl;
import com.example.outlay.model.ModelPemasukan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Pemasukan extends AppCompatActivity {

    private static final int PEMASUKAN_REQUEST = 1;

    RecyclerView recyclerView;
    AdapterPemasukan adapterPemasukan;
    Button back, add;
    DatabaseCtrl databaseCtrl;
    EditText start, finish;
    String filter = "no", startDate, finishDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarFinish = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateMulai = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, month);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateStart();
        }
    };

    private void updateStart() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        start.setText(sdf.format(myCalendarStart.getTime()));
    }

    DatePickerDialog.OnDateSetListener dateSelesai = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendarFinish.set(Calendar.YEAR, year);
            myCalendarFinish.set(Calendar.MONTH, month);
            myCalendarFinish.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateFinish();
        }
    };

    private void updateFinish() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        finish.setText(sdf.format(myCalendarFinish.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_pemasukan);

        databaseCtrl = new DatabaseCtrl(this);
        Cursor res = databaseCtrl.getqueryPemasukan();

        if(res.getCount() == 0) {
            setContentView(R.layout.blank_page);
            blankStatePemasukan(this);
        }
        else {
            Intent get = getIntent();
//            filter = get.getStringExtra("ISFILTER");
            if (filter.equals("")) filter = "no";
            Toast.makeText(this, filter, Toast.LENGTH_SHORT).show();
//            startDate = get.getStringExtra("start");
//            Toast.makeText(this, startDate, Toast.LENGTH_SHORT).show();
//            finishDate = get.getStringExtra("finish");
//            Toast.makeText(this, finishDate, Toast.LENGTH_SHORT).show();

            setContentView(R.layout.activity_pemasukan);

            back = findViewById(R.id.back_btn_pemasukan);
            add = findViewById(R.id.add_pemasukan);

            start = findViewById(R.id.startDatePemasukan);
            finish = findViewById(R.id.finishDatePemasukan);

            recyclerView = findViewById(R.id.recyclerPemasukan);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapterPemasukan = new AdapterPemasukan(this, getList());
            recyclerView.setAdapter(adapterPemasukan);

            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(Pemasukan.this, dateMulai, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(Pemasukan.this, dateSelesai, myCalendarFinish.get(Calendar.YEAR), myCalendarFinish.get(Calendar.MONTH), myCalendarFinish.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }
    }

    private void blankStatePemasukan(Context context) {
        ImageView img = findViewById(R.id.imageBlank);
        TextView txt = findViewById(R.id.text_blank);
        Button add_blank = findViewById(R.id.submit_blank),
                back_blank = findViewById(R.id.back_blank);

        img.setImageResource(R.drawable.income_color);
        txt.setText("Kamu belum memiliki pemasukkan");
        add_blank.setText("Tambahkan Pemasukkan");
        add_blank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FormPemasukan.class);
                startActivityForResult(intent, PEMASUKAN_REQUEST);
            }
        });
        back_blank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<ModelPemasukan> getList() {

        ArrayList<ModelPemasukan> models = new ArrayList<>();
        Cursor res;
        ModelPemasukan model;

//        if (filter.equals("yes")) {
//            res = databaseCtrl.getqueryPemasukanFilter(startDate, finishDate);
//        }
//        else res = databaseCtrl.getqueryPemasukan();
        res = databaseCtrl.getqueryPemasukan();
        if(res.getCount() != 0){
            while (res.moveToNext()) {
                model = new ModelPemasukan();
                model.setNama(res.getString(1));
                model.setNominal(databaseCtrl.currencyConv(res.getInt(2)));
                String hari = databaseCtrl.getHari(res.getString(4));
                model.setHari(hari+", ");
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

    public void onFilterPemasukan(View view){
        String dateStart = start.getText().toString().trim();
        String dateFinish = finish.getText().toString().trim();
        Intent refresh = new Intent(this, Pemasukan.class);
        refresh.putExtra("ISFILTER", "yes");
        refresh.putExtra("start", dateStart);
        refresh.putExtra("finish", dateFinish);
        startActivity(refresh);
//        overridePendingTransition(0, 0);
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
