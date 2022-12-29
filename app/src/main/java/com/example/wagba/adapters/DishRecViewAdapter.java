package com.example.wagba.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.R;
import com.example.wagba.ViewModels.CartViewModel;
import com.example.wagba.ViewModels.UserViewModel;
import com.example.wagba.models.DishModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DishRecViewAdapter extends RecyclerView.Adapter<DishRecViewAdapter.DishRecViewHolder> {
    Context context;
    ArrayList<DishModel> dishModels;
    CartViewModel cartViewModel;

    public DishRecViewAdapter(Context context, ArrayList<DishModel> dishModels){
        this.context=context;
        this.dishModels=dishModels;
        cartViewModel = new ViewModelProvider((FragmentActivity) context).get(CartViewModel.class);
        /**
         * Creating a Firebase Authentication instance to reference to
         * the userRef in the database and aid in pushing in the cart.
        * */
    }
    @NonNull
    @Override
    public DishRecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dish_item,parent,false);
        return new DishRecViewAdapter.DishRecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishRecViewHolder holder, int position) {
        holder.dishTitle.setText(dishModels.get(position).getDishName());
        holder.dishPrice.setText(Integer.toString(dishModels.get(position).getDishPrice()));
        Picasso.get().load(dishModels.get(position).getDishImage()).into(holder.dishIcon);
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Add the item to the cart
                 * */
                cartViewModel.addToCart(dishModels.get(holder.getAdapterPosition()));
                Toast.makeText(context.getApplicationContext(), "Item added to cart",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return dishModels.size();
    }

    public static class DishRecViewHolder extends RecyclerView.ViewHolder{

        ImageView dishIcon;
        TextView dishTitle;
        TextView dishPrice;
        Button addToCart;
        public DishRecViewHolder(@NonNull View itemView) {
            super(itemView);
            dishIcon = itemView.findViewById(R.id.dish_icon_1);
            dishTitle = itemView.findViewById(R.id.dish_title_1);
            dishPrice = itemView.findViewById(R.id.dish_price_1);
            addToCart = itemView.findViewById(R.id.add_to_cart_btn);
        }
    }


}
