package com.example.wagba.Repository;

import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.wagba.models.CartModel;
import com.example.wagba.models.DishModel;
import com.example.wagba.models.RestaurantModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartRepo {
    static CartRepo instance;
    private MutableLiveData<CartModel> cart = new MutableLiveData<>();
    private ArrayList<DishModel> dishes = new ArrayList<>();
    private FirebaseAuth auth;
    CartModel cartModel;

    public static CartRepo getInstance(){
        if(instance==null){
            instance=new CartRepo();
        }
        return instance;
    }
    public MutableLiveData<CartModel> getCart(){
        loadCart();
        cart.setValue(cartModel);
        return cart;
    }
    public void addToCart(DishModel dish){
        /**
         * Add a dish to the cart and notify changes and update the firebase db
         * */
    }
    private void loadCart() {
        cartModel = new CartModel();
        auth = FirebaseAuth.getInstance();
        String currentUser = auth.getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Query cartQuery  = db.child("users").child(currentUser).child("cart");
        cartQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String dishID = snap.getKey();
                    DishModel dish = snap.getValue(DishModel.class);
                    dish.setDishID(Integer.parseInt(dishID));
                    cartModel.addDishToCart(dish);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Looper.getMainLooper().isCurrentThread()) {
                        cart.setValue(cartModel);
                    } else {
                        cart.postValue(cartModel);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
