package de.prosiebensat1digital.helium.model;

import com.google.gson.annotations.SerializedName;

public class DeviceToken {
    @SerializedName("id")
    private String mId;
    
    public String getId() {
        return mId;
    }
}
