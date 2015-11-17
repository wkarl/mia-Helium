package de.prosiebensat1digital.helium.model;

import com.google.gson.annotations.SerializedName;

public class MiddlewareResult<T> {
    @SerializedName("status")
    private int mStatus;
    @SerializedName("response")
    private T   mResponse;
    
    public MiddlewareResult(int status, T response) {
        mStatus = status;
        mResponse = response;
    }
    
    public int getStatus() {
        return mStatus;
    }
    
    public T getResponse() {
        return mResponse;
    }
}
