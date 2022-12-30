package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.R;
import com.example.wagba.ViewModels.CartViewModel;
import com.example.wagba.ViewModels.OrderViewModel;
import com.example.wagba.fragments.HomeFragment;
import com.example.wagba.models.CartModel;
import com.example.wagba.models.OrderModel;

import java.util.Date;

public class PaymentActivity extends AppCompatActivity {
    TextView basketTotal,total;
    Button placeOrderBtn;
    CartModel cart;
    OrderViewModel orderViewModel;
    CartViewModel cartViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        basketTotal = findViewById(R.id.basket_total);
        total = findViewById(R.id.total);
        placeOrderBtn = findViewById(R.id.place_order_btn);

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        cart = getIntent().getParcelableExtra("cart");
        basketTotal.setText(Float.toString(cart.getTotalPrice()));
        total.setText(Float.toString(cart.getTotalPrice()));

        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                OrderModel order = new OrderModel(cart,"Ain Shams University",date.toString(),"Cash","Gate 4");
                boolean orderCreateStatus = orderViewModel.createOrder(order);
                if(orderCreateStatus){
                    Toast.makeText(getApplicationContext(), "An order is created successfully!", Toast.LENGTH_SHORT).show();
                    cartViewModel.clearCart();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "An order wasn't created, perhaps there is an order running", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}