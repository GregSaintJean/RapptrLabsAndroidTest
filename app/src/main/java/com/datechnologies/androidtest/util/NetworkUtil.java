package com.datechnologies.androidtest.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    public static boolean hasNetworkConnectivity(Context ctx){
        if(ctx == null){
            return false;
        }

        NetworkInfo networkInfo = getConnectivityManager(ctx).getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static ConnectivityManager getConnectivityManager(Context ctx){
        if(ctx == null){
            throw new IllegalArgumentException("ctx is null");
        }

        return (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private NetworkUtil(){}
}
