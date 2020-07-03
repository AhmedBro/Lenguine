package com.example.linguine.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.linguine.Models.CategoryModel;

import com.example.linguine.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.AdapterVertical> {
    ArrayList<CategoryModel> mItem;
    Context mContext;
    Click mClick;

    public Category_Adapter(ArrayList<CategoryModel> mItem, Context mContext, Click mClick) {
        this.mItem = mItem;
        this.mContext = mContext;
        this.mClick = mClick;
    }

    @NonNull
    @Override
    public Category_Adapter.AdapterVertical onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_shap, parent, false);
        Category_Adapter.AdapterVertical adapter = new Category_Adapter.AdapterVertical(view);
        return adapter;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Category_Adapter.AdapterVertical holder, final int position) {
        holder.mName.setText(mItem.get(position).getmName());
        Glide.with(mContext).load(mItem.get(position).getmImage()).into(holder.mImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItem.get(position).getmName().equalsIgnoreCase("Mex Crepe"))
                {
                    mClick.select("Mex Crepe");
                }
                else if (mItem.get(position).getmName().equalsIgnoreCase("Crepe"))
                {
                    mClick.select("Crepe");

                }
                else if (mItem.get(position).getmName().equalsIgnoreCase("Super Meals"))
                {
                    mClick.select("Super Meals");
                }
                else if (mItem.get(position).getmName().equalsIgnoreCase("Mex Meals"))
                {
                    mClick.select("Mex Meals");
                }
                else if (mItem.get(position).getmName().equalsIgnoreCase("White Sauce Noodles"))
                {
                    mClick.select("White Sauce Noodles");
                }
                else if (mItem.get(position).getmName().equalsIgnoreCase("Red Sauce Noodles"))
                {
                    mClick.select("Red Sauce Noodles");
                }
                else if (mItem.get(position).getmName().equalsIgnoreCase("Side Items"))
                {
                    mClick.select("Side Items");
                }
                else if (mItem.get(position).getmName().equalsIgnoreCase("Chicken Sandwiches"))
                {
                    mClick.select("Chicken Sandwiches");
                }
                    else if (mItem.get(position).getmName().equalsIgnoreCase("Meet Sandwiches"))
                {
                    mClick.select("Meet Sandwiches");
                }
                else if (mItem.get(position).getmName().equalsIgnoreCase("Basmati Rice"))
                {
                    mClick.select("Basmati Rice");
                }
                else if (mItem.get(position).getmName().equalsIgnoreCase("Syrian Side"))
                {
                    mClick.select("Syrian Side");
                }
                else if (mItem.get(position).getmName().equalsIgnoreCase("Sweet Crepe"))
                {
                    mClick.select("Sweet Crepe");
                }
                else if (mItem.get(position).getmName().equalsIgnoreCase("Balady Side"))
                {
                    mClick.select("Balady Side");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (mItem == null) ? 0 : mItem.size();
    }

    class AdapterVertical extends RecyclerView.ViewHolder {

        TextView mName;
        CircleImageView mImage;

        public AdapterVertical(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.mCatname);
            mImage = itemView.findViewById(R.id.mCatImage);

        }
    }

    public interface  Click {
        void select(String mName);

    }
}
