package com.example.outlay.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outlay.R;
import com.example.outlay.holder.EventClickListener;
import com.example.outlay.holder.HolderHutang;
import com.example.outlay.model.ModelHutang;

import java.util.ArrayList;

public class AdapterHutang extends RecyclerView.Adapter<HolderHutang> {

    Context context;
    ArrayList<ModelHutang> models;

    public AdapterHutang(Context context, ArrayList<ModelHutang> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public HolderHutang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);

        return new HolderHutang(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderHutang holder, int position) {

        holder.titleText.setText(models.get(position).getNama());
        holder.nominalText.setText(models.get(position).getNominal());
        holder.hariText.setText(models.get(position).getHari());
        holder.tanggalText.setText(models.get(position).getDate());
        holder.setEventClickListener(new EventClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                String nominal = models.get(position).getNominal();
                Intent intent = new Intent("custom-message");
                intent.putExtra("ID", models.get(position).getId());
                intent.putExtra("NOMINAL", nominal);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
