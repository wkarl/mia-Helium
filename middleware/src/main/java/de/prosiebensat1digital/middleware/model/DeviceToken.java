package de.prosiebensat1digital.middleware.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceToken {
    @JsonProperty("id")
    private String mId;
    
    public String getId() {
        return mId;
    }
}
