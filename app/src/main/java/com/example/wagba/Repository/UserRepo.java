package com.example.wagba.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.wagba.dao.UserDao;
import com.example.wagba.database.RoomDB;
import com.example.wagba.models.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepo {
    private UserDao userDao;
    private LiveData<List<User>> users;
    private LiveData<User> user;

    public UserRepo(Application application) {
        RoomDB database = RoomDB.getInstance(application);
        userDao = database.userDao();
        /**
         * find returns a User not List<User>.
         * It's just because there's only one user's details saved in the Database
         * */
        users = userDao.findAll();
    }

    public void insert(User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(User user) {
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void delete(User user){
        new DeleteUserAsyncTask(userDao).execute(user);
    }
    public LiveData<User> findByEmail(String email) throws ExecutionException, InterruptedException {
        FindByEmailAsyncTask task = new FindByEmailAsyncTask(userDao);
        LiveData<User> user = task.execute(email).get();
        return user;
    }
    public void findByID(int id){
        new FindByIDAsyncTask(userDao).execute(id);
    }
    public void findAll(){
        new FindAllAsyncTask(userDao).execute();
    }
    public void deleteAll(){
        new DeleteAllAsyncTask(userDao).execute();
    }
    public LiveData<List<User>> getUsers() {
        return users;
    }
    public LiveData<User> getUser(){
        return user;
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private UserDao userDao;
        private DeleteAllAsyncTask(UserDao userDao){
            this.userDao=userDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAll();
            return null;
        }
    }
    private static class FindAllAsyncTask extends AsyncTask<Void, Void, LiveData<User>> {
        private UserDao userDao;

        private FindAllAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }


        @Override
        protected LiveData<User> doInBackground(Void... voids) {
            return userDao.find();
        }

    }
    private static class FindByIDAsyncTask extends AsyncTask<Integer, Void, LiveData<User>> {
        private UserDao userDao;

        private FindByIDAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }


        @Override
        protected LiveData<User> doInBackground(Integer... ints) {
            return userDao.findByID(ints[0]);
        }
    }
    private static class DeleteUserAsyncTask extends AsyncTask<User,Void,Void>{
        private UserDao userDao;
        private DeleteUserAsyncTask(UserDao userDao){
            this.userDao=userDao;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }
    private static class FindByEmailAsyncTask extends AsyncTask<String, Void, LiveData<User>> {
        private UserDao userDao;

        private FindByEmailAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected LiveData<User> doInBackground(String... strings) {
            return userDao.findByEmail(strings[0]);

        }
    }
    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }
    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUser(users[0]);
            return null;
        }
    }
}