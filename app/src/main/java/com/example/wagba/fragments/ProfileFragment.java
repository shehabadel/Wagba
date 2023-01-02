package com.example.wagba.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wagba.LoginActivity;
import com.example.wagba.R;
import com.example.wagba.ViewModels.UserViewModel;
import com.example.wagba.models.User;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    FirebaseAuth auth;
    Button logoutBtn, updateDataBtn;
    TextView userEmail,userName,userYear,userDepartment;
    ImageView userAvatar;
    UserViewModel userViewModel;
    User loggedInUser;
    EditText editName,editYear,editDepartment;

    public ProfileFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        logoutBtn = view.findViewById(R.id.logout_btn);
        userEmail = view.findViewById(R.id.profile_email);
        userName=view.findViewById(R.id.name);
        userYear=view.findViewById(R.id.year);
        userDepartment=view.findViewById(R.id.department);

        editName = view.findViewById(R.id.editTextName);
        editYear=view.findViewById(R.id.editTextYear);
        editDepartment=view.findViewById(R.id.editTextDepartment);
        updateDataBtn = view.findViewById(R.id.updateData_btn);


        /**
         * UserViewModel for retrieving the User's logged in Data from RoomDB.
         * */
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        /**
         * Firebase Authentication object for signing out functionality.
         * */
        auth = FirebaseAuth.getInstance();

        //Current logged in user email
        String currentUserEmail = auth.getCurrentUser().getEmail();

        try{
            userViewModel.findByEmail(currentUserEmail).observe(getViewLifecycleOwner(), user -> {
                if(user!=null){
                    loggedInUser = user;
                    /**
                     * set the avatar of the user, name, email...etc.
                     * */
                    userEmail.setText(loggedInUser.getEmail());
                    userName.setText(loggedInUser.getName());
                    userDepartment.setText(loggedInUser.getYear());
                    userYear.setText(loggedInUser.getDepartment());
                }else{
                    User userAccount = new User(currentUserEmail);
                    userViewModel.insert(userAccount);
                }
            });
        }catch(Exception e){
            loggedInUser = new User("Exception@gmail.com");
            userEmail.setText(loggedInUser.getEmail());
            userName.setText("Your name here");
            userDepartment.setText("Your department here");
            userYear.setText("Your year here");
            Log.e("ProfileFrag",e.getMessage());
        }
        /**
         * Logout functionality.
         * Destroy the user's session.
         * */
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        /**
         * Update user's data functionality
         * */
        updateDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(!editName.getText().toString().isEmpty()){
                        loggedInUser.setName(editName.getText().toString());
                    }
                    if(!editYear.getText().toString().isEmpty()){
                        loggedInUser.setYear(editYear.getText().toString());
                    }
                    if(!editDepartment.getText().toString().isEmpty()){
                        loggedInUser.setDepartment(editDepartment.getText().toString());
                    }
                    userViewModel.update(loggedInUser);
                    Toast.makeText(getContext(), "User updated successfully", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Log.d("ProfileFrag","Can't fetch Name, Year, or Department");
                }
            }
        });
        return view;
    }
}