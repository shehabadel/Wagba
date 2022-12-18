package com.example.wagba.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.models.DishModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrackingItemsRecViewAdapter extends RecyclerView.Adapter<TrackingItemsRecViewAdapter.TrackingItemsRecViewHolder> {
    Context context;
    ArrayList<DishModel> dishModels;

    public TrackingItemsRecViewAdapter(Context context, ArrayList<DishModel> dishModels){
        this.context=context;
        this.dishModels=dishModels;
    }
    @NonNull
    @Override
    public TrackingItemsRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.order_item,parent,false);

        return new TrackingItemsRecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackingItemsRecViewHolder holder, int position) {
        holder.dishPrice.setText(Integer.toString(dishModels.get(position).getDishPrice()));
        holder.dishName.setText(dishModels.get(position).getDishName());
        Picasso.get().load(dishModels.get(position).getDishImage()).into(holder.dishIcon);
    }

    @Override
    public int getItemCount() {
        return dishModels.size();
    }

    public static class TrackingItemsRecViewHolder extends RecyclerView.ViewHolder{
        TextView dishName,dishPrice;
        ImageView dishIcon;
        public TrackingItemsRecViewHolder(@NonNull View itemView) {
            super(itemView);
            dishIcon = itemView.findViewById(R.id.order_dish_icon);
            dishName = itemView.findViewById(R.id.order_dish_title);
            dishPrice = itemView.findViewById(R.id.order_dish_price);
        }
    }
}
