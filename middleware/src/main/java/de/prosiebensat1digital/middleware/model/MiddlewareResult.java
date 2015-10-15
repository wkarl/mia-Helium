package de.prosiebensat1digital.middleware.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MiddlewareResult<T> {
    @JsonProperty("status")
    private int mStatus;
    @JsonProperty("response")
    private T   mResponse;
    
    public int getStatus() {
        return mStatus;
    }
    
    public T getResponse() {
        return mResponse;
    }
}
