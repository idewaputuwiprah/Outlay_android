package com.example.outlay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Calendar;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "finance.db";
    public static final String TABLE_1 = "pengeluaran_table";
    public static final String TABLE_2 = "pemasukan_table";
    public static final String TABLE_3 = "hutang_table";
    public static final String TABLE_4 = "kategori";

    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_4 + " (ID_KATEGORI INTEGER PRIMARY KEY AUTOINCREMENT, NAMA_KATEGORI TEXT)");
        db.execSQL("create table " + TABLE_1 + " (ID_PENGELUARAN INTEGER PRIMARY KEY AUTOINCREMENT, NAMA_PENGELUARAN TEXT, NOMINAL INTEGER, ID_KATEGORI INTEGER NOT NULL, TANGGAL TEXT, FOREIGN KEY (ID_KATEGORI) REFERENCES KATEGORI (ID_KATEGORI))");
        db.execSQL("create table " + TABLE_2 + " (ID_PEMASUKAN INTEGER PRIMARY KEY AUTOINCREMENT, NAMA_PEMASUKAN TEXT, NOMINAL INTEGER, ID_KATEGORI INTEGER NOT NULL, TANGGAL TEXT, FOREIGN KEY (ID_KATEGORI) REFERENCES KATEGORI (ID_KATEGORI))");
        db.execSQL("create table " + TABLE_3 + " (ID_HUTANG INTEGER PRIMARY KEY AUTOINCREMENT, NAMA_HUTANG TEXT, NOMINAL INTEGER, ID_KATEGORI INTEGER NOT NULL, FOREIGN KEY (ID_KATEGORI) REFERENCES KATEGORI (ID_KATEGORI))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_4);
        onCreate(db);
    }

    public Cursor queryPemasukan(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_2,null);
        return res;
    }

    public Cursor queryPengeluaran(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_1, null);
        return res;
    }

    public boolean insertPemasukan(String nama, String tanggal, int nominal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAMA_PEMASUKAN", nama);
        contentValues.put("NOMINAL", nominal);
        contentValues.put("ID_KATEGORI", 1);
        contentValues.put("TANGGAL", tanggal);
        long result = db.insert(TABLE_2, null, contentValues);

        if(result == -1) return false;
        else return true;
    }

    public boolean insertPengeluaran(String nama, String tanggal, int nominal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAMA_PENGELUARAN", nama);
        contentValues.put("NOMINAL", nominal);
        contentValues.put("ID_KATEGORI", 1);
        contentValues.put("TANGGAL", tanggal);
        long result = db.insert(TABLE_1, null, contentValues);

        if(result == -1) return false;
        else return true;
    }

    public boolean insertKategori(String nama){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAMA_KATEGORI", nama);
        long result = db.insert(TABLE_4, null, contentValues);

        if(result == -1) return false;
        else return true;
    }

    public int totalBalance(){
        int res = 0, balance = 0, expense = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor b = db.rawQuery("select SUM(NOMINAL) from " + TABLE_2, null);
        Cursor e = db.rawQuery("select SUM(NOMINAL) from " + TABLE_1, null);

        if (b.moveToFirst()) balance = b.getInt(0);
        else balance = -1;

        if(e.moveToFirst()) expense = e.getInt(0);
        else expense = -1;

        res = balance - expense;
        return res;
    }
}
