package de.sevenfactory.helium.model;

import com.google.gson.annotations.SerializedName;

public class MiddlewareResult<T> {
    @SerializedName("status")
    private int mStatus;
    @SerializedName("response")
    private T   mResponse;

    public int getStatus() {
        return mStatus;
    }
    
    public T getResponse() {
        return mResponse;
    }
}
