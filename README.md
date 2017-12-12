## ActivityIndicatorView [![CircleCI](https://circleci.com/gh/WindSekirun/ActivityIndicatorView.svg?style=svg)](https://circleci.com/gh/WindSekirun/ActivityIndicatorView)

bring [UIActivityIndicatorView](https://developer.apple.com/documentation/uikit/uiactivityindicatorview) in Android Application.

### Usages
*rootProject/build.gradle*
```	
allprojects {
    repositories {
	    maven { url 'https://jitpack.io' }
    }
}
```

*app/build.gradle*
```
dependencies {
    implementation 'com.github.WindSekirun:ActivityIndicatorView:1.0.0'
}
```

### XML
```XML
<pyxis.uzuki.live.activityindicatorview.ActivityIndicatorView
        android:layout_width="37dp"
        android:layout_height="37dp"
        android:layout_gravity="center"
        app:autoPlay="true"
        app:clockwise="true"
        app:duration="60"
        app:indicatorColor="#000000"
        app:oneShot="false" />
```

### License 
```
Copyright 2017 WindSekirun (DongGil, Seo)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
