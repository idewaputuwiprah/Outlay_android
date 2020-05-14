package com.example.outlay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.outlay.controller.DatabaseCtrl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView pengeluaran, pemasukan, laporan, hutang;
    TextView textBalance, nama, status;
    DBHandler dbHandler;
    DatabaseCtrl databaseCtrl;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(this);
        databaseCtrl = new DatabaseCtrl(this);

        bindCard();
        checkDatabase();
        setUser();

        textBalance = findViewById(R.id.balance);
        totalBalance();
    }

    private void totalBalance() {
        int res = databaseCtrl.totalBalance() - sharedPreferences.getInt("hutang", 0);
        textBalance.setText(databaseCtrl.currencyConv(res)+",00");
    }

    private void setUser() {
        Cursor res = databaseCtrl.getUser();
        if (res.getCount() != 0){
            res.moveToFirst();
            nama.setText(res.getString(1));
            status.setText(res.getString(2));
        }
    }

    private void bindCard(){
        pengeluaran = findViewById(R.id.card_pengeluaran);
        pemasukan = findViewById(R.id.card_pemasukan);
        laporan = findViewById(R.id.card_laporan);
        hutang = findViewById(R.id.card_hutang);
        nama = findViewById(R.id.tvUserName);
        status = findViewById(R.id.tvStatus);
        sharedPreferences = getSharedPreferences("com.example.outlay.hutang", Context.MODE_PRIVATE);

        pengeluaran.setOnClickListener(this);
        pemasukan.setOnClickListener(this);
        laporan.setOnClickListener(this);
        hutang.setOnClickListener(this);
    }

    private void checkDatabase(){
        Cursor res = dbHandler.queryKategori();
        if (res.getCount() == 0){
            dbHandler.insertKategori("Other");
            dbHandler.insertKategori("Makan/Minum");
            dbHandler.insertKategori("Hobi");
            dbHandler.insertKategori("Elektronik");
            dbHandler.insertKategori("Pendidikan");
        }
    }

    @Override
    public void onClick(View v) {
        int status = v.getId();

        if (status == R.id.card_pengeluaran){
            Intent intent = new Intent(this, Pengeluaran.class);
            startActivity(intent);
        }
        else if (status == R.id.card_pemasukan){
            Intent intent = new Intent(this, Pemasukan.class);
            startActivity(intent);
        }
        else if (status == R.id.card_laporan){
            Intent intent = new Intent(this, Report.class);
            startActivity(intent);
        }
        else if (status == R.id.card_hutang){
            Intent intent = new Intent(this, Tagihan.class);
            startActivity(intent);
        }
    }
}
