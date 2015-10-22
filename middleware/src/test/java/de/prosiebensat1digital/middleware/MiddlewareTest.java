package de.prosiebensat1digital.middleware;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.prosiebensat1digital.middleware.config.Config;
import de.prosiebensat1digital.middleware.mock.MockDeviceStore;
import de.prosiebensat1digital.middleware.model.MiddlewareResult;
import de.prosiebensat1digital.middleware.network.RequestSigner;
import de.prosiebensat1digital.middleware.util.KeyGenerator;
import retrofit.http.GET;

public class MiddlewareTest extends Assert {
    public static final long   MAX_TIMESTAMP_DELTA = 5 * 60 * 1000; // milliseconds
    public static final Config ENDPOINT            = new Config() {
        final String API_PREFIX = "/7tv/v2";

        public String getEndpoint() {
            return "https://mobileapi.prosiebensat1.com" + API_PREFIX;
        }
        
        public String getSecret() {
            return "056812ed6d6a4ed810867009cc4c07c1";
        }

        public String getSecretId() {
            return "2c49d9dd597c784c250d486577b7052e";
        }
    };

    private TokenRepository mTokenRepository;
    private MockDeviceStore mDeviceStore;

    @Before
    public void setUp() {
        mDeviceStore = new MockDeviceStore();
        mTokenRepository = new TokenRepository(ENDPOINT, mDeviceStore);
    }

    @Test
    public void registerDevice() {
        String token = mTokenRepository.getDeviceToken();
        assertEquals(token.length(), 32);
    }

    @Test
    public void validateRequestTimestamp() {
        RequestSigner requestSigner =
                new RequestSigner(ENDPOINT.getSecret(), ENDPOINT.getSecretId());
        Middleware        middleware = new Middleware(ENDPOINT, requestSigner, mDeviceStore);
        final SettingsApi api        = middleware.createApi(SettingsApi.class);

        // Test #1: request settings without any manipulation

        Object result = api.getSettings().getResponse();
        assertNotNull(result);

        // Test #2: manipulate time delta and test adjustment logic

        // Create RequestSigner with initial fake timestamp delta
        requestSigner.adjustLocalTime(System.currentTimeMillis() + MAX_TIMESTAMP_DELTA * 2);

        // Request settings with incorrect time delta
        result = api.getSettings().getResponse();
        assertNotNull(result);
    }

    @Test
    public void signRequest() {
        KeyGenerator generator = new KeyGenerator("secret", "secretId");
        String key =
                generator.generateKey("token", "get", "url", "body", 1445359906067l, 200l);
        assertEquals(key,
                "tokensecretId1445359906263e4efe16b259158610273e6f5abc28a5a78c2ae22a00b0f5b9adf976a30672");
    }

    private interface SettingsApi {
        @GET("/settings")
        MiddlewareResult<Object> getSettings();
    }
}