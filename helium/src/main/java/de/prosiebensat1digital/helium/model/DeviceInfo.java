package de.prosiebensat1digital.helium.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.prosiebensat1digital.helium.util.CompareUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceInfo {
    @JsonProperty("app")
    public App    mApp;
    @JsonProperty("device")
    public Device mDevice;
    
    public DeviceInfo() {
    }
    
    public DeviceInfo(String inAppName, String inAppVersion, String inModel, String inPlatformName,
                      String inPlatformVersion) {
        mApp = new App(inAppName, inAppVersion);
        mDevice = new Device(inModel, new Platform(inPlatformName, inPlatformVersion));
    }
    
    public boolean equals(final DeviceInfo other) {
        return other != null && mApp != null && mDevice != null
               && mApp.equals(other.mApp) && mDevice.equals(other.mDevice);
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class App {
        @JsonProperty("id")
        public String mName;
        @JsonProperty("version")
        public String mVersion;
        
        public App() {
        }
        
        public App(final String inName, final String inVersion) {
            mName = inName;
            mVersion = inVersion;
        }
        
        public boolean equals(final App other) {
            return other != null
                   && CompareUtils.equals(mName, other.mName)
                   && CompareUtils.equals(mVersion, other.mVersion);
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Device {
        @JsonProperty("model")
        public String   mModel;
        @JsonProperty("os")
        public Platform mPlatform;
        
        public Device() {
        }
        
        public Device(final String inModel, final Platform inPlatform) {
            mModel = inModel;
            mPlatform = inPlatform;
        }
        
        public boolean equals(final Device other) {
            return other != null
                   && CompareUtils.equals(mModel, other.mModel)
                   && mPlatform.equals(other.mPlatform);
        }
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Platform {
        @JsonProperty("type")
        public String mType;
        @JsonProperty("version")
        public String mVersion;
        
        public Platform() {
        }
        
        public Platform(final String inType, final String inVersion) {
            mType = inType;
            mVersion = inVersion;
        }
        
        public boolean equals(final Platform other) {
            return other != null
                   && CompareUtils.equals(mType, other.mType)
                   && CompareUtils.equals(mVersion, other.mVersion);
        }
    }
}
