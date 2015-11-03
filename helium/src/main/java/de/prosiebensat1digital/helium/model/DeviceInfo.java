package de.prosiebensat1digital.helium.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.prosiebensat1digital.helium.util.CompareUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceInfo {
    public App mApp;
    public Device mDevice;

    public DeviceInfo(@JsonProperty("app") App inApp, @JsonProperty("device") Device inDevice) {
        mApp = inApp;
        mDevice = inDevice;
    }

    public DeviceInfo(String inAppName, String inAppVersion, String inModel, String inPlatformName,
                      String inPlatformVersion) {
        mApp = new App(inAppName, inAppVersion);
        mDevice = new Device(inModel, new Platform(inPlatformName, inPlatformVersion));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class App {
        public String mName;
        public String mVersion;

        public App(@JsonProperty("id") String inName, @JsonProperty("version") String inVersion) {
            mName = inName;
            mVersion = inVersion;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Device {
        public String mModel;
        public Platform mPlatform;

        public Device(@JsonProperty("model") String inModel, @JsonProperty("os") Platform inPlatform) {
            mModel = inModel;
            mPlatform = inPlatform;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Platform {
        public String mType;
        public String mVersion;

        public Platform(@JsonProperty("type") String inType, @JsonProperty("version") String inVersion) {
            mType = inType;
            mVersion = inVersion;
        }
    }
}
