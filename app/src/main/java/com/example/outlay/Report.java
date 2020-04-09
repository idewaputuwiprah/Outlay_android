package com.example.outlay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.outlay.controller.DatabaseCtrl;

public class Report extends AppCompatActivity {

    DatabaseCtrl databaseCtrl;
    TextView pengeluaran, pemasukan, tagihan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        databaseCtrl = new DatabaseCtrl(this);
        bindTextView();

        pemasukan.setText(databaseCtrl.totalPemasukan()+",00");
        pengeluaran.setText(databaseCtrl.totalPengeluaran()+",00");
        tagihan.setText(databaseCtrl.totalHutang()+",00");
    }

    private void bindTextView() {
        pengeluaran = findViewById(R.id.nominalPengeluaran);
        pemasukan = findViewById(R.id.nominalPemasukan);
        tagihan = findViewById(R.id.nominalTagihan);
    }

    public void onBackReport(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
