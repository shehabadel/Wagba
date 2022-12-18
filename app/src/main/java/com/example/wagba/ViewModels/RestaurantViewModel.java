package com.example.wagba.ViewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wagba.Repository.RestaurantRepo;
import com.example.wagba.models.RestaurantModel;

import java.util.ArrayList;

public class RestaurantViewModel extends ViewModel {
    MutableLiveData<ArrayList<RestaurantModel>> restaurants;

    public void init (){
        if(restaurants!=null){
            return;
        }
        restaurants = RestaurantRepo.getInstance().getRestaurants();
    }

    public LiveData<ArrayList<RestaurantModel>> getRestaurants(){
        return restaurants;
    }
}
