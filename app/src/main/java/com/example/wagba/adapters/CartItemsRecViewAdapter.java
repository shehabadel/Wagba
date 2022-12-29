package com.example.wagba.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagba.R;
import com.example.wagba.ViewModels.CartViewModel;
import com.example.wagba.models.DishModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartItemsRecViewAdapter extends RecyclerView.Adapter<CartItemsRecViewAdapter.CartItemsRecViewHolder> {
    Context context;
    ArrayList<DishModel> dishModels;
    CartViewModel cartViewModel;

    public CartItemsRecViewAdapter(Context context, ArrayList<DishModel> dishModels){
        this.context=context;
        this.dishModels=dishModels;
        cartViewModel = new ViewModelProvider((FragmentActivity) context).get(CartViewModel.class);
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
        Picasso.get().load(dishModels.get(position).getDishImage()).into(holder.dishIcon);
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Remove that current item from the cart.
                 * */
                cartViewModel.removeFromCart(dishModels.get(holder.getAdapterPosition()).getDishID());
                Toast.makeText(context.getApplicationContext(), "Item removed from cart",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return dishModels.size();
    }

    public static class CartItemsRecViewHolder extends RecyclerView.ViewHolder{
        TextView dishName,dishPrice;
        ImageView dishIcon;
        Button removeBtn;
        public CartItemsRecViewHolder(@NonNull View itemView) {
            super(itemView);
            dishIcon = itemView.findViewById(R.id.order_dish_icon);
            dishName = itemView.findViewById(R.id.order_dish_title);
            dishPrice = itemView.findViewById(R.id.order_dish_price);
            removeBtn = itemView.findViewById(R.id.order_remove_dish_btn);
        }
    }
}
