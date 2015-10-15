package de.prosiebensat1digital.middleware.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MiddlewareError {
    @JsonProperty("status")
    private int         mStatus;
    @JsonProperty("errors")
    private List<Error> mErrors;
    
    public int getStatus() {
        return mStatus;
    }
    
    public Error getError() {
        return mErrors.get(0);
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Error {
        @JsonProperty("code")
        private String mCode;
        @JsonProperty("msg")
        private String mMessage;
        
        public String getCode() {
            return mCode;
        }
        
        public String getMessage() {
            return mMessage;
        }
    }
}
