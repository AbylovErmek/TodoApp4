package com.geektech.todoapp4;

import android.app.Application;

import androidx.room.Room;

import com.geektech.todoapp4.room.AppDatabase;

public class App extends Application {

    private static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        new Prefs(this);
        appDatabase = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries().build();
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
