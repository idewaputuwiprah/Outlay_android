package com.example.outlay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outlay.R;
import com.example.outlay.holder.HolderPemasukan;
import com.example.outlay.model.ModelPemasukan;

import java.util.ArrayList;

public class AdapterPemasukan extends RecyclerView.Adapter<HolderPemasukan> {

    Context context;
    ArrayList<ModelPemasukan> models;

    public AdapterPemasukan(Context context, ArrayList<ModelPemasukan> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public HolderPemasukan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,null);

        return new HolderPemasukan(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPemasukan holder, int position) {

        holder.titleText.setText(models.get(position).getNama());
        holder.tanggalText.setText(models.get(position).getDate());
        holder.nominalText.setText(models.get(position).getNominal());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
