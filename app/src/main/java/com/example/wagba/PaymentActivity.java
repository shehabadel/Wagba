package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.wagba.R;
import com.example.wagba.models.CartModel;

public class PaymentActivity extends AppCompatActivity {
    TextView basketTotal,total;
    CartModel cart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        basketTotal = findViewById(R.id.basket_total);
        total = findViewById(R.id.total);
        cart = getIntent().getParcelableExtra("cart");
       basketTotal.setText(Float.toString(cart.getTotalPrice()));
       total.setText(Float.toString(cart.getTotalPrice()));

    }
}