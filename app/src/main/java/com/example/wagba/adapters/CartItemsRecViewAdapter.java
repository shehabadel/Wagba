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

import java.util.ArrayList;

public class CartItemsRecViewAdapter extends RecyclerView.Adapter<CartItemsRecViewAdapter.CartItemsRecViewHolder> {
    Context context;
    ArrayList<DishModel> dishModels;

    public CartItemsRecViewAdapter(Context context, ArrayList<DishModel> dishModels){
        this.context=context;
        this.dishModels=dishModels;
    }
    @NonNull
    @Override
    public CartItemsRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.order_item,parent,false);

        return new CartItemsRecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsRecViewHolder holder, int position) {
        holder.dishPrice.setText(Integer.toString(dishModels.get(position).getDishPrice()));
        holder.dishName.setText(dishModels.get(position).getDishName());
        holder.dishIcon.setImageResource(dishModels.get(position).getDishImage());
    }

    @Override
    public int getItemCount() {
        return dishModels.size();
    }

    public static class CartItemsRecViewHolder extends RecyclerView.ViewHolder{
        TextView dishName,dishPrice;
        ImageView dishIcon;
        public CartItemsRecViewHolder(@NonNull View itemView) {
            super(itemView);
            dishIcon = itemView.findViewById(R.id.order_dish_icon);
            dishName = itemView.findViewById(R.id.order_dish_title);
            dishPrice = itemView.findViewById(R.id.order_dish_price);
        }
    }
}
