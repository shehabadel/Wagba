package com.example.wagba.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class OrderModel implements Parcelable {
    String deliveryAddress;
    String orderID;
    String orderDate;
    ArrayList<DishModel> orderItems = new ArrayList<>();
    String orderPayMethod;
    String orderGate;
    float orderTotalPrice;
    Status orderStatus;

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderItems(ArrayList<DishModel> orderItems) {
        this.orderItems.addAll(orderItems);
    }

    public String getOrderPayMethod() {
        return orderPayMethod;
    }

    public void setOrderPayMethod(String orderPayMethod) {
        this.orderPayMethod = orderPayMethod;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderModel(String deliveryAddress, String orderID, String orderDate, ArrayList<DishModel> orderItems, String orderPayMethod, String orderGate, float orderTotalPrice, Status orderStatus) {
        this.deliveryAddress = deliveryAddress;
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
        this.orderPayMethod = orderPayMethod;
        this.orderGate = orderGate;
        this.orderTotalPrice = orderTotalPrice;
        this.orderStatus = orderStatus;
    }

    public void setOrderTotalPrice(float orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderGate() {
        return orderGate;
    }

    public void setOrderGate(String orderGate) {
        this.orderGate = orderGate;
    }

    public OrderModel(CartModel cartModel, String deliveryAddress, String orderDate, String orderPayMethod, String orderGate){
        this.orderItems=cartModel.getCartItems();
        this.orderTotalPrice=cartModel.getTotalPrice();
        this.orderStatus=Status.PLACED;
        this.deliveryAddress=deliveryAddress;
        this.orderDate=orderDate;
        this.orderPayMethod=orderPayMethod;
        this.orderGate=orderGate;
    }
    public OrderModel(String deliveryAddress, String orderID, String orderDate, ArrayList<DishModel> orderItems, float orderTotalPrice) {
        this.deliveryAddress = deliveryAddress;
        this.orderID = orderID;
        this.orderDate=orderDate;
        this.orderItems = orderItems;
        this.orderTotalPrice = orderTotalPrice;
        ArrayList<DishModel> copiedDishModel = new ArrayList<>();
        copiedDishModel.addAll(orderItems);
        this.orderItems = copiedDishModel;
    }

    public OrderModel(String deliveryAddress, String orderID, String orderDate, ArrayList<DishModel> orderItems, float orderTotalPrice, Status orderStatus) {
        this.deliveryAddress = deliveryAddress;
        this.orderID = orderID;
        this.orderDate=orderDate;
        this.orderItems = orderItems;
        this.orderTotalPrice = orderTotalPrice;
        this.orderStatus = orderStatus;
        ArrayList<DishModel> copiedDishModel = new ArrayList<>();
        copiedDishModel.addAll(orderItems);
        this.orderItems = copiedDishModel;
    }

    public OrderModel() {

    }


    protected OrderModel(Parcel in) {
        deliveryAddress = in.readString();
        orderID = in.readString();
        orderItems = in.createTypedArrayList(DishModel.CREATOR);
        orderPayMethod = in.readString();
        orderGate = in.readString();
        orderTotalPrice = in.readFloat();
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

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public ArrayList<DishModel> getOrderItems() {
        return orderItems;
    }

    public float getOrderTotalPrice() {
        return orderTotalPrice;
    }


    public Status getOrderStatus() {
        return this.orderStatus;
    }
public String getOrderStatusString(){
        return this.orderStatus.toString();
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(deliveryAddress);
        parcel.writeString(orderID);
        parcel.writeTypedList(orderItems);
        parcel.writeString(orderPayMethod);
        parcel.writeString(orderGate);
        parcel.writeFloat(orderTotalPrice);
        parcel.writeString(orderDate);
    }
}
