package de.sevenfactory.helium.todo;

import java.util.List;

import de.sevenfactory.helium.Helium;
import de.sevenfactory.helium.model.MiddlewareResult;
import retrofit.http.GET;

public class TodoRepository {
    private Api mApi;

    public TodoRepository(Helium helium) {
        mApi = helium.createApi(Api.class);
    }

    public List<TodoItem> getTodoItems() {
        return mApi.fetchTodoItems().getResponse();
    }

    public interface Api {
        @GET("/todo")
        MiddlewareResult<List<TodoItem>> fetchTodoItems();
    }
}
