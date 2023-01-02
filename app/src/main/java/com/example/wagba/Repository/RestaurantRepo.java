package com.example.wagba.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.wagba.models.DishModel;
import com.example.wagba.models.RestaurantModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RestaurantRepo {
    static RestaurantRepo instance;
    private ArrayList<RestaurantModel> restaurantModels = new ArrayList<RestaurantModel>();
    private MutableLiveData<ArrayList<RestaurantModel>> restaurant = new MutableLiveData<>();


    public static RestaurantRepo getInstance(){
       if(instance==null){
           instance = new RestaurantRepo();
       }
       return instance;
   }

   public MutableLiveData<ArrayList<RestaurantModel>> getRestaurants(){
        loadRestaurants();
       restaurant.setValue(restaurantModels);
        return restaurant;
   }

    private void loadRestaurants() {
        DatabaseReference restaurantRef = FirebaseDatabase.getInstance().getReference();

        Query restaurantQuery = restaurantRef.child("restaurants");
        restaurantQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /**
                 * To avoid populating of duplicate data
                 */
                restaurantModels.clear();
                try{
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        /**
                         * Extracting the restaurant data manually to avoid
                         * converting the data attributes to HashMap
                         * */
                        String restaurantID = snap.getKey();
                        String restaurantCategory = (String) snap.child("restaurantCategory").getValue();
                        String restaurantName = (String) snap.child("restaurantName").getValue();
                        float restaurantRating = Float.parseFloat(snap.child("restaurantRating").getValue().toString());
                        String restaurantImage = (String) snap.child("restaurantImage").getValue();

                        restaurantModels.add(new RestaurantModel(Integer.parseInt(restaurantID),restaurantName, restaurantCategory, restaurantRating, restaurantImage));
                        restaurant.setValue(restaurantModels);
                    }
                }catch(Exception e){
                    Log.e("ResRepo Exception: ", e.getStackTrace().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
