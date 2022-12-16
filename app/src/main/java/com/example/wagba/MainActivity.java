package com.example.wagba;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(user==null){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }else{
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }
                finish();
            }
        }, secondsDelayed * 3000);
    }
}
