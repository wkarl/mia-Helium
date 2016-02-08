package de.sevenfactory.helium;

import com.google.gson.Gson;

import de.sevenfactory.helium.apiinterface.ConfigStaticApi;
import de.sevenfactory.helium.callback.OnDeviceTokenChangeListener;
import de.sevenfactory.helium.config.Config;
import de.sevenfactory.helium.model.DeviceInfo;
import de.sevenfactory.helium.model.DeviceToken;
import de.sevenfactory.helium.store.DeviceStore;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

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
    
    public synchronized void resetDeviceToken() {
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
    
    public synchronized void setListener(OnDeviceTokenChangeListener listener) {
        mListener = listener;
    }
    
    private ConfigStaticApi getUtilApi(String endpoint) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setConverter(new GsonConverter(new Gson()))
                .setLogLevel(Helium.LOG_LEVEL)
                .build();
        return restAdapter.create(ConfigStaticApi.class);
    }
}
