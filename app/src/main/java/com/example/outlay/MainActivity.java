package com.example.outlay;

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
        textBalance.setText(databaseCtrl.currencyConv(dbHandler.totalBalance())+",00");
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
        Cursor res = dbHandler.queryPengeluaran();
        if (res.getCount() == 0){
            boolean status = dbHandler.insertKategori("Other");
            boolean status2 = dbHandler.insertPemasukan("Gaji Bulanan", "4-8-2020", 5000000);
            boolean status3 = dbHandler.insertPengeluaran("Makan malam", "4-8-2020", 24000);
            Toast.makeText(this,status3+"", Toast.LENGTH_SHORT).show();
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
