package com.example.outlay.controller;

import android.content.Context;
import android.database.Cursor;

import com.example.outlay.DBHandler;

public class DatabaseCtrl {

    DBHandler dbHandler;

    public DatabaseCtrl(Context context){
        dbHandler = new DBHandler(context);
    }

    public Cursor getqueryPengeluaran(){
        Cursor res = dbHandler.queryPengeluaran();
        return res;
    }

    public String currencyConv(int angka) {
        String nominal = angka+"";
        String number="";
        int temp = 0;

        for (int i=nominal.length()-1; i>=0; i--){
            number = number + nominal.charAt(i);
            temp++;
            if (temp==3){
                number = number + ".";
                temp = 0;
            }
        }
        String res = new StringBuffer(number).reverse().toString();
        res = "Rp " + res;
        return res;
    }

}
