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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wagba.LoginActivity;
import com.example.wagba.R;
import com.example.wagba.ViewModels.UserViewModel;
import com.example.wagba.models.User;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    FirebaseAuth auth;
    Button logoutBtn;
    TextView userEmail;
    ImageView userAvatar;
    UserViewModel userViewModel;
    User loggedInUser;
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
                }else{
                    User userAccount = new User(currentUserEmail);
                    userViewModel.insert(userAccount);
                }
            });
        }catch(Exception e){
            loggedInUser = new User("Exception@gmail.com");
            userEmail.setText(loggedInUser.getEmail());
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

        return view;
    }
}