package com.example.wagba.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.wagba.models.OrderModel;
import com.example.wagba.models.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderRepo {
    static OrderRepo instance;
    private MutableLiveData<OrderModel> order = new MutableLiveData<>();
    private MutableLiveData<ArrayList<OrderModel>> previousOrdersLive = new MutableLiveData<>();
    private ArrayList<OrderModel> prevOrdersModels = new ArrayList<OrderModel>();
    private FirebaseAuth auth =  FirebaseAuth.getInstance();
    OrderModel orderModel = new OrderModel();

    public static OrderRepo getInstance(){
        if(instance==null){
            instance = new OrderRepo();
        }
        return instance;
    }

    public MutableLiveData<OrderModel> getOrder() {
        loadOrder();
        order.setValue(orderModel);
        return order;
    }
    public MutableLiveData<ArrayList<OrderModel>> getPreviousOrdersLive(){
        prevOrdersModels.clear();
        getAllPrevious();
        previousOrdersLive.setValue(prevOrdersModels);
        return previousOrdersLive;
    }
    public boolean createOrder(OrderModel order){
        return pushOrder(order);
    }
/**
 * Fetches an order to be shown in the tracking page
 * TODO: Fix if there are null values.
 * */
    private void loadOrder() {
        try{
        String currentUser = auth.getCurrentUser().getUid();
        Log.d("CurrentUser",currentUser);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Query orderQuery = db.child("users").child(currentUser).child("order");
        orderQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    if(snapshot.exists()){
                        orderModel = snapshot.getValue(OrderModel.class);
                        orderModel.setOrderID(snapshot.getKey());
                        orderModel.setOrderStatus(Status.valueOf(snapshot.child("orderStatus").getValue().toString()));
                        order.setValue(orderModel);
                    }
                }catch(Exception e){
                    Log.e("loadOrder",e.getMessage());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }catch(Exception e){
            Log.e("LoadOrder-UID",e.getMessage());
        }
    }
/**
 * Creates an order and pushes it to the order node of a user
 * returns true if order is made successfully, otherwise false.
 * */
    private boolean pushOrder(OrderModel newOrder) {
        String currentUser = auth.getCurrentUser().getUid();
        loadOrder();
        boolean orderStatus=canIMakeOrder();
        boolean orderMade=false;
        /**
         * Create a new order when there is no current order,
         * or the current order's status is completed.
         * */
        try{
            if(orderStatus) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                DatabaseReference orderRef = db.child("users").child(currentUser).child("order");
                orderRef.setValue(newOrder);
                orderMade = true;
            }else{
                try{
                    Log.d("Ordestat",orderModel.getOrderStatus().toString());
                }catch(Exception e){
                    Log.d("orderexcp",e.getMessage());
                }
                orderMade=false;
            }
        }catch(Exception e){
            Log.e("OrderRepo-Push",e.getMessage());
        }
        return orderMade;
    }
    /**
     * Used to check the availability to make an order
    * */
    private boolean canIMakeOrder(){
        if(order.getValue()==null || order.getValue().getOrderStatus()==null){
            return true;
        }else if(order.getValue().getOrderStatus()==Status.COMPLETED){
            return true;
        }else{
            return false;
        }
    }
/**
 * Fetches all the previous orders.
 * */
    private void getAllPrevious(){
        String currentUser = auth.getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        try{
        Query orderQuery = db.child("users").child(currentUser).child("previousOrders");
        orderQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prevOrdersModels.clear();
                for(DataSnapshot snap: snapshot.getChildren()){
                    OrderModel orderSnapshot = snap.getValue(OrderModel.class);
                    orderSnapshot.setOrderID(snap.getKey());
                    prevOrdersModels.add(orderSnapshot);
                    previousOrdersLive.setValue(prevOrdersModels);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }catch(Exception e){
            Log.e("getPrevOrders",e.getMessage());
        }
    }
}
