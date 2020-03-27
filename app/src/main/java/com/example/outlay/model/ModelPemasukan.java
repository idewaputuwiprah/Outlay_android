package com.example.outlay.model;

import java.util.Date;

public class ModelPemasukan {
    String nominal, nama;
    Date date;

    public ModelPemasukan(){

    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
