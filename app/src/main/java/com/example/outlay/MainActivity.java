package com.example.outlay;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.outlay.controller.DatabaseCtrl;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView pengeluaran, pemasukan, laporan, hutang;
    TextView textBalance;
    DBHandler dbHandler;
    DatabaseCtrl databaseCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(this);
        databaseCtrl = new DatabaseCtrl(this);

        bindCard();
        checkDatabase();

        textBalance = findViewById(R.id.balance);
        textBalance.setText(databaseCtrl.currencyConv(databaseCtrl.totalBalance())+",00");
    }

    private void bindCard(){
        pengeluaran = findViewById(R.id.card_pengeluaran);
        pemasukan = findViewById(R.id.card_pemasukan);
        laporan = findViewById(R.id.card_laporan);
        hutang = findViewById(R.id.card_hutang);

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
