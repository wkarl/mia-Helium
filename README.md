[![API](https://img.shields.io/badge/API-10%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=10) [![Build Status](https://travis-ci.org/7factory/mia-Helium.svg?branch=master)](https://travis-ci.org/7factory/mia-Helium) [![Release](https://jitpack.io/v/7factory/mia-Helium.svg)](https://jitpack.io/#7factory/mia-Helium)
[![License](http://img.shields.io/:license-mit-brightgreen.svg?style=flat)](https://raw.githubusercontent.com/7factory/mia-Helium/master/LICENSE)

![logo](https://github.com/7factory/mia-Argon/raw/gh-pages/images/helium_400px.png?raw=true "Helium")

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

