package de.sevenfactory.helium.network;

import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.Proxy;

import de.sevenfactory.helium.TokenRepository;

public class RequestAuthenticator implements Authenticator {
    private static final String KEY_EXPIRED       = "KeyExpired";
    private static final String DEVICE_ID_INVALID = "DeviceIdInvalid";
    private static final String HEADER_TIMESTAMP  = "timestamp";
    
    private RequestSigner   mRequestSigner;
    private TokenRepository mTokenRepository;

    public RequestAuthenticator(RequestSigner requestSigner, TokenRepository tokenRepository) {
        mRequestSigner = requestSigner;
        mTokenRepository = tokenRepository;
    }
    
    @Override
    public Request authenticate(Proxy proxy, Response response) throws IOException {
        String body = response.body().string();
        if (body.contains(KEY_EXPIRED)) {
            long serverTime = Long.decode(response.header(HEADER_TIMESTAMP));
            
            mRequestSigner.adjustLocalTime(serverTime);
            return mRequestSigner.signRequest(response.request(), mTokenRepository.getDeviceToken());
        } else if (body.contains(DEVICE_ID_INVALID)) {
            mTokenRepository.resetDeviceToken();
            return mRequestSigner.signRequest(response.request(), mTokenRepository.getDeviceToken());
        }
        
        return null;
    }
    
    @Override
    public Request authenticateProxy(final Proxy proxy, final Response response)
            throws IOException {
        // Give up
        return null;
    }
}
