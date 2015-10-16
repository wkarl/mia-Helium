package de.prosiebensat1digital.middleware;

import com.squareup.okhttp.OkHttpClient;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.prosiebensat1digital.middleware.config.Config;
import de.prosiebensat1digital.middleware.mock.MockDeviceStore;
import de.prosiebensat1digital.middleware.model.MiddlewareResult;
import de.prosiebensat1digital.middleware.network.JsonConverter;
import de.prosiebensat1digital.middleware.network.KeyInterceptor;
import de.prosiebensat1digital.middleware.network.RequestAuthenticator;
import de.prosiebensat1digital.middleware.network.RequestSigner;
import de.prosiebensat1digital.middleware.store.DeviceStore;
import de.prosiebensat1digital.middleware.util.KeyGenerator;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.http.GET;

public class MiddlewareTest extends Assert {
    public static final long   MAX_TIMESTAMP_DELTA = 5 * 60 * 1000; // milliseconds
    public static final Config ENDPOINT            = new Config() {
        final String API_PREFIX = "/7tv/v2";

        public String getEndpoint() {
            return "https://mobileapi-test.prosiebensat1.com" + API_PREFIX;
        }

        public String getSecret() {
            return "056812ed6d6a4ed810867009cc4c07c1";
        }

        public String getSecretId() {
            return "2c49d9dd597c784c250d486577b7052e";
        }
    };

    private TokenRepository mTokenRepository;

    @Before
    public void setUp() {
        DeviceStore store = new MockDeviceStore();
        mTokenRepository = new TokenRepository(ENDPOINT, store);
    }

    @Test
    public void registerDevice() {
        String token = mTokenRepository.getDeviceToken();
        assertEquals(token.length(), 32);
    }

    @Test
    public void validateRequestTimestamp() {
        CustomRequestSigner requestSigner =
                new CustomRequestSigner(ENDPOINT.getSecret(), ENDPOINT.getSecretId(), mTokenRepository);

        // Test #1: request settings without any manipulation

        Client      client  = getClient(requestSigner);
        RestAdapter adapter = createRestAdapter(client, ENDPOINT);

        Object result = adapter.create(SettingsApi.class).getSettings().getResponse();
        assertNotNull(result);
        assertEquals(requestSigner.getAdjustCount(), 0);

        // Test #2: manipulate time delta and test adjustment logic

        // Create RequestSigner with initial fake timestamp delta
        requestSigner.adjustLocalTime(System.currentTimeMillis() + MAX_TIMESTAMP_DELTA * 2);
        requestSigner.resetAdjustCount();

        // Request settings with incorrect time delta
        result = adapter.create(SettingsApi.class).getSettings().getResponse();
        assertNotNull(result);
        assertEquals(requestSigner.getAdjustCount(), 1);

        // Request settings a second time to make sure the time delta was adjusted correctly
        result = adapter.create(SettingsApi.class).getSettings().getResponse();
        assertNotNull(result);
        assertEquals(requestSigner.getAdjustCount(), 1);
    }

    @Test
    public void signRequest() {
        KeyGenerator generator = new KeyGenerator("secret", "secretId");
        String key =
                generator.generateKey("token", "get", "url", "body", 1445359906067l, 200l);
        assertEquals(key,
                "tokensecretId1445359906263e4efe16b259158610273e6f5abc28a5a78c2ae22a00b0f5b9adf976a30672");
    }

    private Client getClient(RequestSigner inRequestSigner) {
        KeyInterceptor       interceptor   = new KeyInterceptor(inRequestSigner);
        RequestAuthenticator authenticator = new RequestAuthenticator(inRequestSigner);

        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(interceptor);
        client.interceptors().add(authenticator);
        client.setAuthenticator(authenticator);

        return new OkClient(client);
    }

    private RestAdapter createRestAdapter(final Client inClient, final Config inConfig) {
        return new RestAdapter.Builder()
                .setClient(inClient)
                .setEndpoint(inConfig.getEndpoint())
                .setConverter(new JsonConverter())
                .setLogLevel(Middleware.LOG_LEVEL)
                .build();
    }

    private static class CustomRequestSigner extends RequestSigner {
        private int mAdjustCount = 0;

        public CustomRequestSigner(final String inSecret, final String inSecretId,
                                   final TokenRepository inTokenRepository) {
            super(inSecret, inSecretId, inTokenRepository);
        }

        @Override
        public void adjustLocalTime(final long inServerTime) {
            super.adjustLocalTime(inServerTime);
            mAdjustCount++;
        }

        public void resetAdjustCount() {
            mAdjustCount = 0;
        }

        public int getAdjustCount() {
            return mAdjustCount;
        }
    }

    private interface SettingsApi {
        @GET("/settings")
        MiddlewareResult<Object> getSettings();
    }
}