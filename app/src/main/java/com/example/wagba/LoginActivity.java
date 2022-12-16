package com.example.wagba;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    /*
    ImageView signupGoogleImg, signUpMSImg;
    private GoogleSignInClient googleSignInClient;
    FirebaseAuth auth;
    GoogleSignInOptions googleSignInOptions;
    static int RC_SIGN_IN=1234;
    */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

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

        /*
        signupGoogleImg = findViewById(R.id.signup_google);
        signUpMSImg = findViewById(R.id.signup_microsoft);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        
        
        signupGoogleImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                SignIn();
            }
        });
*/
    }

    private void loginUser() {
        String email = emailEditTxt.getText().toString();
        String password = pwEditTxt.getText().toString();
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
/*
    private void SignIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            firebaseAuthGoogleSignIn(task.);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
            }catch(ApiException e){
                e.printStackTrace();
                Toast.makeText(this,"Error in signing in", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void firebaseAuthGoogleSignIn(GoogleSignInAccount acc){
        AuthCredential credential = GoogleAuthProvider.getCredential(acc.getIdToken(),null);
        auth.signInWithCredential(credential).addOnSuccessListener(this, authResult->{
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }).addOnFailureListener(this, e->{
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        });
    }

 */
}