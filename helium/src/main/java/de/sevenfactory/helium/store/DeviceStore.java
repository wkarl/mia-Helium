package de.sevenfactory.helium.store;

import de.sevenfactory.helium.model.DeviceInfo;

public interface DeviceStore {
    String getDeviceToken();
    
    void setDeviceToken(String deviceToken);
    
    void resetDeviceToken();
    
    DeviceInfo generateDeviceInfo();
    
    DeviceInfo getStoredDeviceInfo();
    
    void setDeviceInfo(DeviceInfo deviceInfo);
}
