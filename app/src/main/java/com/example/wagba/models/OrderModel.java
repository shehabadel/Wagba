package com.example.wagba.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class OrderModel implements Parcelable {
    String restaurantName;
    String orderID;
    Date orderDate;
    ArrayList<DishModel> orderItems = new ArrayList<>();
    float orderTotalPrice;
    Status orderStatus;

    public OrderModel(String restaurantName, String orderID, Date orderDate, ArrayList<DishModel> orderItems, float orderTotalPrice) {
        this.restaurantName = restaurantName;
        this.orderID = orderID;
        Date copiedDate = new Date();
        this.orderDate=copiedDate;
        this.orderItems = orderItems;
        this.orderTotalPrice = orderTotalPrice;
        ArrayList<DishModel> copiedDishModel = new ArrayList<>();
        copiedDishModel.addAll(orderItems);
        this.orderItems = copiedDishModel;
    }

    public OrderModel(String restaurantName, String orderID, Date orderDate, ArrayList<DishModel> orderItems, float orderTotalPrice, Status orderStatus) {
        this.restaurantName = restaurantName;
        this.orderID = orderID;
        Date copiedDate = new Date();
        this.orderDate=copiedDate;
        this.orderItems = orderItems;
        this.orderTotalPrice = orderTotalPrice;
        this.orderStatus = orderStatus;
        ArrayList<DishModel> copiedDishModel = new ArrayList<>();
        copiedDishModel.addAll(orderItems);
        this.orderItems = copiedDishModel;
    }


    protected OrderModel(Parcel in) {
        restaurantName = in.readString();
        orderID = in.readString();
        orderItems = in.createTypedArrayList(DishModel.CREATOR);
        orderTotalPrice = in.readFloat();
    }

    public OrderModel() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(restaurantName);
        dest.writeString(orderID);
        dest.writeTypedList(orderItems);
        dest.writeFloat(orderTotalPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderModel> CREATOR = new Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel in) {
            return new OrderModel(in);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getOrderID() {
        return orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public ArrayList<DishModel> getOrderItems() {
        return orderItems;
    }

    public float getOrderTotalPrice() {
        return orderTotalPrice;
    }


    public Status getOrderStatus() {
        return orderStatus;
    }


}
