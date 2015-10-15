package de.prosiebensat1digital.middleware;

import de.prosiebensat1digital.middleware.apiinterface.ConfigStaticApi;
import de.prosiebensat1digital.middleware.callback.OnDeviceTokenChangeListener;
import de.prosiebensat1digital.middleware.config.Config;
import de.prosiebensat1digital.middleware.model.DeviceInfo;
import de.prosiebensat1digital.middleware.model.DeviceToken;
import de.prosiebensat1digital.middleware.store.DeviceStore;
import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

public class TokenRepository {
    private Config      mEndpointConfig;
    private DeviceStore mDeviceStore;
    private String      mDeviceToken;
    private DeviceInfo  mDeviceInfo;
    
    private OnDeviceTokenChangeListener mListener;
    
    protected TokenRepository(final Config inEndpointConfig, final DeviceStore inDeviceStore) {
        mEndpointConfig = inEndpointConfig;
        mDeviceStore = inDeviceStore;
        mDeviceInfo = inDeviceStore.generateDeviceInfo();
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
    
    public void storeDeviceInfo(DeviceInfo inDeviceInfo) {
        mDeviceStore.setDeviceInfo(inDeviceInfo);
        mDeviceInfo = inDeviceInfo;
    }
    
    public void setListener(OnDeviceTokenChangeListener inListener) {
        mListener = inListener;
    }
    
    private ConfigStaticApi getUtilApi(String inEndpoint) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(inEndpoint)
                .setConverter(new JacksonConverter())
                .setLogLevel(Middleware.LOG_LEVEL)
                .build();
        return restAdapter.create(ConfigStaticApi.class);
    }
}
