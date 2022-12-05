package com.example.wagba;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Random;

public class RestaurantsListActivity extends AppCompatActivity implements IRestaurantRecyclerView{
    ArrayList<RestaurantModel> restaurantModels= new ArrayList<>();
    Random rand = new Random();
    RecyclerView restaurantsRecyclerView;
    RestaurantsRecViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurants_list);
        setupRestaurantModels();

        restaurantsRecyclerView = findViewById(R.id.restaurants_rcv);
        adapter = new RestaurantsRecViewAdapter(this,restaurantModels, this);

        restaurantsRecyclerView.setAdapter(adapter);
        restaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                    R.drawable.ic_baseline_restaurant_24
                    ));
        }
    }

    @Override
    public void onItemClick(int position) {
    //TODO Open another activity that contains the Restaurant Data.
    }
}