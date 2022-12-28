package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.ViewModels.UserViewModel;
import com.example.wagba.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    EditText pwEditTxt,emailEditTxt;
    Button goToSignUp, loginBtn;
    FirebaseAuth auth;
    UserViewModel userViewModel;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        /**
         * UserViewModel for inserting the logged in user in the Room's Database.
         * */
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        pwEditTxt = findViewById(R.id.pw_editTxt);
        emailEditTxt = findViewById(R.id.email_editTxt);
        goToSignUp=findViewById(R.id.SignUp_btn);
        loginBtn=findViewById(R.id.loginBtn);
        auth = FirebaseAuth.getInstance();

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                intent.putExtra("email",emailEditTxt.getText());
                intent.putExtra("pw", pwEditTxt.getText());
                startActivity(intent);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = emailEditTxt.getText().toString();
        String password = pwEditTxt.getText().toString();
        /**
         * Input Validation
         * */
        if(TextUtils.isEmpty(email)){
            Toast.makeText(LoginActivity.this,"Email cannot be empty!",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Password cannot be empty!",Toast.LENGTH_SHORT).show();
        }
        else{
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Account logged in successfully!", Toast.LENGTH_SHORT).show();
                        /**
                         * Save the logged in user in the Room's Database.
                         * */
                        User user = new User(email);
                        userViewModel.insert(user);
                        /**
                         * Start the Home activity that contains Restaurants List...etc.
                         * */
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Login Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("Login Error: ",task.getException().getMessage());
                    }
                }
            });
        }
    }
}