package com.example.wagba.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CartModel implements Parcelable {
    ArrayList<DishModel> cartItems = new ArrayList<>();
    float totalPrice;

    public CartModel(){}

    public CartModel(ArrayList<DishModel> cartItems){
        this.cartItems=cartItems;
    }
    public CartModel(ArrayList<DishModel> cartItems, float totalPrice) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }

    protected CartModel(Parcel in) {
        cartItems = in.createTypedArrayList(DishModel.CREATOR);
        totalPrice = in.readFloat();
    }

    public static final Creator<CartModel> CREATOR = new Creator<CartModel>() {
        @Override
        public CartModel createFromParcel(Parcel in) {
            return new CartModel(in);
        }

        @Override
        public CartModel[] newArray(int size) {
            return new CartModel[size];
        }
    };

    public void setCartItems(ArrayList<DishModel> cartItems) {
        this.cartItems = cartItems;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void calculateTotalPrice(){
        float sum= 0.0F;
        for(int i=0; i<cartItems.size(); i++){
            sum+=cartItems.get(i).getDishPrice();
        }
        this.totalPrice=sum;
    }
    public ArrayList<DishModel> getCartItems() {
        return cartItems;
    }
    public void addDishToCart(DishModel dish){
        this.cartItems.add(dish);
    }
    public float getTotalPrice() {
        return totalPrice;
    }
    public void clearDishes(){
        this.cartItems.clear();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(cartItems);
        parcel.writeFloat(totalPrice);
    }
}
