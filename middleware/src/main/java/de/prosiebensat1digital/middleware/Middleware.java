package de.prosiebensat1digital.middleware;

import com.squareup.okhttp.OkHttpClient;

import de.prosiebensat1digital.middleware.callback.OnDeviceTokenChangeListener;
import de.prosiebensat1digital.middleware.config.Config;
import de.prosiebensat1digital.middleware.network.JsonConverter;
import de.prosiebensat1digital.middleware.network.KeyInterceptor;
import de.prosiebensat1digital.middleware.network.RequestAuthenticator;
import de.prosiebensat1digital.middleware.network.RequestSigner;
import de.prosiebensat1digital.middleware.store.DeviceStore;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class Middleware {
    public static final RestAdapter.LogLevel LOG_LEVEL = RestAdapter.LogLevel.FULL;

    private TokenRepository mTokenRepository;
    private RestAdapter     mRestAdapter;

    public Middleware(Config inConfig, DeviceStore inDeviceStore) {
        this(inConfig, new RequestSigner(inConfig.getSecret(), inConfig.getSecretId()), inDeviceStore);
    }

    Middleware(Config inConfig, RequestSigner inSigner, DeviceStore inDeviceStore) {
        mTokenRepository = new TokenRepository(inConfig, inDeviceStore);
        RequestSigner signer = inSigner;
        mRestAdapter = createRestAdapter(inConfig, signer, mTokenRepository);
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
                .setConverter(new JsonConverter())
                .setLogLevel(LOG_LEVEL)
                .build();
    }
} 
