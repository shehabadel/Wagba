package com.example.wagba.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.wagba.PaymentActivity;
import com.example.wagba.R;
import com.example.wagba.adapters.CartItemsRecViewAdapter;
import com.example.wagba.models.DishModel;

import java.util.ArrayList;
import java.util.Random;

public class CartFragment extends Fragment {
    Button checkoutBtn;
    ArrayList<DishModel> cartDishes = new ArrayList<DishModel>();
    Random rand = new Random();
    RecyclerView cartItemsRecView;
    CartItemsRecViewAdapter cartItemsAdapter;
    public CartFragment() {
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
            cartDishes.add(new DishModel(
                    dishNames[i],
                    price,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTA4DMgrZm0yjXZ3Sn3lCXjqg8Q6AHqEjVe0f54zGj7&s"
            ));
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);
        checkoutBtn = view.findViewById(R.id.checkout_btn);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getActivity(), PaymentActivity.class);
                //TODO put extra the payment model
                startActivity(intent);
            }
        });
        setupDishModel();

        cartItemsRecView = view.findViewById(R.id.cart_items_rcv);
        cartItemsAdapter = new CartItemsRecViewAdapter(view.getContext(),cartDishes);
        cartItemsRecView.setAdapter(cartItemsAdapter);
        cartItemsRecView.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));
        return view;
    }
}