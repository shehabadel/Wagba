package com.example.wagba.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.wagba.models.DishModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DishRepo {
    static DishRepo instance;
    private ArrayList<DishModel> dishModels = new ArrayList<DishModel>();
    private MutableLiveData<ArrayList<DishModel>> dish = new MutableLiveData<>();
    private static final String DISHES_PARENT_NODE = "restaurants";
    private static final String DISHES_NODE = "restaurantDishes";
    private static final String RESTAURANT_ID_KEY = "restaurantID";

    public static DishRepo getInstance(){
        if(instance==null){
            instance=new DishRepo();
        }
        return instance;
    }

    public MutableLiveData<ArrayList<DishModel>> getDishes(int restaurantID){
        if(dishModels.size()==0){

            loadDishes(restaurantID);
        }
        dish.setValue(dishModels);
        return dish;
    }

    private void loadDishes(int restaurantID) {
        DatabaseReference dishRef = FirebaseDatabase.getInstance().getReference();
        Query dishQuery  = dishRef.child(DISHES_PARENT_NODE).child(Integer.toString(restaurantID)).child(DISHES_NODE);
        dishQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()){
                   DishModel dishSnapShot = snap.getValue(DishModel.class);
                    dishModels.add(dishSnapShot);
                    dish.setValue(dishModels);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*dishRef.child(DISHES_NODE)
                .orderByChild(RESTAURANT_ID_KEY)
                .equalTo(Integer.toString(restaurantID))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap: snapshot.getChildren()){
                            Log.d("Dish Snap",snap.getValue().toString());
                            DishModel dishSnapShot = snap.getValue(DishModel.class);
                            String dishID = snap.getKey();
                            Log.d("Key of the dish: ",dishID);
                            String dishName = snap.child("dishName").getValue().toString();
                            int dishPrice = Integer.parseInt(snap.child("dishPrice").getValue().toString());
                            String dishImage = snap.child("dishImage").getValue().toString();
                            Log.d("Name of the dish: ", dishName);
                            dishModels.add(new DishModel(Integer.parseInt(dishID),dishName,dishPrice,dishImage));
                            dish.setValue(dishModels);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

         */
    }
}
