package com.olskrain.calculator;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Andrey Ievlev on 19,Март,2019
 */

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Timber.plant(new Timber.DebugTree());
    }

    public static App getInstance() {
        return instance;
    }
}
