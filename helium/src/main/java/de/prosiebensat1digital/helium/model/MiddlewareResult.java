package de.prosiebensat1digital.helium.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MiddlewareResult<T> {
    private int mStatus;
    private T   mResponse;
    
    public MiddlewareResult(@JsonProperty("status") int status, @JsonProperty("response") T response) {
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
