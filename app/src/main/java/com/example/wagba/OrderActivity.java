package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wagba.models.OrderModel;

import java.util.Date;

public class OrderActivity extends AppCompatActivity {
    OrderModel order;
    ImageView restaurantIcon;
    TextView restaurantName,orderNumber, orderDate, orderTotalPrice,orderSummary, orderDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        order = getIntent().getParcelableExtra("previousOrder");

        restaurantIcon = findViewById(R.id.restaurant_icon);
        restaurantName = findViewById(R.id.restaurant_name);
        orderNumber = findViewById(R.id.order_number);
        orderDate = findViewById(R.id.order_date);
        orderTotalPrice = findViewById(R.id.order_total_price);
        orderSummary= findViewById(R.id.order_summary);
        orderDetails = findViewById(R.id.order_details);

        restaurantIcon.setImageResource(R.drawable.ic_baseline_restaurant_24);
        restaurantName.setText(order.getDeliveryAddress());
        orderNumber.setText(order.getOrderID().toString());
        orderDate.setText(new Date().toString());
        orderTotalPrice.setText(Float.toString(order.getOrderTotalPrice()));
        orderDetails.setText("A lot of stuff were bought yesterday. Lorem posum bolossum ");
    }
}