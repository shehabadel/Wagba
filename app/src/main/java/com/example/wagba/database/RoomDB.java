package com.example.wagba.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wagba.dao.UserDao;
import com.example.wagba.models.User;

@Database(entities = {User.class}, version = 1)
public abstract class RoomDB extends RoomDatabase {
/*
* Singleton
* */
    private static RoomDB instance;

    public abstract UserDao userDao();

    public static synchronized RoomDB getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(
                    context.getApplicationContext(),
                    RoomDB.class, "WagabaDB")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
