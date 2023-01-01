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
import com.example.wagba.interfaces.IOrderRecyclerView;
import com.example.wagba.models.OrderModel;

import java.util.ArrayList;

public class PreviousOrdersRecViewAdapter extends RecyclerView.Adapter<PreviousOrdersRecViewAdapter.MyViewHolder>{
    private final IOrderRecyclerView iOrderRecyclerView;
    Context context;
    ArrayList<OrderModel> orderModels;

    public PreviousOrdersRecViewAdapter(Context context, ArrayList<OrderModel>  orderModels, IOrderRecyclerView iOrderRecyclerView){
        this.context=context;
        this.orderModels=orderModels;
        this.iOrderRecyclerView=iOrderRecyclerView;
    }

    @NonNull
    @Override
    public PreviousOrdersRecViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate the layout and giving a look to our rows
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.previous_orders_item,parent,false);

        return new PreviousOrdersRecViewAdapter.MyViewHolder(view, iOrderRecyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //TODO CHANGE THIS
        holder.restaurantIcon.setImageResource(R.drawable.ic_baseline_restaurant_24);
        holder.orderDate.setText(orderModels.get(position).getOrderDate().toString());
        holder.orderNumber.setText(orderModels.get(position).getOrderID());
        holder.orderTotalPrice.setText(Float.toString(orderModels.get(position).getOrderTotalPrice())+" EGP");
    }


    @Override
    public int getItemCount() {
        //The recycler view just wants to know the number of items you want
        //to display
        return orderModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //Grabbing the views from our restaurant_item layout file
        // Kidna like in the onCreate method

        ImageView restaurantIcon;
        TextView orderDate, orderTotalPrice, orderNumber;
        public MyViewHolder(@NonNull View itemView, IOrderRecyclerView iOrderRecyclerView){
            super(itemView);
            restaurantIcon = itemView.findViewById(R.id.restaurant_icon);
            orderDate = itemView.findViewById(R.id.order_date);
            orderTotalPrice = itemView.findViewById(R.id.order_total_price);
            orderNumber = itemView.findViewById(R.id.order_number);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(iOrderRecyclerView != null){
                        int pos = getAdapterPosition();
                        if(pos!= RecyclerView.NO_POSITION){
                            iOrderRecyclerView.onOrderItemClick(pos) ;
                        }
                    }
                }
            });
        }
    }
}
