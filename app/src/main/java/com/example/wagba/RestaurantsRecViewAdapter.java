package com.example.wagba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RestaurantsRecViewAdapter extends RecyclerView.Adapter<RestaurantsRecViewAdapter.MyViewHolder>{
    private final IRestaurantRecyclerView iRestaurantRecyclerView;
    Context context;
    ArrayList<RestaurantModel> restaurantModels;

    public RestaurantsRecViewAdapter(Context context, ArrayList<RestaurantModel> restaurantModels, IRestaurantRecyclerView iRestaurantRecyclerView){
        this.context=context;
        this.restaurantModels=restaurantModels;
        this.iRestaurantRecyclerView=iRestaurantRecyclerView;
    }

    @NonNull
    @Override
    public RestaurantsRecViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout and giving a look to our rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.restaurant_item,parent,false);

        return new RestaurantsRecViewAdapter.MyViewHolder(view, iRestaurantRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Assigning values to the views we created in the recycler_view_row layout file
        //based on the position of the recycler view

        holder.restaurantTitle.setText(restaurantModels.get(position).getRestaurantName());
        holder.restaurantCategory.setText(restaurantModels.get(position).getRestaurantCategory());
        holder.restaurantRating.setText(Float.toString(restaurantModels.get(position).getRestaurantRating()));
        holder.restaurantIcon.setImageResource(restaurantModels.get(position).getRestaurantImage());
    }


    @Override
    public int getItemCount() {
        //The recycler view just wants to know the number of items you want
        //to display
        return restaurantModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //Grabbing the views from our restaurant_item layout file
        // Kidna like in the onCreate method

        ImageView restaurantIcon;
        TextView restaurantTitle;
        TextView restaurantCategory;
        TextView restaurantRating;
        public MyViewHolder(@NonNull View itemView, IRestaurantRecyclerView iRestaurantRecyclerView){
            super(itemView);
            restaurantIcon = itemView.findViewById(R.id.restaurant_icon);
            restaurantTitle = itemView.findViewById(R.id.restaurant_title);
            restaurantCategory = itemView.findViewById(R.id.restaurant_category);
            restaurantRating = itemView.findViewById(R.id.restaurant_rating);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(iRestaurantRecyclerView != null){
                        int pos = getAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION){
                            iRestaurantRecyclerView.onItemClick(pos) ;
                        }
                    }
                }
            });
        }
    }
}
