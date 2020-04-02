package com.example.outlay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outlay.R;
import com.example.outlay.holder.HolderPengeluaran;
import com.example.outlay.model.ModelPengeluaran;

import java.util.ArrayList;

public class AdapterPengeluaran extends RecyclerView.Adapter<HolderPengeluaran> {

    Context context;
    ArrayList<ModelPengeluaran> models;

    public AdapterPengeluaran(Context context, ArrayList<ModelPengeluaran> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public HolderPengeluaran onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);

        return new HolderPengeluaran(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPengeluaran holder, int position) {

//        holder.titleText.setText(models.get(position).getNama());
        holder.titleText.setText(models.get(position).getNama());
        holder.tanggalText.setText(models.get(position).getDate());
        holder.nominalText.setText(models.get(position).getNominal());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
