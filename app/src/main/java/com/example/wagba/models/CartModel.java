package com.example.wagba.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class CartModel implements Parcelable {
    ArrayList<DishModel> cartItems = new ArrayList<>();
    float totalPrice;

    protected CartModel(Parcel in) {
        cartItems = in.createTypedArrayList(DishModel.CREATOR);
        totalPrice = in.readFloat();
    }

    public CartModel(){}
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

    public CartModel(ArrayList<DishModel> cartItems){
        this.cartItems=cartItems;
    }
    public CartModel(ArrayList<DishModel> cartItems, float totalPrice) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }

    public void calculateTotalPrice(){
        float sum= 0.0F;
        for(int i=0; i<cartItems.size(); i++){
            sum+=cartItems.get(i).getDishPrice();
        }
        this.totalPrice=sum;
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

    public ArrayList<DishModel> getCartItems() {
        return cartItems;
    }

    public void addDishToCart(DishModel dish){
        this.cartItems.add(dish);
    }
    public float getTotalPrice() {
        return totalPrice;
    }
}
