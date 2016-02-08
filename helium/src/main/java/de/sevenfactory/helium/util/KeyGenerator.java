package de.sevenfactory.helium.util;

import android.text.format.DateUtils;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class KeyGenerator {
    private String mSecret;
    private String mSecretId;
    
    public KeyGenerator(final String secret, final String secretId) {
        mSecret = secret;
        mSecretId = secretId;
    }
    
    public String generateKey(String deviceToken, String requestMethod, String url, String body,
                              long localTime, long timeDelta) {
        long   timestamp   = (localTime + timeDelta) / DateUtils.SECOND_IN_MILLIS;
        String encodedBody = body != null ? sha256encode(body) : "";
        String signature   = deviceToken + mSecret + timestamp + requestMethod + url + encodedBody;
        
        return deviceToken + mSecretId + timestamp + sha256encode(signature);
    }
    
    private String sha256encode(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.toLowerCase(Locale.ENGLISH).getBytes(Charset.defaultCharset()));
            byte[] digest = md.digest();
            return String.format("%0" + (digest.length * 2) + "X", new BigInteger(1, digest))
                    .toLowerCase(Locale.ENGLISH);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
