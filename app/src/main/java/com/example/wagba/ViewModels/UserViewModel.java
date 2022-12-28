package com.example.wagba.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wagba.Repository.UserRepo;
import com.example.wagba.models.User;

public class UserViewModel extends AndroidViewModel {
    private UserRepo repository;
    private LiveData<User> user;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepo(application);
        user = repository.getUser();
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void findByEmail(String email) {
        repository.findByEmail(email);
    }
    public void findByID(int id){
        repository.findByID(id);
    }
    public void findAll(){
        repository.findAll();
    }
    public void delete(User user){
        repository.delete(user);
    }
    public void deleteAll(){
        repository.deleteAll();
    }
}
