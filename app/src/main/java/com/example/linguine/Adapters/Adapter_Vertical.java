package com.example.linguine.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.linguine.Models.HomeVerticalAdapter;
import com.example.linguine.R;


import java.util.ArrayList;

public class Adapter_Vertical extends RecyclerView.Adapter<Adapter_Vertical.AdapterVertical> {
    ArrayList<HomeVerticalAdapter> mItem;
    Context mContext;

    public Adapter_Vertical(ArrayList<HomeVerticalAdapter> mItem, Context mContext) {
        this.mItem = mItem;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AdapterVertical onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_shap, parent, false);
        Adapter_Vertical.AdapterVertical adapter = new Adapter_Vertical.AdapterVertical(view);
        return adapter;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterVertical holder, final int position) {
        holder.mName.setText("الاسم : " + mItem.get(position).getmName());
        holder.mPrice.setText("كبير: " + mItem.get(position).getmPrice() + "جنيه");
        Glide.with(mContext).load(mItem.get(position).getmImage()).into(holder.mImage);

        if (!mItem.get(position).getmPrice2().equalsIgnoreCase("")) {
            holder.mPrice2.setText("وسط: " + mItem.get(position).getmPrice2() + "جنيه");
        }


    }

    @Override
    public int getItemCount() {
        return (mItem == null) ? 0 : mItem.size();
    }

    class AdapterVertical extends RecyclerView.ViewHolder {

        TextView mName, mPrice, mPrice2;
        ImageView mImage;

        public AdapterVertical(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.itemImage);
            mName = itemView.findViewById(R.id.itemName);
            mPrice = itemView.findViewById(R.id.mPrice);
            mPrice2 = itemView.findViewById(R.id.mPrice2);
        }
    }
}
