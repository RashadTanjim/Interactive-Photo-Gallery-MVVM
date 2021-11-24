# Interactive Gallery App

#### A simple image gallery that fetches data from **Lorem Picsum** API and builds with MVVM and with a touch of Clean Architecture.


[Lorem Picsum](https://picsum.photos/): Lorem Picsum provides random or specific uploaded images as placeholders. Developers need to specify the desired image size (width & height) at the end of the request URL. Grayscale and blur effects can be applied to any image.

[MVVM Architecture](https://developer.android.com/jetpack/guide): This guide encompasses best practices and recommended architecture for building robust, production-quality apps.

[Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/): Used Conventional commit messages for committing every activity.

## Features
1. Use https://picsum.photos to get a list of photos
2. The user can tap the photo
3. The user can see a full-screen view of the photo
4. The user has an infinite scroll on the gallery screen
5. The app’s a flavor/scheme can be changed easily so that changing from development API to production API becomes trivial
6. Cache images
7. Cache API response
8. Allow saving photos in JPEG format to the local gallery
9. Allow sharing the photo
10. Day-Night mode sync with Calendar Time
11. Code In-line and Multi-line documents add
12. Git Commits describes using Conventional Commit Message Guideline

## Architectural Pattern
* Built with Modern Android Development (MAD) practices
* Utilization of clean architecture and SOLID design principles
* Utilized Usecase, Repository pattern for data
* Includes unit tests for Use cases, Repository, ViewModels, API response
* Dependency Injection using Dragger-Hilt
* Util class to reduce common code duplication
* Full app built with Kotlin 
* Jetpack Components used

<img src="https://github.com/RashadTanjim/Interactive-Photo-Gallery-MVVM/blob/main/Documentation/app-preview.png" width="800px" />

## Branch Description
1. Main/ Master branch is set for main production branch
2. Development Branch is set for all development face
3. Room-implementation Branch is for implementing local database
4. api-implementation Branch is for API integration
5. unit-test-imp Branch is for testing (not fully done)
6. ci-cd-pipeline Branch is for creating automation (Plan implement later) 

## Download Demo on Android
Download the [APK file](https://github.com/RashadTanjim/Interactive-Photo-Gallery-MVVM/blob/main/Documentation/app-release-0.1.0.apk?raw=true)

## Libraries Used
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and thread handling 
- [Room](https://developer.android.com/jetpack/androidx/releases/room) - FThe Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
  - [Hilt](https://dagger.dev/hilt) - An easier way to incorporate Dagger DI into Android apps. 
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Firebase](https://firebase.google.com/) - Firebase helps build and run successful apps.
- [Zoomage](http://jsibbold.github.io/zoomage/) - A simple pinch-to-zoom ImageView library for Android with an emphasis on a smooth and natural feel.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Lottie](https://github.com/airbnb/lottie-android/) - Lottie is a mobile library for Android and iOS that parses Adobe After Effects animations exported as JSON with Bodymovin and renders them natively on mobile!
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - For writing Gradle build scripts using Kotlin.
- [MockK](https://mockk.io) - For Mocking and Unit Testing.
- [Glide](https://github.com/bumptech/glide/) - Glide is a fast and efficient open-source media management and image loading framework for Android that wraps media decoding, memory, and disk caching, and resource pooling into a simple and easy to use interface.

<img src="https://github.com/RashadTanjim/Interactive-Photo-Gallery-MVVM/blob/main/Documentation/Jetpack.png" width="800px" height="400px" />


#### Developed By Md Rashad Tanjim - website: https://rashadtanjim.info/

## License

```
   Copyright (C) 2021 MD RASHAD TANJIM

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
