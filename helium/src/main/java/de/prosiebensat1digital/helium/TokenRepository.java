package de.prosiebensat1digital.helium;

import de.prosiebensat1digital.helium.apiinterface.ConfigStaticApi;
import de.prosiebensat1digital.helium.callback.OnDeviceTokenChangeListener;
import de.prosiebensat1digital.helium.config.Config;
import de.prosiebensat1digital.helium.model.DeviceInfo;
import de.prosiebensat1digital.helium.model.DeviceToken;
import de.prosiebensat1digital.helium.store.DeviceStore;
import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

public class TokenRepository {
    private Config      mEndpointConfig;
    private DeviceStore mDeviceStore;
    private String      mDeviceToken;
    private DeviceInfo  mDeviceInfo;
    
    private OnDeviceTokenChangeListener mListener;
    
    protected TokenRepository(final Config endpointConfig, final DeviceStore deviceStore) {
        mEndpointConfig = endpointConfig;
        mDeviceStore = deviceStore;
        mDeviceInfo = deviceStore.generateDeviceInfo();
    }
    
    public synchronized String getDeviceToken() {
        if (mDeviceToken != null) {
            return mDeviceToken;
        }
        
        mDeviceToken = mDeviceStore.getDeviceToken();
        
        if (mDeviceToken == null) {
            DeviceToken response = getUtilApi(mEndpointConfig.getEndpoint())
                    .registerDevice(mDeviceInfo)
                    .getResponse();
            mDeviceToken = response.getId();
            mDeviceStore.setDeviceToken(mDeviceToken);
            mDeviceStore.setDeviceInfo(mDeviceInfo);
            
            if (mListener != null) {
                mListener.onChanged(mDeviceToken);
            }
        }
        
        return mDeviceToken;
    }
    
    public void resetDeviceToken() {
        mDeviceToken = null;
        mDeviceStore.resetDeviceToken();
    }
    
    public DeviceInfo getCurrentDeviceInfo() {
        return mDeviceInfo;
    }
    
    public DeviceInfo getStoredDeviceInfo() {
        return mDeviceStore.getStoredDeviceInfo();
    }
    
    public void storeDeviceInfo(DeviceInfo deviceInfo) {
        mDeviceStore.setDeviceInfo(deviceInfo);
        mDeviceInfo = deviceInfo;
    }
    
    public void setListener(OnDeviceTokenChangeListener listener) {
        mListener = listener;
    }
    
    private ConfigStaticApi getUtilApi(String endpoint) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setConverter(new JacksonConverter())
                .setLogLevel(Helium.LOG_LEVEL)
                .build();
        return restAdapter.create(ConfigStaticApi.class);
    }
}
