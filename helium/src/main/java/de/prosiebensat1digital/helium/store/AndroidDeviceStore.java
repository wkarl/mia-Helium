package de.prosiebensat1digital.helium.store;

import android.content.Context;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import de.prosiebensat1digital.helium.model.DeviceInfo;
import de.prosiebensat1digital.helium.util.DeviceInfoFactory;

public class AndroidDeviceStore implements DeviceStore {
    private static final String PREFS_NAME       = "device_store";
    private static final String KEY_DEVICE_TOKEN = "device_token";
    private static final String KEY_DEVICE_INFO  = "device_info";
    
    private SharedPreferences mSharedPreferences;
    private DeviceInfo        mGeneratedDeviceInfo;
    private ObjectMapper      mObjectMapper;
    
    public AndroidDeviceStore(final Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        mGeneratedDeviceInfo = DeviceInfoFactory.create(context);
        mObjectMapper = new ObjectMapper();
    }
    
    @Override
    public String getDeviceToken() {
        return mSharedPreferences.getString(KEY_DEVICE_TOKEN, null);
    }
    
    @Override
    public void setDeviceToken(final String deviceToken) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_DEVICE_TOKEN, deviceToken);
        editor.apply();
    }
    
    @Override
    public void resetDeviceToken() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(KEY_DEVICE_TOKEN);
        editor.apply();
    }
    
    @Override
    public DeviceInfo generateDeviceInfo() {
        return mGeneratedDeviceInfo;
    }
    
    @Override
    public DeviceInfo getStoredDeviceInfo() {
        DeviceInfo deviceInfo = null;
        
        try {
            String json = mSharedPreferences.getString(KEY_DEVICE_INFO, null);
            
            if (json != null) {
                deviceInfo = mObjectMapper.readValue(json, DeviceInfo.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return deviceInfo;
    }
    
    @Override
    public void setDeviceInfo(DeviceInfo deviceInfo) {
        try {
            String json = mObjectMapper.writeValueAsString(deviceInfo);
            
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(KEY_DEVICE_INFO, json);
            editor.apply();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
