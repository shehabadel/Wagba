package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.ViewModels.CartViewModel;
import com.example.wagba.ViewModels.OrderViewModel;
import com.example.wagba.models.CartModel;
import com.example.wagba.models.OrderModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {
    TextView basketTotal,total;
    Button placeOrderBtn;
    CartModel cart;
    OrderViewModel orderViewModel;
    CartViewModel cartViewModel;
    RadioButton Gate3,Gate4,PM12,PM3;
    String orderTime="";
    String orderGate ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        basketTotal = findViewById(R.id.basket_total);
        total = findViewById(R.id.total);
        placeOrderBtn = findViewById(R.id.place_order_btn);
        Gate3=findViewById(R.id.RadioGate3);
        Gate4=findViewById(R.id.RadioGate4);
        PM12=findViewById(R.id.Radio12PM);
        PM3=findViewById(R.id.Radio3PM);

        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        cart = getIntent().getParcelableExtra("cart");
        basketTotal.setText(Float.toString(cart.getTotalPrice()));
        total.setText(Float.toString(cart.getTotalPrice()));
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean gateCheck = setOrderGate();
                boolean timeCheck = setOrderTime();
                if(gateCheck&&timeCheck){
                String orderID = "Order#"+ new SimpleDateFormat("mm-HH-dd-MM-yyyy").format(new Date());
                OrderModel order = new OrderModel(
                        cart,
                        "Ain Shams University",
                        orderTime,
                        "Cash",
                        orderGate,
                        orderID
                );
                boolean orderCreateStatus = orderViewModel.createOrder(order);
                if(orderCreateStatus){
                    Toast.makeText(getApplicationContext(), "An order is created successfully!", Toast.LENGTH_SHORT).show();
                    cartViewModel.clearCart();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    /**
                     * Go to the HomeActivity and prevent from
                     * going back to the PaymentActivity
                     */
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "An order wasn't created, perhaps there is an order running", Toast.LENGTH_SHORT).show();
                }
                }else{
                    Toast.makeText(getApplicationContext(),"An order couldn't be made, please check again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean setOrderGate(){
        if(Gate3.isChecked()){
            orderGate = "Gate 3";
            return true;
        }else if(Gate4.isChecked()){
            orderGate = "Gate 4";
            return true;
        }else{
            Toast.makeText(this,"Please choose a delivery address!",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    private boolean setOrderTime(){
        if (PM12.isChecked()) {
            if (!checkSelectedTimeAvailability(12)) {
                Toast.makeText(this, "Orders at 12PM must be placed before 10AM", Toast.LENGTH_SHORT).show();
            } else {
                orderTime= "12:00 PM" + " "+ new SimpleDateFormat("mm-HH-dd-MM-yyyy").format(new Date());
                return true;
            }
        }else if(PM3.isChecked()){
            if (!checkSelectedTimeAvailability(15)) {
                Toast.makeText(this, "Orders at 3PM must be placed before 1PM", Toast.LENGTH_SHORT).show();
            } else {
                orderTime= "3:00 PM" + " "+new SimpleDateFormat("mm-HH-dd-MM-yyyy").format(new Date());;
                return true;
            }
        }else{
            Toast.makeText(this, "Please choose a delivery time in the known interval", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    private boolean checkSelectedTimeAvailability(int deliveryTime) {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        if(deliveryTime==12){
            return currentHour<10;
        }else if(deliveryTime==15){
            return currentHour<13;
        }else{
            return false;
        }
    }
}