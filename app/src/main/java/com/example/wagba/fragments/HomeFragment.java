package com.example.wagba.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wagba.OrderActivity;
import com.example.wagba.adapters.PreviousOrdersRecViewAdapter;
import com.example.wagba.interfaces.IOrderRecyclerView;
import com.example.wagba.interfaces.IRestaurantRecyclerView;
import com.example.wagba.R;
import com.example.wagba.RestaurantActivity;
import com.example.wagba.adapters.RestaurantsRecViewAdapter;
import com.example.wagba.models.DishModel;
import com.example.wagba.models.OrderModel;
import com.example.wagba.models.RestaurantModel;
import com.example.wagba.models.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class HomeFragment extends Fragment implements IRestaurantRecyclerView, IOrderRecyclerView {
    ArrayList<RestaurantModel> restaurantModels= new ArrayList<>();
    ArrayList<DishModel> dishModels = new ArrayList<>();
    ArrayList<OrderModel> orderModels = new ArrayList<>();
    Random rand = new Random();
    RecyclerView restaurantsRecyclerView;
    RecyclerView previousOrdersRecyclerView;
    RestaurantsRecViewAdapter adapter;
    PreviousOrdersRecViewAdapter prevOrdersAdapter;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_home, container, false);
        setupDishModel();
        setupRestaurantModels();
        setupPreviousOrdersModels();

        restaurantsRecyclerView = view.findViewById(R.id.restaurants_rcv);
        adapter = new RestaurantsRecViewAdapter(view.getContext(),restaurantModels, this);

        previousOrdersRecyclerView = view.findViewById(R.id.previous_orders_rcv);
        prevOrdersAdapter = new PreviousOrdersRecViewAdapter(view.getContext(), orderModels,this );

        restaurantsRecyclerView.setAdapter(adapter);
        restaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        previousOrdersRecyclerView.setAdapter(prevOrdersAdapter);
        previousOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }
    private void setupRestaurantModels(){
        //TODO fetch data from firebase and populate the restaurantModels
        String[] restaurantNames = getResources().getStringArray(R.array.restaurant_names);
        String[] restaurantCategories = getResources().getStringArray(R.array.restaurant_categories);

        for(int i=0; i< restaurantNames.length; i++){
            float rating = (float) (rand.nextFloat()*(5.0-0.0)+0.0);
            restaurantModels.add(new RestaurantModel(
                    restaurantNames[i],
                    restaurantCategories[i],
                    rating,
                    R.drawable.ic_baseline_restaurant_24,
                    dishModels
            ));
        }
    }
    private void setupPreviousOrdersModels(){
        String[] restaurantNames = getResources().getStringArray(R.array.restaurant_names);
        for(int i=0; i<4; i++){
            int orderNo = rand.nextInt(10-5)+5;
            float totalPrice =(float) (rand.nextInt(500-100)+100);
            orderModels.add(new OrderModel(
                    restaurantNames[i],
                    Integer.toString(orderNo),
                    "Order summary where alot of things were bought",
                    new Date(),
                    dishModels,
                    totalPrice,
                    Status.COMPLETED
            ));
        }
    }
    private void setupDishModel(){
        //TODO fetch data from firebase and populate the restaurantModels
        String[] dishNames = getResources().getStringArray(R.array.restaurant_dishes);

        for(int i=0; i< dishNames.length; i++){
            int price = (int)(rand.nextInt(100-20)+20);
            dishModels.add(new DishModel(
                    dishNames[i],
                    price,
                    R.drawable.ic_baseline_restaurant_menu_24
            ));
        }
    }
    @Override
    public void onItemClick(int position) {
        Intent intent  = new Intent(getActivity(), RestaurantActivity.class );
        intent.putExtra("restaurant",restaurantModels.get(position));
        startActivity(intent);
    }

    @Override
    public void onOrderItemClick(int position) {
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        intent.putExtra("previousOrder", orderModels.get(position));
        startActivity(intent);
    }
}