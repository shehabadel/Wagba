package com.example.wagba.Repository;

import android.os.Build;
import android.os.Looper;
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
    String restaurantName = "";

    public static DishRepo getInstance(){
        if(instance==null){
            instance=new DishRepo();
        }
        return instance;
    }

    public MutableLiveData<ArrayList<DishModel>> getDishes(int restaurantID){
        dishModels.clear();
        loadDishes(restaurantID);
        dish.setValue(dishModels);
        return dish;
    }

    private void loadDishes(int restaurantID) {
        restaurantName = getRestaurantName(restaurantID);
        DatabaseReference dishRef = FirebaseDatabase.getInstance().getReference();
        Query dishQuery  = dishRef.child(DISHES_PARENT_NODE).child(Integer.toString(restaurantID)).child(DISHES_NODE);
        dishQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    dishModels.clear();
                    for(DataSnapshot snap: snapshot.getChildren()){
                        DishModel dishSnapShot = snap.getValue(DishModel.class);
                        if(restaurantName!=null)
                        {
                            dishSnapShot.setRestaurantName(restaurantName);
                        }
                        dishModels.add(dishSnapShot);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (Looper.getMainLooper().isCurrentThread()) {
                            dish.setValue(dishModels);
                        } else {
                            dish.postValue(dishModels);
                        }
                    }
                }catch(Exception e){
                    Log.e("DishRepo Exception: ",e.getStackTrace().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String getRestaurantName(int restaurantID){
        DatabaseReference restaurantRef = FirebaseDatabase.getInstance().getReference();
        Query resQuery  = restaurantRef
                .child(DISHES_PARENT_NODE)
                .child(Integer.toString(restaurantID))
                .child("restaurantName");
        resQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    String snapshotVal = (String) snapshot.getValue();
                    restaurantName=snapshotVal;
                }catch(Exception e){
                    Log.e("DishRepo Exception: ",e.getStackTrace().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return restaurantName;
    }
}
