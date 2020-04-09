package com.example.outlay.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outlay.R;

public class HolderHutang extends RecyclerView.ViewHolder {

    public TextView titleText, nominalText, tanggalText;

    public HolderHutang(@NonNull View itemView) {
        super(itemView);

        this.titleText = itemView.findViewById(R.id.title);
        this.nominalText = itemView.findViewById(R.id.nominal);
        this.tanggalText = itemView.findViewById(R.id.tanggal);
    }
}
