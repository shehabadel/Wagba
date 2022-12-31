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
    String currentUser = auth.getCurrentUser().getUid();
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

    private void loadOrder() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Query orderQuery = db.child("users").child(currentUser).child("order");
        orderQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderModel = snapshot.getValue(OrderModel.class);
                orderModel.setOrderID(snapshot.getKey());
                orderModel.setOrderStatus(Status.valueOf(snapshot.child("orderStatus").getValue().toString()));
                order.setValue(orderModel);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean pushOrder(OrderModel newOrder) {
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
     * Called in loadOrder, check if the current order status is completed then
     * add it to the previous orders node.
     * */
    private void addToPreviousOrders(OrderModel prevOrder){

    }
    /**
     * Used to check the availability to make an order
    * */
    private boolean canIMakeOrder(){
        if(order.getValue()==null){
            return true;
        }else if(order.getValue().getOrderStatus()==Status.COMPLETED){
            return true;
        }else{
            return false;
        }
    }

    private void getAllPrevious(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Query orderQuery = db.child("users").child(currentUser).child("previousOrders");
        orderQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prevOrdersModels.clear();
                for(DataSnapshot snap: snapshot.getChildren()){
                    Log.d("PRVORDER", (String) snap.child("orderGate").getValue());
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
    }
}
