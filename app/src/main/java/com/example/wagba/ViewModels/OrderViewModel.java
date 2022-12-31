package com.example.wagba.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wagba.Repository.CartRepo;
import com.example.wagba.Repository.OrderRepo;
import com.example.wagba.models.OrderModel;

import java.util.ArrayList;


public class OrderViewModel extends ViewModel {
    MutableLiveData<OrderModel> order;
    MutableLiveData<ArrayList<OrderModel>> previousOrders;
    public void init(){
        if(order!=null || previousOrders!=null){
            return;
        }
        order = OrderRepo.getInstance().getOrder();
        previousOrders = OrderRepo.getInstance().getPreviousOrdersLive();
    }
    public LiveData<OrderModel> getOrder(){
        return order;
    }
    public LiveData<ArrayList<OrderModel>> getPreviousOrders(){
        return previousOrders;
    }
    public boolean createOrder(OrderModel order){
        return OrderRepo.getInstance().createOrder(order);
    }
}
