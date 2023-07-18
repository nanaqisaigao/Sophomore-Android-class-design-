package com.example.sharedpreferences.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.sharedpreferences.Dao.UserDao;
import com.example.sharedpreferences.entity.User;

@Database(entities = { User.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}