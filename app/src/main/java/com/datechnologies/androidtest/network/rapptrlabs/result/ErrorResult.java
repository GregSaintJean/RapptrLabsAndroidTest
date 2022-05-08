package com.datechnologies.androidtest.network.rapptrlabs.result;

public class ErrorResult extends Result {
    public ErrorResult(String error, String message){
        this.error = error;
        this.message = message;
    }
}
