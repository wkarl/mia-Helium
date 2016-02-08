package de.sevenfactory.helium.mock;

import de.sevenfactory.helium.config.Config;

public class MockConfig implements Config {
    private String mEndpoint;

    public MockConfig(String endpoint) {
        mEndpoint = endpoint;
    }

    @Override
    public String getEndpoint() {
        return mEndpoint;
    }

    @Override
    public String getSecret() {
        return null;
    }

    @Override
    public String getSecretId() {
        return null;
    }
}
