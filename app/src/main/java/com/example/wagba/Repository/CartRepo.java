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
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    String currentUser = auth.getCurrentUser().getUid();
    CartModel cartModel = new CartModel();

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
        pushToCart(dish);
    }
    public void removeFromCart(String dishID){
        /**
         * Remove a dish from a cart
         * */
        removeItem(dishID);

    }
    public void clearCart(){
        /**
         * Resets a cart upon creating a new order
         * */
        cart.setValue(null);
        cartModel=null;
        deleteCart();
    }
    private void loadCart() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Query cartQuery  = db.child("users").child(currentUser).child("cart");
        cartQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // To avoid populating duplicate data on change
                if(cartModel!=null){
                    cartModel.clearDishes();
                }
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String dishID = snap.getKey();
                    DishModel dish = snap.getValue(DishModel.class);
                    dish.setDishID(dishID);
                    cartModel.addDishToCart(dish);
                }
                cart.postValue(cartModel);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void pushToCart(DishModel dish){
        try{
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            DatabaseReference cartRef  = db.child("users").child(currentUser).child("cart").push();
            cartRef.setValue(dish);
        }catch(Exception e){
            Log.e("CartRepo",e.getMessage());
        }

    }
    private void removeItem(String dishID){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("users").child(currentUser).child("cart").child(dishID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void deleteCart(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference cartRef  = db.child("users").child(currentUser).child("cart");
        cartRef.setValue(null);
    }
}
