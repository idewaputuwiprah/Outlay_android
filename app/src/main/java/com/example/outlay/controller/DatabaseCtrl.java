package com.example.outlay.controller;

import android.content.Context;
import android.database.Cursor;

import com.example.outlay.DBHandler;

public class DatabaseCtrl {

    DBHandler dbHandler;

    public DatabaseCtrl(Context context){
        dbHandler = new DBHandler(context);
    }

    public String getDate(String hari, String bulan, String tahun){
        String res = "";
        res = res + tahun + "-";

        if (bulan.length() == 1) res = res + "0" + bulan + "-";
        else res = res + bulan + "-";

        if (hari.length()==1) res = res + "0" + hari;
        else res = res + hari;

        return res;
    }

    private int getIdKategori(String kategori){

        int res = dbHandler.queryIdKategori(kategori);
        return res;

    }

    public Cursor getqueryPengeluaran(){
        Cursor res = dbHandler.queryPengeluaran();
        return res;
    }

    public Cursor getqueryPemasukan(){
        Cursor res = dbHandler.queryPemasukan();
        return res;
    }

    public Cursor getqueryHutang(){
        Cursor res = dbHandler.queryHutang();
        return res;
    }

    public Cursor getqueryKategori(){
        Cursor res = dbHandler.queryKategori();
        return res;
    }

    public String currencyConv(int angka) {
        String nominal = angka+"";
        String number="";
        int temp = 0;

        for (int i=nominal.length()-1; i>=0; i--){
            number = number + nominal.charAt(i);
            temp++;
            if (temp==3 && i>0){
                number = number + ".";
                temp = 0;
            }
        }
        String res = new StringBuffer(number).reverse().toString();
        res = "Rp " + res;
        return res;
    }

    public int totalBalance(){
        int res = 0, balance = 0, expense = 0;

        Cursor b = dbHandler.sum(dbHandler.TABLE_2, "NOMINAL");
        Cursor e = dbHandler.sum(dbHandler.TABLE_1, "NOMINAL");

        if (b.moveToFirst()) balance = b.getInt(0);
        else balance = -1;

        if(e.moveToFirst()) expense = e.getInt(0);
        else expense = -1;

        res = balance - expense;
        return res;
    }

    public String totalPemasukan(){
        int res;

        Cursor temp = dbHandler.sum(dbHandler.TABLE_2, "NOMINAL");
        if (temp.moveToFirst()) res = temp.getInt(0);
        else res = -1;

        return currencyConv(res);
    }

    public String totalPengeluaran(){
        int res;

        Cursor temp = dbHandler.sum(dbHandler.TABLE_1, "NOMINAL");
        if (temp.moveToFirst()) res = temp.getInt(0);
        else res = -1;

        return currencyConv(res);
    }

    public String totalHutang(){
        int res;

        Cursor temp = dbHandler.sum(dbHandler.TABLE_3, "NOMINAL");
        if (temp.moveToFirst()) res = temp.getInt(0);
        else res = -1;

        return currencyConv(res);
    }

    public boolean insertPemasukanCtrl(String nama, String tanggal, int nominal, String kategori){
        int id = getIdKategori(kategori);
        boolean status = dbHandler.insertPemasukan(nama, tanggal, nominal, id);
        return status;
    }

    public boolean insertPengeluaranCtrl(String nama, String tanggal, int nominal, String kategori){
        int id = getIdKategori(kategori);
        boolean status = dbHandler.insertPengeluaran(nama, tanggal, nominal, id);
        return status;
    }

    public boolean insertTagihanCtrl(String nama, String tanggal, int nominal, String kategori){
        int id = getIdKategori(kategori);
        boolean status = dbHandler.insertTagihan(nama, tanggal, nominal, id);
        return status;
    }

}
