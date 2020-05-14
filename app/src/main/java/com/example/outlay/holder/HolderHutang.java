package com.example.outlay.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outlay.R;

public class HolderHutang extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView titleText, nominalText, tanggalText;
    EventClickListener eventClickListener;

    public HolderHutang(@NonNull View itemView) {
        super(itemView);

        this.titleText = itemView.findViewById(R.id.title);
        this.nominalText = itemView.findViewById(R.id.nominal);
        this.tanggalText = itemView.findViewById(R.id.tanggal);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.eventClickListener.onItemClickListener(v, getLayoutPosition());
    }

    public void setEventClickListener(EventClickListener listener){
        this.eventClickListener = listener;
    }
}
