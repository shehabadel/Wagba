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
        if(dishes!=null){
            return;
        }
        dishes = DishRepo.getInstance().getDishes(restaurantID);
    }
    public LiveData<ArrayList<DishModel>> getDishes(){
        return dishes;
    }
}
