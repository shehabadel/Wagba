package com.example.wagba.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wagba.Repository.UserRepo;
import com.example.wagba.models.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserViewModel extends AndroidViewModel {
    private UserRepo repository;
    private LiveData<List<User>> users;
    private LiveData<User> loggedInUser;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepo(application);
        users = repository.getUsers();
    }

    public void insert(User user) {
        repository.insert(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public LiveData<User> findByEmail(String email) throws ExecutionException, InterruptedException {
        LiveData<User> user =  repository.findByEmail(email);
        return user;
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
