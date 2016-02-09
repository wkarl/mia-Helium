[![API](https://img.shields.io/badge/API-10%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=10) [![Build Status](https://travis-ci.org/7factory/mia-Helium.svg?branch=master)](https://travis-ci.org/7factory/mia-Helium) [![Release](https://jitpack.io/v/7factory/mia-Helium.svg)](https://jitpack.io/#7factory/mia-Helium)
[![License](http://img.shields.io/:license-mit-brightgreen.svg?style=flat)](https://raw.githubusercontent.com/7factory/mia-Helium/master/LICENSE)

![logo](https://github.com/7factory/mia-Helium/raw/gh-pages/images/helium_400px.png?raw=true "Helium")

A Java library for accessing servers based on [mia-js](https://github.com/7factory/mia-js).

Helium automatically performs the necessary device registration and session key handling required by mia-js to perform authenticated requests.

Despite having been developed with Android in mind, the Helium should work properly on pure Java environments. The included unit tests do not depend on any way on Android-specific code.

## Using Gradle ##

Add the following lines to your root build.gradle:

``` gradle
allprojects {
    repositories {
        [...]
        maven { url "https://jitpack.io" }
    }
}
```

Then reference the library from your module's build.gradle:

``` gradle
dependencies {
    [...]
    compile 'com.github.7factory:mia-helium:x.y'
}
```

## Integration ##

Helium is based on the popular [Retrofit](http://square.github.io/retrofit/) library, making it easy to implement your API through the use of Java Interfaces.

For example, a simple to-do service including a method to fetch to-do items:
``` java
public interface TodoApi {
    @GET("/todo")
    MiddlewareResult<List<TodoItem>> fetchTodoItems();
}
```

In order to instantiate the API, you will first need is a configured Helium instance:
``` java
Config config     = new ServiceConfig();
DeviceStore store = new AndroidDeviceStore(context);

Helium helim = new Helium(config, store);

TodoApi todoApi = helium.createApi(Api.class);
```

The `Config` interface needs to be implemented by you and contains your service-specific endpoint URL and secrets.

The `DeviceStore` interface allows you to define how Helium generates the `DeviceInfo` objects required for authentication, as well as how the resulting session tokens are stored.

Helium ships with `AndroidDeviceStore`, an implementation of `DeviceStore` based on the Android `PackageManager` and `SharedPreferences` classes. A `MockDeviceStore` that does not depend on any Android classes is also available for unit testing.

A simple integration example is in method `testGetTodoItems()` in our [unit tests](https://github.com/7factory/mia-Helium/blob/master/helium/src/test/java/de/sevenfactory/helium/UnitTests.java).
