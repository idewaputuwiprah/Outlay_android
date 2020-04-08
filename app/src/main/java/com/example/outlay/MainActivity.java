package com.example.outlay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView pengeluaran, pemasukan, laporan, hutang;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(this);

        bindCard();
        checkDatabase();
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
        Cursor res = dbHandler.queryPemasukan();
        if (res.getCount() == 0){
            boolean status = dbHandler.insertPemasukan("Gaji Bulanan", "4-8-2020", "5000000");
            Toast.makeText(this,status+"", Toast.LENGTH_SHORT).show();
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
