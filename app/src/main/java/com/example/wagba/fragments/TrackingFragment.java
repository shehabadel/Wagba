package com.example.wagba.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.R;
import com.example.wagba.ViewModels.OrderViewModel;
import com.example.wagba.adapters.CartItemsRecViewAdapter;
import com.example.wagba.adapters.TrackingItemsRecViewAdapter;
import com.example.wagba.models.DishModel;
import com.example.wagba.models.OrderModel;
import com.example.wagba.models.Status;

import java.util.ArrayList;
import java.util.Random;

public class TrackingFragment extends Fragment {
    ArrayList<DishModel> orderDishes = new ArrayList<DishModel>();
    Random rand = new Random();
    RecyclerView orderItemsRecView;
    TrackingItemsRecViewAdapter orderItemsAdapter;
    OrderViewModel orderViewModel;
    TextView orderStatusTXT, orderDateTXT, orderAddressTXT, orderGateTXT, orderTotalTXT;
    OrderModel order;
    public TrackingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void setupDishModel(){
        //TODO fetch data from firebase and populate the orderDishes
        String[] dishNames = getResources().getStringArray(R.array.restaurant_dishes);

        for(int i=0; i< dishNames.length; i++){
            int price = (int)(rand.nextInt(100-20)+20);
            orderDishes.add(new DishModel(
                    dishNames[i],
                    price,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTA4DMgrZm0yjXZ3Sn3lCXjqg8Q6AHqEjVe0f54zGj7&s"
            ));
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tracking, container, false);
        orderStatusTXT=view.findViewById(R.id.order_status);
        orderDateTXT=view.findViewById(R.id.order_date);
        orderAddressTXT = view.findViewById(R.id.order_address);
        orderGateTXT = view.findViewById(R.id.order_delivery_gate);
        orderTotalTXT = view.findViewById(R.id.order_total_price);

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        orderViewModel.init();
        orderViewModel.getOrder().observe(getViewLifecycleOwner(), new Observer<OrderModel>() {
            @Override
            public void onChanged(OrderModel orderModel) {
                orderItemsAdapter.notifyDataSetChanged();
                orderDishes.clear();
                if(orderModel!=null && orderModel.getOrderStatus()!=null){
                    orderDateTXT.setText(orderModel.getOrderDate());
                    orderAddressTXT.setText(orderModel.getDeliveryAddress());
                    orderGateTXT.setText(orderModel.getOrderGate());
                    orderTotalTXT.setText(Float.toString(orderModel.getOrderTotalPrice()));
                    orderDishes.addAll(orderModel.getOrderItems());
                    orderStatusTXT.setText(orderModel.getOrderStatus().toString());
                    if(orderModel.getOrderStatus()== Status.COMPLETED){
                        Toast.makeText(getContext(),"Your order has been completed successfully!",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        orderItemsRecView = view.findViewById(R.id.order_items_rcv);
        orderItemsAdapter = new TrackingItemsRecViewAdapter(view.getContext(),orderDishes);
        orderItemsRecView.setAdapter(orderItemsAdapter);
        orderItemsRecView.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));
        return view;
    }
}