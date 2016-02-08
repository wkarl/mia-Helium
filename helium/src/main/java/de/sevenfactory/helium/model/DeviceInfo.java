package de.sevenfactory.helium.model;

import com.google.gson.annotations.SerializedName;

public class DeviceInfo {
    @SerializedName("app")
    private App mApp;
    @SerializedName("device")
    private Device mDevice;

    public App getApp() {
        return mApp;
    }

    public Device getDevice() {
        return mDevice;
    }

    public DeviceInfo(App inApp, Device inDevice) {
        mApp = inApp;
        mDevice = inDevice;
    }

    public DeviceInfo(String inAppName, String inAppVersion, String inModel, String inPlatformName,
                      String inPlatformVersion) {
        mApp = new App(inAppName, inAppVersion);
        mDevice = new Device(inModel, new Platform(inPlatformName, inPlatformVersion));
    }

    public static class App {
        @SerializedName("id")
        private String mName;
        @SerializedName("version")
        private String mVersion;

        public String getName() {
            return mName;
        }

        public String getVersion() {
            return mVersion;
        }

        public App(String inName, String inVersion) {
            mName = inName;
            mVersion = inVersion;

            // Filter version name: remove everything after hyphen
            int pos = mVersion.indexOf("-");
            if (pos > 0) {
                mVersion = mVersion.substring(0, pos);
            }
        }
    }

    public static class Device {
        @SerializedName("model")
        private String mModel;
        @SerializedName("os")
        private Platform mPlatform;

        public String getModel() {
            return mModel;
        }

        public Platform getPlatform() {
            return mPlatform;
        }

        public Device(String inModel, Platform inPlatform) {
            mModel = inModel;
            mPlatform = inPlatform;
        }
    }

    public static class Platform {
        @SerializedName("type")
        private String mType;

        public String getVersion() {
            return mVersion;
        }

        public String getType() {
            return mType;
        }

        @SerializedName("version")
        private String mVersion;

        public Platform(String inType, String inVersion) {
            mType = inType;
            mVersion = inVersion;
        }
    }
}
