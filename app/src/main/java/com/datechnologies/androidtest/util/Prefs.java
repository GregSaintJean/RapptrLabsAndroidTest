package com.datechnologies.androidtest.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.datechnologies.androidtest.R;

public class Prefs {

    public static boolean setLogin(Context ctx,
                               boolean async,
                               int value){
        if(ctx == null){
            throw new IllegalArgumentException("ctx is null");
        }

        SharedPreferences.Editor editor = getSharedPreferencesEditor(ctx);
        editor.putInt(ctx.getString(R.string.loggedin), value);

        if(async){
            editor.apply();
            return true;
        } else {
            return editor.commit();
        }
    }

    public static int getLogin(Context ctx){
        if(ctx == null){
            throw new IllegalArgumentException("ctx is null");
        }

        return getSharedPreferences(ctx).getInt(ctx.getString(R.string.loggedin), 0);
    }

    public static SharedPreferences.Editor getSharedPreferencesEditor(Context ctx){
        if(ctx == null){
            throw new IllegalArgumentException("ctx is null");
        }

        return ctx.getSharedPreferences(ctx.getString(R.string.android_test_key), Context.MODE_PRIVATE).edit();
    }

    private static SharedPreferences getSharedPreferences(Context ctx){
        if(ctx == null){
            throw new IllegalArgumentException("ctx is null");
        }

        return ctx.getSharedPreferences(ctx.getString(R.string.android_test_key), Context.MODE_PRIVATE);
    }


    private Prefs(){}
}
