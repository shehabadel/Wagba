package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.ViewModels.DishViewModel;
import com.example.wagba.adapters.DishRecViewAdapter;
import com.example.wagba.interfaces.IDishRecyclerView;
import com.example.wagba.models.DishModel;
import com.example.wagba.models.RestaurantModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class RestaurantActivity extends AppCompatActivity implements IDishRecyclerView {
    RestaurantModel restaurant;
    TextView restaurantTitle, restaurantCategory, restaurantRating;
    ImageView restaurantIcon;
    /**
     * Dish ViewModel, Adapter, and restaurantID for fetching dishes.
     * */
    int restaurantID;
    private DishViewModel dishViewModel;
    DishRecViewAdapter dishAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);
        /**
         * Assigning the views
         * */
        restaurant = getIntent().getParcelableExtra("restaurant");
        restaurantCategory = findViewById(R.id.restaurant_category);
        restaurantTitle = findViewById(R.id.restaurant_title);
        restaurantRating = findViewById(R.id.restaurant_rating);
        restaurantIcon = findViewById(R.id.restaurant_icon);
        restaurantID = restaurant.getRestaurantID();
        /**
         * Dish ViewMode, RecyclerView, Adapter
         * */
        RecyclerView dishesRecyclerView = findViewById(R.id.restaurant_dish_rcv);
        dishViewModel = new ViewModelProvider(this).get(DishViewModel.class);
        dishViewModel.init(restaurantID);
        dishViewModel.getDishes().observe(this, new Observer<ArrayList<DishModel>>() {
            @Override
            public void onChanged(ArrayList<DishModel> dishModels) {
                dishAdapter.notifyDataSetChanged();
            }
        });
        dishAdapter = new DishRecViewAdapter(
                this,
                dishViewModel.getDishes().getValue()
        );

       /**
        * Dish's Recycler View setting the adapter
        * */
        dishesRecyclerView.setAdapter(dishAdapter);
        dishesRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        /**
         * Updating the views with restaurant model's values
         * */
        restaurantCategory.setText(restaurant.getRestaurantCategory());
        restaurantTitle.setText(restaurant.getRestaurantName());
        restaurantRating.setText(Float.toString(restaurant.getRestaurantRating()));
        Picasso.get().load(restaurant.getRestaurantImage()).into(restaurantIcon);
    }
    @Override
    public void onDishItemClick(int position) {
        Toast.makeText(RestaurantActivity.this, "Easter-Egg: Check the Logcat", Toast.LENGTH_SHORT).show();
        Log.d("Easter-Egg: ","Sorry for the messy design but working in a part-time job, working on my own project, " +
                "and college's stuff including Graduation project" + " has got me so overwhelmed to think about considering a neat design.");
    }
}