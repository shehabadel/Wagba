package com.example.wagba.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CartModel implements Parcelable {
    OrderModel order;
    float totalPrice;

    protected CartModel(Parcel in) {
        order = in.readParcelable(OrderModel.class.getClassLoader());
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

    public CartModel(OrderModel order, float totalPrice) {
        this.order = order;
        this.totalPrice = totalPrice;
    }

    public void calculateTotalPrice(){
        float sum= 0.0F;
        for(int i=0; i<order.getOrderItems().size(); i++){
            sum+=order.getOrderItems().get(i).getDishPrice();
        }
        this.totalPrice=sum;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(order, i);
        parcel.writeFloat(totalPrice);
    }

    public OrderModel getOrder() {
        return order;
    }

    public float getTotalPrice() {
        return totalPrice;
    }
}
