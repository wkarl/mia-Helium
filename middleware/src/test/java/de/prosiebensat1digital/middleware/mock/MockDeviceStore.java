package de.prosiebensat1digital.middleware.mock;

import de.prosiebensat1digital.middleware.model.DeviceInfo;
import de.prosiebensat1digital.middleware.store.DeviceStore;

public class MockDeviceStore implements DeviceStore {
    String     mDeviceToken;
    DeviceInfo mDeviceInfo;
    
    @Override
    public String getDeviceToken() {
        return mDeviceToken;
    }
    
    @Override
    public void setDeviceToken(final String inDeviceToken) {
        mDeviceToken = inDeviceToken;
    }
    
    @Override
    public void resetDeviceToken() {
        mDeviceToken = null;
    }
    
    @Override
    public DeviceInfo generateDeviceInfo() {
        return new DeviceInfo("7TVAndroid", "1.6.0.0", "TestDevice", "Android", "22");
    }
    
    @Override
    public DeviceInfo getStoredDeviceInfo() {
        return mDeviceInfo;
    }
    
    @Override
    public void setDeviceInfo(final DeviceInfo inDeviceInfo) {
        mDeviceInfo = inDeviceInfo;
    }
}
