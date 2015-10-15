package de.prosiebensat1digital.middleware.network;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;

public class RequestAuthenticator implements Interceptor, Authenticator {
    private static final String KEY_EXPIRED       = "KeyExpired";
    private static final String DEVICE_ID_INVALID = "DeviceIdInvalid";
    private static final String HEADER_TIMESTAMP  = "timestamp";
    
    private static final int MAX_ATTEMPTS = 3;
    
    private RequestSigner mRequestSigner;
    private int           mAttemptCount;
    
    public RequestAuthenticator(final RequestSigner inRequestSigner) {
        mRequestSigner = inRequestSigner;
    }
    
    @Override
    public Request authenticate(final Proxy proxy, final Response response) throws IOException {
        if (mAttemptCount >= MAX_ATTEMPTS) {
            return null;
        }
        mAttemptCount++;
        
        String body = response.body().string();
        if (body.contains(KEY_EXPIRED)) {
            long serverTime = Long.decode(response.header(HEADER_TIMESTAMP));
            
            mRequestSigner.adjustLocalTime(serverTime);
            Request signedRequest = mRequestSigner.signRequest(response.request());
            
            return signedRequest;
        } else if (body.contains(DEVICE_ID_INVALID)) {
            mRequestSigner.resetDeviceToken();
            Request signedRequest = mRequestSigner.signRequest(response.request());
            
            return signedRequest;
        }
        
        return null;
    }
    
    @Override
    public Request authenticateProxy(final Proxy proxy, final Response response)
            throws IOException {
        // Give up
        return null;
    }
    
    @Override
    public Response intercept(final Chain chain) throws IOException {
        // Reset attempt counter on new calls
        mAttemptCount = 0;
        return chain.proceed(chain.request());
    }
}
