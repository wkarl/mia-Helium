package de.sevenfactory.helium.mock;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import java.io.IOException;

public class MockServer {
    private MockWebServer mServer;

    public MockServer() {
        mServer = new MockWebServer();
    }

    public MockServer enqueueAcceptKey() {
        enqueue("{\"status\":200,\"response\":{\"id\":\"!ThisIsYour32CharacterAccessKey!\"}}");
        return this;
    }

    public MockServer enqueueRejectKey() {
        mServer.enqueue(new MockResponse()
                .setBody("{\"status\":401,\"errors\":[{\"code\":\"DeviceIdInvalid\",\"msg\":\"Your id is empty or less than 32 characters.\"}]}")
                .setResponseCode(401));
        return this;
    }

    public MockServer enqueueRejectTimestamp() {
        mServer.enqueue(new MockResponse()
                .setBody("{\"status\":401,\"errors\":[{\"code\":\"KeyExpired\",\"msg\":\"Your timestamp is invalid.\"}]}")
                .setHeader("timestamp", System.currentTimeMillis())
                .setResponseCode(401));
        return this;
    }

    public MockServer enqueue(String response) {
        mServer.enqueue(new MockResponse().setBody(response));
        return this;
    }

    public MockServer start() throws IOException {
        mServer.start();
        return this;
    }

    public String getUrl(String path) {
        return mServer.getUrl(path).toString();
    }

    public void shutdown() throws IOException {
        mServer.shutdown();
    }
}
