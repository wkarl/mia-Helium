package de.prosiebensat1digital.middleware.util;

import android.text.format.DateUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * Created by wolfgang on 10/20/15.
 */
public class KeyGenerator {
   private String          mSecret;
   private String          mSecretId;

   public KeyGenerator(final String inSecret, final String inSecretId) {
      mSecret = inSecret;
      mSecretId = inSecretId;
   }

   public String generateKey(final String inDeviceToken, final String inRequestMethod, final String inUrl,
         final String inBody, final long inLocalTime, final long inTimeDelta) {
         long timestamp = (inLocalTime + inTimeDelta) / DateUtils.SECOND_IN_MILLIS;
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
