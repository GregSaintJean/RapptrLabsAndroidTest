package com.datechnologies.androidtest.util;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.datechnologies.androidtest.App;

public class AndroidUtil {

    public static <T extends ViewModel> T createViewModel(Class<T> modelClass){
        ViewModelProvider.AndroidViewModelFactory factory
                = ViewModelProvider.AndroidViewModelFactory.getInstance(App.getInstance());

        return factory.create(modelClass);
    }

    public static void startActivityClearStack(AppCompatActivity currentActivity,
                                               Class newActivityClass,
                                               String action,
                                               Bundle extras){
        if(currentActivity == null)
            throw new IllegalArgumentException("currentActivity is null");

        if(newActivityClass == null)
            throw new IllegalArgumentException("newActivityClass is null");

        Intent intent = new Intent(currentActivity, newActivityClass);

        if(action != null){
            intent.setAction(action);
        }

        if(extras != null){
            intent.putExtras(extras);
        }

        intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK
        );
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }


    private AndroidUtil(){}
}
