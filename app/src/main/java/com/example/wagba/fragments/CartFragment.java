package com.example.wagba.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wagba.PaymentActivity;
import com.example.wagba.R;
import com.example.wagba.ViewModels.CartViewModel;
import com.example.wagba.adapters.CartItemsRecViewAdapter;
import com.example.wagba.models.CartModel;
import com.example.wagba.models.DishModel;

import java.util.ArrayList;
import java.util.Random;

public class CartFragment extends Fragment {
    Button checkoutBtn;
    ArrayList<DishModel> cartDishes = new ArrayList<DishModel>();
    Random rand = new Random();
    RecyclerView cartItemsRecView;
    CartItemsRecViewAdapter cartItemsAdapter;
    CartViewModel cartViewModel;
    CartModel cart;
    TextView basketTotal;
    TextView cartTotalTxt;
    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);
        checkoutBtn = view.findViewById(R.id.checkout_btn);
        basketTotal = view.findViewById(R.id.basket_total);
        cartTotalTxt = view.findViewById(R.id.total);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getActivity(), PaymentActivity.class);
                intent.putExtra("cart",cart);
                startActivity(intent);
            }
        });
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.init();
        cartViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<CartModel>() {
            @Override
            public void onChanged(CartModel cartModel) {
                cartItemsAdapter.notifyDataSetChanged();
                cartModel.calculateTotalPrice();
                /**
                 * Updating the CartModel that will be passed
                 * to the PaymentActivity
                 * */
                cart = new CartModel();
                cart.setCartItems(cartModel.getCartItems());
                cart.setTotalPrice(cartModel.getTotalPrice());
                setCheckoutBtnState();
                /**
                 * Populating Total TextViews.
                 * */
                float cartTotal = cartModel.getTotalPrice();
                basketTotal.setText(Float.toString(cartTotal) +" EGP");
                cartTotalTxt.setText(Float.toString(cartTotal)+" EGP");
            }
        });
        cartItemsRecView = view.findViewById(R.id.cart_items_rcv);
        cartItemsAdapter = new CartItemsRecViewAdapter(
                view.getContext(),
                cartViewModel.getCart().getValue().getCartItems())
        ;
        cartItemsRecView.setAdapter(cartItemsAdapter);
        cartItemsRecView.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));
        return view;
    }
    /**
     * Set the Checkout button to be clickable
     * if there are items in the cart.
     * */
    private void setCheckoutBtnState(){
        if(cart.getCartItems().size()>0){
            checkoutBtn.setEnabled(true);
        }else{
            checkoutBtn.setEnabled(false);
        }
    }
}