package de.prosiebensat1digital.middleware.network;

import android.text.format.DateUtils;

import com.squareup.okhttp.Request;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import de.prosiebensat1digital.middleware.TokenRepository;
import okio.Buffer;

public class RequestSigner {
    public static final String HEADER_KEY = "key";
    
    private String          mSecret;
    private String          mSecretId;
    private TokenRepository mTokenRepository;
    private long            mTimeDelta;
    
    public RequestSigner(String inSecret, String inSecretId, TokenRepository inTokenRepository) {
        mSecret = inSecret;
        mSecretId = inSecretId;
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
        String key = generateKey(deviceToken, inRequest.method(), inRequest.urlString(), body);
        
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
    
    private String generateKey(String inDeviceToken, String inRequestMethod, String inUrl,
                               String inBody) {
        long timestamp = (System.currentTimeMillis() + mTimeDelta) / DateUtils.SECOND_IN_MILLIS;
        
        String encodedBody = inBody != null ? sha256encode(inBody) : "";
        String signature   =
                inDeviceToken + mSecret + timestamp + inRequestMethod + inUrl + encodedBody;
        
        return inDeviceToken + mSecretId + timestamp + sha256encode(signature);
    }
    
    private String sha256encode(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.toLowerCase(Locale.ENGLISH).getBytes());
            byte[] digest = md.digest();
            return String.format("%0" + (digest.length * 2) + "X", new BigInteger(1, digest))
                         .toLowerCase(Locale.ENGLISH);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
