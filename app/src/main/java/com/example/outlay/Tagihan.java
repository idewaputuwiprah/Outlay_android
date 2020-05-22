package com.example.outlay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.outlay.adapter.AdapterHutang;
import com.example.outlay.controller.DatabaseCtrl;
import com.example.outlay.model.ModelHutang;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Tagihan extends AppCompatActivity {

    private static final int TAGIHAN_REQUEST = 1;

    RecyclerView recyclerView;
    AdapterHutang adapterHutang;
    Button back, add, pay;
    DatabaseCtrl databaseCtrl;
    SharedPreferences sharedPreferences;
    Dialog confirmDialog;
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

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            confirmDialog = new Dialog(context);
            String nominal = intent.getStringExtra("NOMINAL");
            String id = intent.getStringExtra("ID");
            int temp = databaseCtrl.reConvCurrency(nominal);
            int res = sharedPreferences.getInt("hutang", 0) + temp;
//            Toast.makeText(context, res+"", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            confirmDialog.setContentView(R.layout.popup);
            Button yes, no;
            yes = confirmDialog.findViewById(R.id.accept);
            no = confirmDialog.findViewById(R.id.cancel);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putInt("hutang", res);
                    editor.apply();
                    boolean status = databaseCtrl.removeTagihan(id);
                    Toast.makeText(context, "Berhasil melakukan pembayaran", Toast.LENGTH_SHORT).show();
                    confirmDialog.dismiss();
                    Intent refresh = new Intent(context, Tagihan.class);
                    startActivity(refresh);
                    overridePendingTransition(0, 0);
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDialog.dismiss();
                }
            });

            confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            confirmDialog.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tagihan);
        confirmDialog = new Dialog(this);

        databaseCtrl = new DatabaseCtrl(this);
        Cursor res = databaseCtrl.getqueryHutang();
        if (res.getCount() == 0){
            setContentView(R.layout.blank_page);
            blankStateTagihan(this);
        }
        else {
            setContentView(R.layout.activity_tagihan);
            back = findViewById(R.id.back_btn_pemasukan);
            add = findViewById(R.id.add_pemasukan);
            pay = findViewById(R.id.pay);

            start = findViewById(R.id.startDateHutang);
            finish = findViewById(R.id.finishDateHutang);

            recyclerView = findViewById(R.id.recyclerTagihan);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapterHutang = new AdapterHutang(this, getList());
            recyclerView.setAdapter(adapterHutang);

            sharedPreferences = getSharedPreferences("com.example.outlay.hutang", Context.MODE_PRIVATE);
            LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("custom-message"));

            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(Tagihan.this, dateMulai, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(Tagihan.this, dateSelesai, myCalendarFinish.get(Calendar.YEAR), myCalendarFinish.get(Calendar.MONTH), myCalendarFinish.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }
    }

    private void blankStateTagihan(Context context) {
        ImageView img = findViewById(R.id.imageBlank);
        TextView txt = findViewById(R.id.text_blank);
        Button add_blank = findViewById(R.id.submit_blank),
                back_blank = findViewById(R.id.back_blank);

        img.setImageResource(R.drawable.debt_color);
        txt.setText("Kamu belum memiliki tagihan / hutang");
        add_blank.setText("Tambahkan Tagihan");
        add_blank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FormTagihan.class);
                startActivityForResult(intent, TAGIHAN_REQUEST);
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

    private ArrayList<ModelHutang> getList() {
        ArrayList<ModelHutang> models = new ArrayList<>();
        Cursor res;
        ModelHutang model;

        Intent intent = getIntent();
        if (intent.getExtras() == null){

        }
        else {
            filter = intent.getStringExtra("ISFILTER");
            startDate = intent.getStringExtra("start");
            finishDate = intent.getStringExtra("finish");
        }

        if (filter.equals("yes")) {
            res = databaseCtrl.getqueryHutangFilter(startDate,finishDate);
        }
        else res = databaseCtrl.getqueryHutang();

//        res = databaseCtrl.getqueryHutang();

        if(res.getCount() != 0){
            while (res.moveToNext()) {
                model = new ModelHutang();
                model.setId(res.getInt(0)+"");
                model.setNama(res.getString(1));
                model.setNominal(databaseCtrl.currencyConv(res.getInt(2)));
                model.setIdKategori(res.getInt(3)+"");
                String hari = databaseCtrl.getHari(res.getString(4));
                model.setHari(hari + ", ");
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

    public void onFilterHutang(View view){
        String dateStart = start.getText().toString().trim();
        String dateFinish = finish.getText().toString().trim();
        Intent refresh = new Intent(this, Tagihan.class);
        refresh.putExtra("ISFILTER", "yes");
        refresh.putExtra("start", dateStart);
        refresh.putExtra("finish", dateFinish);
        startActivity(refresh);
        overridePendingTransition(0, 0);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == TAGIHAN_REQUEST){
//            if (resultCode == RESULT_OK){
//                finish();
//                overridePendingTransition(0, 0);
//                startActivity(getIntent());
//                overridePendingTransition(0, 0);
////                LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("custom-message"));
//            }
//        }
//    }
}
