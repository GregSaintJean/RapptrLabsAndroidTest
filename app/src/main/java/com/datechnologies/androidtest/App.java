package com.datechnologies.androidtest;

import android.app.Application;

import java.util.concurrent.Executor;

public class App  extends Application {

    private static App sInstance;
    private static AppExecutors sAppExecutors;

    @Override
    public void onCreate(){
        super.onCreate();
        sInstance = this;
        sAppExecutors = new AppExecutors();
    }

    public static App getInstance(){
        return sInstance;
    }

    public static Executor getNetworkIOExecutor(){
        return sAppExecutors.networkIO();
    }

}
