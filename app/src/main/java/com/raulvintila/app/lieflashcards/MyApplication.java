package com.raulvintila.app.lieflashcards;

import android.app.Application;
import android.content.res.Configuration;

import com.raulvintila.app.lieflashcards.Database.manager.DatabaseManager;
import com.raulvintila.app.lieflashcards.Database.manager.IDatabaseManager;

public class MyApplication extends Application {

    private static MyApplication singleton;
    public IDatabaseManager databaseManager;

    public MyApplication getInstance(){
        return singleton;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        databaseManager = new DatabaseManager(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
