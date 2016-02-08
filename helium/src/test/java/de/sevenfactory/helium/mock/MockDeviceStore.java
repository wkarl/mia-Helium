package de.sevenfactory.helium.mock;

import de.sevenfactory.helium.model.DeviceInfo;
import de.sevenfactory.helium.store.DeviceStore;

public class MockDeviceStore implements DeviceStore {
    private String     mDeviceToken;
    private DeviceInfo mDeviceInfo;

    public MockDeviceStore() {
        this(null);
    }

    public MockDeviceStore(String deviceToken) {
        mDeviceToken = deviceToken;
    }

    @Override
    public String getDeviceToken() {
        return mDeviceToken;
    }
    
    @Override
    public void setDeviceToken(final String deviceToken) {
        mDeviceToken = deviceToken;
    }
    
    @Override
    public void resetDeviceToken() {
        mDeviceToken = null;
    }
    
    @Override
    public DeviceInfo generateDeviceInfo() {
        return new DeviceInfo("UnitTests", "1.0", "MockDevice", "Android", "23");
    }
    
    @Override
    public DeviceInfo getStoredDeviceInfo() {
        return mDeviceInfo;
    }
    
    @Override
    public void setDeviceInfo(final DeviceInfo deviceInfo) {
        mDeviceInfo = deviceInfo;
    }
}
