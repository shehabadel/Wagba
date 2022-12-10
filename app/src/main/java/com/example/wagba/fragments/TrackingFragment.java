package com.example.wagba.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wagba.R;
import com.example.wagba.adapters.CartItemsRecViewAdapter;
import com.example.wagba.models.DishModel;

import java.util.ArrayList;
import java.util.Random;

public class TrackingFragment extends Fragment {
    ArrayList<DishModel> orderDishes = new ArrayList<DishModel>();
    Random rand = new Random();
    RecyclerView orderItemsRecView;
    CartItemsRecViewAdapter orderItemsAdapter;
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
                    R.drawable.ic_baseline_restaurant_menu_24
            ));
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tracking, container, false);

        setupDishModel();
        orderItemsRecView = view.findViewById(R.id.order_items_rcv);
        orderItemsAdapter = new CartItemsRecViewAdapter(view.getContext(),orderDishes);
        orderItemsRecView.setAdapter(orderItemsAdapter);
        orderItemsRecView.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));
        return view;
    }
}