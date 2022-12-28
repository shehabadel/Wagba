package com.example.wagba.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wagba.Repository.CartRepo;
import com.example.wagba.models.CartModel;
import com.example.wagba.models.DishModel;

public class CartViewModel extends ViewModel {
    MutableLiveData<CartModel> cart;
    MutableLiveData<Float> totalPrice;
    public void init(){
        if(cart!=null){
            return;
        }
        cart = CartRepo.getInstance().getCart();
    }
    public LiveData<CartModel> getCart(){
        return cart;
    }
    public void addToCart(DishModel dish){
        CartRepo.getInstance().addToCart(dish);
    }
}

