package com.example.linguine.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.linguine.Activities.AdminMap;
import com.example.linguine.Models.OrderModel;
import com.example.linguine.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Admin_Adapter extends RecyclerView.Adapter<Admin_Adapter.MyHolder> {
    ArrayList<OrderModel> mOrders;
    Context mContext;

    public Admin_Adapter(ArrayList<OrderModel> mOrders, Context mContext) {
        this.mOrders = mOrders;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_shap , parent , false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.mOrder.setText( "The Order"+ "  :" +mOrders.get(position).getmOrder());
        holder.mPhone.setText( "Client Phone"+ " : " +mOrders.get(position).getmPhone());
        holder.mAddress.setText( "Client Address"+ " : " +mOrders.get(position).getmAddress());
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference("Orders").child(mOrders.get(position).getmPhone());
                databaseReference.removeValue();
                Toast.makeText(mContext, "deleted", Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext , AdminMap.class);
                mIntent.putExtra("mPhone",mOrders.get(position).getmPhone());
                mContext.startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {

        TextView mPhone , mAddress , mOrder ;
        ImageView mImageView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mAddress = itemView.findViewById(R.id.mAddress);
            mPhone = itemView.findViewById(R.id.mPhone);
            mOrder = itemView.findViewById(R.id.mOrder);
            mImageView = itemView.findViewById(R.id.mDelete);
        }
    }
}
