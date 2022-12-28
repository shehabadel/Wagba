package com.example.wagba.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wagba.models.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM users WHERE id LIKE :id LIMIT 1")
    LiveData<User> findByID(int id);

    @Query("SELECT * FROM users where email like :email LIMIT 1")
    LiveData<User> findByEmail(String email);

    @Query("SELECT * FROM users LIMIT 1")
    LiveData<User> find();

    @Query("SELECT * FROM users")
    LiveData<List<User>> findAll();

    @Query("DELETE FROM users")
    void deleteAll();
}
