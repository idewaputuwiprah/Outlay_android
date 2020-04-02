package com.example.outlay;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        db.execSQL("create table " + TABLE_1 + " (ID_PENGELUARAN INTEGER PRIMARY KEY AUTOINCREMENT, NAMA_PENGELUARAN TEXT, NOMINAL TEXT, ID_KATEGORI INTEGER NOT NULL, TANGGAL TEXT, FOREIGN KEY (ID_KATEGORI) REFERENCES KATEGORI (ID_KATEGORI))");
        db.execSQL("create table " + TABLE_2 + " (ID_PEMASUKAN INTEGER PRIMARY KEY AUTOINCREMENT, NAMA_PEMASUKAN TEXT, NOMINAL TEXT, ID_KATEGORI INTEGER NOT NULL, TANGGAL TEXT, FOREIGN KEY (ID_KATEGORI) REFERENCES KATEGORI (ID_KATEGORI))");
        db.execSQL("create table " + TABLE_3 + " (ID_HUTANG INTEGER PRIMARY KEY AUTOINCREMENT, NAMA_HUTANG TEXT, NOMINAL TEXT, ID_KATEGORI INTEGER NOT NULL, FOREIGN KEY (ID_KATEGORI) REFERENCES KATEGORI (ID_KATEGORI))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_4);
        onCreate(db);
    }
}