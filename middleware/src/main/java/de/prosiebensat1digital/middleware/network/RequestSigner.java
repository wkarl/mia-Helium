package de.prosiebensat1digital.middleware.network;

import com.squareup.okhttp.Request;

import java.io.IOException;

import de.prosiebensat1digital.middleware.util.KeyGenerator;
import okio.Buffer;

public class RequestSigner {
    public static final String HEADER_KEY = "key";
    
    private long         mTimeDelta;
    private KeyGenerator mKeyGenerator;
    
    public RequestSigner(String secret, String secretId) {
        mKeyGenerator = new KeyGenerator(secret, secretId);
        mTimeDelta = 0;
    }
    
    public Request signRequest(Request request, String deviceToken) throws IOException {
        String body = null;
        if (request.body() != null) {
            // Read body as json string
            Buffer buffer = new Buffer();
            request.body().writeTo(buffer);
            body = buffer.readUtf8();
        }
        
        // Generate Key
        String key = mKeyGenerator
                .generateKey(deviceToken, request.method(), request.urlString(), body,
                        System.currentTimeMillis(), mTimeDelta);
        
        return request.newBuilder()
                .removeHeader(HEADER_KEY)   // Disallow duplicate "key" headers
                .addHeader(HEADER_KEY, key)
                .build();
    }
    
    public void adjustLocalTime(long serverTime) {
        mTimeDelta = serverTime - System.currentTimeMillis();
    }
}
