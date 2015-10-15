package de.prosiebensat1digital.middleware.store;

import de.prosiebensat1digital.middleware.model.DeviceInfo;

public interface DeviceStore {
    String getDeviceToken();
    
    void setDeviceToken(final String inDeviceToken);
    
    void resetDeviceToken();
    
    DeviceInfo generateDeviceInfo();
    
    DeviceInfo getStoredDeviceInfo();
    
    void setDeviceInfo(DeviceInfo inDeviceInfo);
}
