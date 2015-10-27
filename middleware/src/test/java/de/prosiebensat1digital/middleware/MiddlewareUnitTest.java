package de.prosiebensat1digital.middleware;

import junit.framework.Assert;

import org.junit.Test;

import de.prosiebensat1digital.middleware.util.KeyGenerator;

public class MiddlewareUnitTest extends Assert {
    @Test
    public void signRequest() {
        KeyGenerator generator = new KeyGenerator("secret", "secretId");
        String key =
                generator.generateKey("token", "get", "url", "body", 1445359906067l, 200l);
        assertEquals(key,
                "tokensecretId1445359906263e4efe16b259158610273e6f5abc28a5a78c2ae22a00b0f5b9adf976a30672");
    }
}