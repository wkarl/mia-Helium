package de.sevenfactory.helium.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MiddlewareError {
    @SerializedName("status")
    private int         mStatus;
    @SerializedName("errors")
    private List<Error> mErrors;
    
    public int getStatus() {
        return mStatus;
    }
    
    public Error getError() {
        return mErrors.get(0);
    }
    
    public static class Error {
        @SerializedName("code")
        private String mCode;
        @SerializedName("msg")
        private String mMessage;
        
        public String getCode() {
            return mCode;
        }
        
        public String getMessage() {
            return mMessage;
        }
    }
}
