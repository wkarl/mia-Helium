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

    @Override
    public int hashCode() {
        int result = mApp != null ? mApp.hashCode() : 0;
        result = 31 * result + (mDevice != null ? mDevice.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof DeviceInfo && mApp.equals(((DeviceInfo) other).mApp) && mDevice.equals(((DeviceInfo) other).mDevice);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class App {
        public String mName;
        public String mVersion;

        public App(@JsonProperty("id") String inName, @JsonProperty("version") String inVersion) {
            mName = inName;
            mVersion = inVersion;
        }

        @Override
        public int hashCode() {
            int result = mName != null ? mName.hashCode() : 0;
            result = 31 * result + (mVersion != null ? mVersion.hashCode() : 0);
            return result;
        }

        @Override
        public boolean equals(final Object other) {
            return other instanceof App
                    && CompareUtils.equals(mName, ((App) other).mName)
                    && CompareUtils.equals(mVersion, ((App) other).mVersion);
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

        @Override
        public int hashCode() {
            int result = mModel != null ? mModel.hashCode() : 0;
            result = 31 * result + (mPlatform != null ? mPlatform.hashCode() : 0);
            return result;
        }

        @Override
        public boolean equals(final Object other) {
            return other instanceof Device
                    && CompareUtils.equals(mModel, ((Device) other).mModel)
                    && mPlatform.equals(((Device) other).mPlatform);
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

        @Override
        public int hashCode() {
            int result = mType != null ? mType.hashCode() : 0;
            result = 31 * result + (mVersion != null ? mVersion.hashCode() : 0);
            return result;
        }

        @Override
        public boolean equals(final Object other) {
            return other instanceof Platform
                    && CompareUtils.equals(mType, ((Platform) other).mType)
                    && CompareUtils.equals(mVersion, ((Platform) other).mVersion);
        }
    }
}
