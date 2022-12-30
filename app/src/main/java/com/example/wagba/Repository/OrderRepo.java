package com.example.wagba.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.wagba.models.OrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class OrderRepo {
    static OrderRepo instance;
    private MutableLiveData<OrderModel> order = new MutableLiveData<>();
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
    public void createOrder(OrderModel order){
        pushOrder(order);
    }

    private void loadOrder() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        Query orderQuery = db.child("users").child(currentUser).child("order");
        orderQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderModel = snapshot.getValue(OrderModel.class);
                order.setValue(orderModel);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void pushOrder(OrderModel order) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        DatabaseReference orderRef = db.child("users").child(currentUser).child("order");
        orderRef.setValue(order);

    }
}
