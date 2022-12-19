package com.example.wagba.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wagba.Repository.DishRepo;
import com.example.wagba.models.DishModel;

import java.util.ArrayList;

public class DishViewModel extends ViewModel {
    MutableLiveData<ArrayList<DishModel>> dishes;
    public void init(int restaurantID){
        Log.d("dishview mode init","The dish view model is called");
        Log.d("dishview model init id", Integer.toString(restaurantID));
        if(dishes!=null){
            return;
        }
        dishes = DishRepo.getInstance().getDishes(restaurantID);
        Log.d("dishview model instance", dishes.getValue().toString());
    }
    public LiveData<ArrayList<DishModel>> getDishes(){
        return dishes;
    }
}
