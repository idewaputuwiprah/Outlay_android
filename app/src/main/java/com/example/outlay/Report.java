package com.example.outlay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Report extends AppCompatActivity {

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        dbHandler = new DBHandler(this);

        int balance = dbHandler.totalBalance();
        Toast.makeText(this, balance+"", Toast.LENGTH_SHORT).show();
    }
}
