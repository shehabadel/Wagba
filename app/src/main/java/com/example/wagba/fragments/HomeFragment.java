package com.example.wagba.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wagba.OrderActivity;
import com.example.wagba.ViewModels.OrderViewModel;
import com.example.wagba.ViewModels.RestaurantViewModel;
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

public class HomeFragment extends Fragment implements IRestaurantRecyclerView, IOrderRecyclerView{
    Random rand = new Random();


    /**
     * Restaurant ViewModel, Adapter, RecyclerView
     * */
    RecyclerView restaurantsRecyclerView;
    RestaurantsRecViewAdapter adapter;
    private RestaurantViewModel restaurantViewModel;
    /**
     * PreviousOrders ViewModel, Adapter, RecyclerView
     * */
    OrderViewModel previousOrderViewModel;
    RecyclerView previousOrdersRecyclerView;
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

        /**
         * Restaurant ViewModel, RecyclerView, Adapter initialization
         * */
        restaurantsRecyclerView = view.findViewById(R.id.restaurants_rcv);
        restaurantViewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);
        restaurantViewModel.init();
        restaurantViewModel.getRestaurants().observe(getViewLifecycleOwner(), new Observer<ArrayList<RestaurantModel>>() {
                @Override
                public void onChanged(ArrayList<RestaurantModel> restaurantModels) {
                    adapter.notifyDataSetChanged();
                }
            });

        adapter = new RestaurantsRecViewAdapter(
                view.getContext(),
                restaurantViewModel.getRestaurants().getValue(),
                this
        );

        /**
         * Restaurant's Recycler View setting the adapter.
         **/
        restaurantsRecyclerView.setAdapter(adapter);
        restaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        /**
         * previousOrders ViewModel, RecyclerView, Adapter initialization
         **/
        previousOrdersRecyclerView = view.findViewById(R.id.previous_orders_rcv);
        previousOrderViewModel= new ViewModelProvider(this).get(OrderViewModel.class);
        previousOrderViewModel.init();
        previousOrderViewModel.getPreviousOrders().observe(getViewLifecycleOwner(), new Observer<ArrayList<OrderModel>>() {
            @Override
            public void onChanged(ArrayList<OrderModel> orderModels) {
                prevOrdersAdapter.notifyDataSetChanged();
            }
        });
        prevOrdersAdapter = new PreviousOrdersRecViewAdapter(
                view.getContext(),
                previousOrderViewModel.getPreviousOrders().getValue(),
                this );


        /**
         * previousOrders' Recycler View setting the adapter.
         **/
        previousOrdersRecyclerView.setAdapter(prevOrdersAdapter);
        previousOrdersRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }
    @Override
    public void onItemClick(int position) {
        Intent intent  = new Intent(getActivity(), RestaurantActivity.class );
        intent.putExtra("restaurant",restaurantViewModel.getRestaurants().getValue().get(position));
        startActivity(intent);
    }

    @Override
    public void onOrderItemClick(int position) {
        Intent intent = new Intent(getActivity(), OrderActivity.class);
        intent.putExtra("previousOrder", previousOrderViewModel.getPreviousOrders().getValue().get(position));
        startActivity(intent);
    }


}