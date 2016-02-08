package de.sevenfactory.helium;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import de.sevenfactory.helium.mock.MockConfig;
import de.sevenfactory.helium.mock.MockDeviceStore;
import de.sevenfactory.helium.mock.MockServer;
import de.sevenfactory.helium.todo.TodoItem;
import de.sevenfactory.helium.todo.TodoRepository;
import de.sevenfactory.helium.util.KeyGenerator;

public class UnitTests extends Assert {
    @Test
    public void testKeyGeneration() {
        KeyGenerator generator = new KeyGenerator("secret", "secretId");
        String key =
                generator.generateKey("token", "get", "url", "body", 1445359906067L, 200L);
        assertEquals(key,
                "tokensecretId1445359906263e4efe16b259158610273e6f5abc28a5a78c2ae22a00b0f5b9adf976a30672");
    }

    @Test
    public void testDeviceRegistration() throws IOException {
        MockServer server = new MockServer()
                .enqueueAcceptKey()
                .start();
        MockDeviceStore store = new MockDeviceStore();

        TokenRepository tokenRepository = new TokenRepository(new MockConfig(server.getUrl("/demo-auth/v1")), store);

        String token = tokenRepository.getDeviceToken();
        assertEquals(token.length(), 32);

        server.shutdown();
    }

    @Test
    public void testRenewDeviceToken() throws IOException {
        MockServer server = new MockServer()
                .enqueueRejectKey()
                .enqueueAcceptKey()
                .enqueue("{\"status\":200,\"response\":[{\"_id\":\"56b86b020fbfed811acf32df\",\"name\":\"Eat some icecream\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.638Z\",\"created\":\"2016-02-08T10:16:34.638Z\"},{\"_id\":\"56b86b020fbfed811acf32e0\",\"name\":\"Go out for lunch\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.639Z\",\"created\":\"2016-02-08T10:16:34.639Z\"},{\"_id\":\"56b86b020fbfed811acf32e1\",\"name\":\"Get some sleep\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.640Z\",\"created\":\"2016-02-08T10:16:34.640Z\"},{\"_id\":\"56b86b020fbfed811acf32e2\",\"name\":\"Get this demo done\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.640Z\",\"created\":\"2016-02-08T10:16:34.640Z\"},{\"_id\":\"56b86b020fbfed811acf32e3\",\"name\":\"Create own project\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.641Z\",\"created\":\"2016-02-08T10:16:34.641Z\"}]}")
                .start();
        MockDeviceStore store = new MockDeviceStore("InvalidKey");

        TodoRepository repository = new TodoRepository(new Helium(new MockConfig(server.getUrl("/demo-auth/v1")), store));

        List<TodoItem> items = repository.getTodoItems();

        server.shutdown();
    }

    @Test
    public void testTimestampCorrection() throws IOException {
        MockServer server = new MockServer()
                .enqueueRejectTimestamp()
                .enqueue("{\"status\":200,\"response\":[{\"_id\":\"56b86b020fbfed811acf32df\",\"name\":\"Eat some icecream\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.638Z\",\"created\":\"2016-02-08T10:16:34.638Z\"},{\"_id\":\"56b86b020fbfed811acf32e0\",\"name\":\"Go out for lunch\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.639Z\",\"created\":\"2016-02-08T10:16:34.639Z\"},{\"_id\":\"56b86b020fbfed811acf32e1\",\"name\":\"Get some sleep\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.640Z\",\"created\":\"2016-02-08T10:16:34.640Z\"},{\"_id\":\"56b86b020fbfed811acf32e2\",\"name\":\"Get this demo done\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.640Z\",\"created\":\"2016-02-08T10:16:34.640Z\"},{\"_id\":\"56b86b020fbfed811acf32e3\",\"name\":\"Create own project\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.641Z\",\"created\":\"2016-02-08T10:16:34.641Z\"}]}")
                .start();
        MockDeviceStore store = new MockDeviceStore("!ThisIsYour32CharacterAccessKey!");

        TodoRepository repository = new TodoRepository(new Helium(new MockConfig(server.getUrl("/demo-auth/v1")), store));

        List<TodoItem> items = repository.getTodoItems();

        server.shutdown();
    }

    @Test
    public void testGetTodoItems() throws IOException {
        MockServer server = new MockServer()
                .enqueueAcceptKey()
                .enqueue("{\"status\":200,\"response\":[{\"_id\":\"56b86b020fbfed811acf32df\",\"name\":\"Eat some icecream\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.638Z\",\"created\":\"2016-02-08T10:16:34.638Z\"},{\"_id\":\"56b86b020fbfed811acf32e0\",\"name\":\"Go out for lunch\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.639Z\",\"created\":\"2016-02-08T10:16:34.639Z\"},{\"_id\":\"56b86b020fbfed811acf32e1\",\"name\":\"Get some sleep\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.640Z\",\"created\":\"2016-02-08T10:16:34.640Z\"},{\"_id\":\"56b86b020fbfed811acf32e2\",\"name\":\"Get this demo done\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.640Z\",\"created\":\"2016-02-08T10:16:34.640Z\"},{\"_id\":\"56b86b020fbfed811acf32e3\",\"name\":\"Create own project\",\"status\":\"unchecked\",\"group\":\"demo\",\"lastModified\":\"2016-02-08T10:16:34.641Z\",\"created\":\"2016-02-08T10:16:34.641Z\"}]}")
                .start();
        MockDeviceStore store = new MockDeviceStore();

        TodoRepository repository = new TodoRepository(new Helium(new MockConfig(server.getUrl("/demo-auth/v1")), store));

        List<TodoItem> items = repository.getTodoItems();

        assertNotNull(items);
        assertEquals(items.size(), 5);

        TodoItem item = items.get(0);
        assertEquals(item.getId(), "56b86b020fbfed811acf32df");
        assertEquals(item.getName(), "Eat some icecream");
        assertEquals(item.getStatus(), false);

        server.shutdown();
    }
}