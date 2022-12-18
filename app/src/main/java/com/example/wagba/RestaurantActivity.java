package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wagba.adapters.DishRecViewAdapter;
import com.example.wagba.adapters.RestaurantsRecViewAdapter;
import com.example.wagba.interfaces.IDishRecyclerView;
import com.example.wagba.models.DishModel;
import com.example.wagba.models.RestaurantModel;

import java.util.ArrayList;
import java.util.Random;

public class RestaurantActivity extends AppCompatActivity implements IDishRecyclerView {
    RestaurantModel restaurant;
    ArrayList<DishModel> dishes = new ArrayList<>();
    TextView restaurantTitle, restaurantCategory, restaurantRating;
    ImageView restaurantIcon;
    Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);
        setupDishModel();
        RecyclerView dishesRecyclerView = findViewById(R.id.restaurant_dish_rcv);



        //Assigning the views
        restaurant = getIntent().getParcelableExtra("restaurant");
        restaurantCategory = findViewById(R.id.restaurant_category);
        restaurantTitle = findViewById(R.id.restaurant_title);
        restaurantRating = findViewById(R.id.restaurant_rating);
        restaurantIcon = findViewById(R.id.restaurant_icon);

        DishRecViewAdapter dishAdapter = new DishRecViewAdapter(this, restaurant.getRestaurantDishes());
        dishesRecyclerView.setAdapter(dishAdapter);
        dishesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Updating the views with restaurant model's values
        restaurantCategory.setText(restaurant.getRestaurantCategory());
        restaurantTitle.setText(restaurant.getRestaurantName());
        restaurantRating.setText(Float.toString(restaurant.getRestaurantRating()));
        restaurantIcon.setImageResource(restaurant.getRestaurantImage());
    }
    private void setupDishModel(){
        //TODO fetch data from firebase and populate the restaurantModels
        String[] dishNames = getResources().getStringArray(R.array.restaurant_dishes);

        for(int i=0; i< dishNames.length; i++){
            int price = (int)(rand.nextInt())*(100-20)+20;
            dishes.add(new DishModel(
                    dishNames[i],
                    price,
                    R.drawable.ic_baseline_restaurant_menu_24
            ));
        }
    }
    @Override
    public void onItemClick(int position) {
       Intent intent  = new Intent(RestaurantActivity.this,HomeActivity.class );
       intent.putExtra("dish",restaurant.getRestaurantDishes().get(position));
       startActivity(intent);
    }
}