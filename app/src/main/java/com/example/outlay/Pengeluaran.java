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

import com.example.outlay.adapter.AdapterPengeluaran;
import com.example.outlay.controller.DatabaseCtrl;
import com.example.outlay.model.ModelPengeluaran;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Pengeluaran extends AppCompatActivity {

    private static final int PENGELUARAN_REQUEST = 1;
    
    RecyclerView recyclerView;
    AdapterPengeluaran adapterPengeluaran;
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
//        setContentView(R.layout.activity_pengeluaran);

        databaseCtrl = new DatabaseCtrl(this);
        Cursor res = databaseCtrl.getqueryPengeluaran();
        if(res.getCount() == 0){
            setContentView(R.layout.blank_page);
            blankStatePengeluaran(this);
        }
        else {
            setContentView(R.layout.activity_pengeluaran);
            back = findViewById(R.id.back_btn_pemasukan);
            add = findViewById(R.id.add_pemasukan);

            start = findViewById(R.id.startDatePengeluaran);
            finish = findViewById(R.id.finishDatePengeluaran);

            recyclerView = findViewById(R.id.recyclerPengeluaran);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapterPengeluaran = new AdapterPengeluaran(this, getList());
            recyclerView.setAdapter(adapterPengeluaran);

            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(Pengeluaran.this, dateMulai, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(Pengeluaran.this, dateSelesai, myCalendarFinish.get(Calendar.YEAR), myCalendarFinish.get(Calendar.MONTH), myCalendarFinish.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }
    }

    private void blankStatePengeluaran(Context context) {
        ImageView img = findViewById(R.id.imageBlank);
        TextView txt = findViewById(R.id.text_blank);
        Button add_blank = findViewById(R.id.submit_blank),
                back_blank = findViewById(R.id.back_blank);

        img.setImageResource(R.drawable.expenses_color);
        txt.setText("Kamu belum memiliki pengeluaran");
        add_blank.setText("Tambahkan Pengeluaran");
        add_blank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FormPengeluaran.class);
                startActivityForResult(intent, PENGELUARAN_REQUEST);
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

    private ArrayList<ModelPengeluaran> getList() {

        ArrayList<ModelPengeluaran> models = new ArrayList<>();
        Cursor res;
        ModelPengeluaran model;

        Intent intent = getIntent();
        if (intent.getExtras() == null){

        }
        else {
            filter = intent.getStringExtra("ISFILTER");
            startDate = intent.getStringExtra("start");
            finishDate = intent.getStringExtra("finish");
        }

//        res = databaseCtrl.getqueryPengeluaran();
        if (filter.equals("yes")) {
            res = databaseCtrl.getqueryPengeluaranFilter(startDate, finishDate);
        }
        else res = databaseCtrl.getqueryPengeluaran();

        if(res.getCount() != 0){
            while (res.moveToNext()) {
                model = new ModelPengeluaran();
                model.setNama(res.getString(1));
                model.setNominal(databaseCtrl.currencyConv(res.getInt(2)));
                String hari = databaseCtrl.getHari(res.getString(4));
                model.setHari(hari + ", ");
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

    public void onFilterPengeluaran(View view){
        String dateStart = start.getText().toString().trim();
        String dateFinish = finish.getText().toString().trim();
        Intent refresh = new Intent(this, Pengeluaran.class);
        refresh.putExtra("ISFILTER", "yes");
        refresh.putExtra("start", dateStart);
        refresh.putExtra("finish", dateFinish);
        startActivity(refresh);
        overridePendingTransition(0, 0);
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
