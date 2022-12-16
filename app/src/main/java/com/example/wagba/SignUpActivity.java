package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    EditText pwEditTxt,emailEditTxt;
    Button goToLogin, signUp;

    FirebaseAuth auth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        pwEditTxt = findViewById(R.id.pw_editTxt2);
        emailEditTxt = findViewById(R.id.email_editTxt2);
        goToLogin=findViewById(R.id.loginBtn_SignUpPage);
        signUp = findViewById(R.id.signup2);
        auth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void createUser() {
        String email = emailEditTxt.getText().toString();
        String password = pwEditTxt.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(SignUpActivity.this,"Email cannot be empty!",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(SignUpActivity.this,"Password cannot be empty!",Toast.LENGTH_SHORT).show();
        }
        else if (!isCollegeEmail(email)){
            Toast.makeText(SignUpActivity.this,"It must be a college email (@eng.asu.edu.eg)!",Toast.LENGTH_SHORT).show();
        }
        else{
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }else{
                        Toast.makeText(SignUpActivity.this, "Registration Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Registration Error: ",task.getException().getMessage());
                    }
                }
            });
        }
    }
    public static boolean isCollegeEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[A-Z0-9_!#$%&'*+/=?`{|}~^-]+↵\n" +
                ")*@eng.asu.edu.eg$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}