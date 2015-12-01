package de.prosiebensat1digital.helium;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import de.prosiebensat1digital.helium.callback.OnDeviceTokenChangeListener;
import de.prosiebensat1digital.helium.config.Config;
import de.prosiebensat1digital.helium.model.DeviceInfo;
import de.prosiebensat1digital.helium.network.KeyInterceptor;
import de.prosiebensat1digital.helium.network.RequestAuthenticator;
import de.prosiebensat1digital.helium.network.RequestSigner;
import de.prosiebensat1digital.helium.store.DeviceStore;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class Helium {
    public static final RestAdapter.LogLevel LOG_LEVEL = RestAdapter.LogLevel.FULL;
    
    private TokenRepository mTokenRepository;
    private RestAdapter     mRestAdapter;
    
    public Helium(Config inConfig, DeviceStore inDeviceStore) {
        this(inConfig, new RequestSigner(inConfig.getSecret(), inConfig.getSecretId()), inDeviceStore);
    }
    
    Helium(Config inConfig, RequestSigner inSigner, DeviceStore inDeviceStore) {
        mTokenRepository = new TokenRepository(inConfig, inDeviceStore);
        mRestAdapter = createRestAdapter(inConfig, inSigner, mTokenRepository);
    }

    public String getDeviceToken() {
        return mTokenRepository.getDeviceToken();
    }

    public DeviceInfo getDeviceInfo() {
        return mTokenRepository.getCurrentDeviceInfo();
    }

    public void setTokenListener(OnDeviceTokenChangeListener inListener) {
        mTokenRepository.setListener(inListener);
    }
    
    public <T> T createApi(Class<T> inClass) {
        return mRestAdapter.create(inClass);
    }
    
    private RestAdapter createRestAdapter(Config inConfig, RequestSigner inSigner, TokenRepository inTokenRepository) {
        KeyInterceptor       interceptor   = new KeyInterceptor(inSigner, inTokenRepository);
        RequestAuthenticator authenticator = new RequestAuthenticator(inSigner, inTokenRepository);
        
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(interceptor);
        client.interceptors().add(authenticator);
        client.setAuthenticator(authenticator);
        
        return new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setEndpoint(inConfig.getEndpoint())
                .setConverter(new GsonConverter(new Gson()))
                .setLogLevel(LOG_LEVEL)
                .build();
    }
} 
