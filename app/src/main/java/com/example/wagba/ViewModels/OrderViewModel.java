package com.example.wagba.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wagba.Repository.CartRepo;
import com.example.wagba.Repository.OrderRepo;
import com.example.wagba.models.OrderModel;


public class OrderViewModel extends ViewModel {
    MutableLiveData<OrderModel> order;

    public void init(){
        if(order!=null){
            return;
        }
        order = OrderRepo.getInstance().getOrder();
    }
    public LiveData<OrderModel> getOrder(){
        return order;
    }
    public void createOrder(OrderModel order){
        OrderRepo.getInstance().createOrder(order);
    }
}
