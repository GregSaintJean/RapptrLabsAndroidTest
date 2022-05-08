package com.datechnologies.androidtest.network;

import com.datechnologies.androidtest.network.rapptrlabs.RapptrlabsService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static RapptrlabsService service;
    private static Retrofit sRetrofit;


    public static RapptrlabsService getService(){
        if(sRetrofit == null){
            sRetrofit = getRetroFit();
        }

        if(service ==  null){
            service = sRetrofit.create(RapptrlabsService.class);
        }

        return service;
    }

    public static Retrofit getRetroFit(){
        if(sRetrofit == null){
            sRetrofit = new Retrofit.Builder()
                    .baseUrl("https://dev.rapptrlabs.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return sRetrofit;
    }
}
