package de.prosiebensat1digital.helium.store;

import de.prosiebensat1digital.helium.model.DeviceInfo;

public interface DeviceStore {
    String getDeviceToken();
    
    void setDeviceToken(String deviceToken);
    
    void resetDeviceToken();
    
    DeviceInfo generateDeviceInfo();
    
    DeviceInfo getStoredDeviceInfo();
    
    void setDeviceInfo(DeviceInfo deviceInfo);
}
