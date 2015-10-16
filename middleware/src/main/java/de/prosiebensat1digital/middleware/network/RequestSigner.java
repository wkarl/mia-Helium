package de.prosiebensat1digital.middleware.network;

import com.squareup.okhttp.Request;

import java.io.IOException;

import de.prosiebensat1digital.middleware.TokenRepository;
import de.prosiebensat1digital.middleware.util.KeyGenerator;
import okio.Buffer;

public class RequestSigner {
    public static final String HEADER_KEY = "key";

    private TokenRepository mTokenRepository;
    private long            mTimeDelta;
    private KeyGenerator    mKeyGenerator;
    
    public RequestSigner(String inSecret, String inSecretId, TokenRepository inTokenRepository) {
        mKeyGenerator = new KeyGenerator(inSecret, inSecretId);
        mTokenRepository = inTokenRepository;
        mTimeDelta = 0;
    }
    
    public Request signRequest(Request inRequest) throws IOException {
        String deviceToken = mTokenRepository.getDeviceToken();
        
        String body = null;
        if (inRequest.body() != null) {
            // Read body as json string
            Buffer buffer = new Buffer();
            inRequest.body().writeTo(buffer);
            body = buffer.readUtf8();
        }

        // Generate Key
        String key = mKeyGenerator
              .generateKey(deviceToken, inRequest.method(), inRequest.urlString(), body,
                    System.currentTimeMillis(), mTimeDelta);
        
        return inRequest.newBuilder()
                .removeHeader(HEADER_KEY)   // Disallow duplicate "key" headers
                .addHeader(HEADER_KEY, key)
                .build();
    }
    
    public void adjustLocalTime(long inServerTime) {
        mTimeDelta = inServerTime - System.currentTimeMillis();
    }
    
    public void resetDeviceToken() {
        mTokenRepository.resetDeviceToken();
    }
}
