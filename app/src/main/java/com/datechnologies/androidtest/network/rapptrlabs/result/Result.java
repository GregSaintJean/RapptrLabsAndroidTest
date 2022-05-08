package com.datechnologies.androidtest.network.rapptrlabs.result;

public abstract class Result {
    protected String error;
    protected String message;

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
