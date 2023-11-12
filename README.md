# ProjectFinder
Android compose application to list github repositories.

[APK Link (https://drive.google.com/file/d/1MoFGXj-Hkvbjd5_ZXQAkkwessuwv5rOg/view?usp=sharing)](https://drive.google.com/file/d/1MoFGXj-Hkvbjd5_ZXQAkkwessuwv5rOg/view?usp=sharing)

## How to run
In order to run project in your local be aware below points ->
* Developed by Android Studio Hedgehog | 2023.1.1 RC 3 Build #AI-231.9392.1.2311.11047128, built on November 3, 2023
* checkout master branch
* add *SERVICE_ENDPOINT_BASE_URL=https://api.github.com* to your local.properties file.
  

## App Video

     Validation Error      Normal Run      Remember Favorite        No Data         Service Error       

<img src="https://github.com/AttilaAKINCI/ProjectFinder/assets/21987335/e8a3746c-35e9-4487-a3a1-9219df3ade6e" width="160"/> <img 
src="https://github.com/AttilaAKINCI/ProjectFinder/assets/21987335/53366e5b-0c23-4093-89e9-a0010d2428c9" width="160"/>  <img 
src="https://github.com/AttilaAKINCI/ProjectFinder/assets/21987335/ffa04e5b-2f06-478e-bc62-5ed509f6a2f8" width="160"/>  <img 
src="https://github.com/AttilaAKINCI/ProjectFinder/assets/21987335/14c531fb-4c77-4f96-8a15-a42297075b46" width="160"/>  <img 
src="https://github.com/AttilaAKINCI/ProjectFinder/assets/21987335/8d2bd069-6b1d-4d9e-a956-7517ea8cd569" width="160"/>


## 3rd party lib. usages & Tech Specs
* Kotlin
* [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
* [Kotlin DSL](https://developer.android.com/build/migrate-to-kotlin-dsl)
* Patterns
    - MVVM
    - Clean Architecture
    - Repository
* [JetPack Compose](https://developer.android.com/jetpack/compose?gclid=Cj0KCQiAjMKqBhCgARIsAPDgWlyVg8bZaasX_bdQfYrAXsuDQ6vD-2SmFcTv34Fb-jLQxgGqPD7UxKgaAso5EALw_wcB&gclsrc=aw.ds)
* [Edge to Edge UI design](https://developer.android.com/jetpack/compose/layouts/insets)
* Dark/Light UI Mode 
* [Compose Destinations](https://github.com/raamcosta/compose-destinations) / [Documentation](https://composedestinations.rafaelcosta.xyz/)
* [Room Database](https://developer.android.com/jetpack/androidx/releases/room)
    - Suspend response handling
    - Reactive Flow response handling
* [Ktor Client](https://ktor.io/docs/client-supported-platforms.html)
* [Coil](https://github.com/coil-kt/coil)
    - Asynch image loading
    - Gif play support
* [Timber Client logging](https://github.com/JakeWharton/timber)
* [Dependency Injection (HILT)](https://developer.android.com/training/dependency-injection/hilt-android)
* Unit testing
* [Turbine](https://github.com/cashapp/turbine)
* [MockK](https://mockk.io/)
* Junit5


#### UI Flow
1- App starts with native splash screen then, user is navigated to project list screen

2- In project list screen, user can enter usernames to "Repository Owner" input field and search corresponding user repositories.

3- If repository liked before, it will be remembered and user can see like icon on project list.

4- Row clicks navigates user to project detail screen to show more details about it. 

5- User can like/favorite repositories in project detail screen


#### ScreenShots
Light Mode:

<img src="https://github.com/AttilaAKINCI/ProjectFinder/blob/compose/images/1-light.png" width="115">   <img
src="https://github.com/AttilaAKINCI/ProjectFinder/blob/compose/images/2-light.png" width="115">   <img
src="https://github.com/AttilaAKINCI/ProjectFinder/blob/compose/images/3-light.png" width="115">   <img
src="https://github.com/AttilaAKINCI/ProjectFinder/blob/compose/images/4-light.png" width="115">   <img
src="https://github.com/AttilaAKINCI/ProjectFinder/blob/compose/images/5-light.png" width="115">   <img
src="https://github.com/AttilaAKINCI/ProjectFinder/blob/compose/images/6-light.png" width="115">   <img
src="https://github.com/AttilaAKINCI/ProjectFinder/blob/compose/images/7-light.png" width="115">

Dark Mode:

<img src="https://github.com/AttilaAKINCI/ProjectFinder/blob/compose/images/1-dark.png" width="115">   <img
src="https://github.com/AttilaAKINCI/ProjectFinder/blob/compose/images/2-dark.png" width="115">   <img
src="https://github.com/AttilaAKINCI/ProjectFinder/blob/compose/images/3-dark.png" width="115">   <img
src="https://github.com/AttilaAKINCI/ProjectFinder/blob/compose/images/4-dark.png" width="115">   <img
src="https://github.com/AttilaAKINCI/ProjectFinder/blob/compose/images/5-dark.png" width="115">   <img
src="https://github.com/AttilaAKINCI/ProjectFinder/blob/compose/images/6-dark.png" width="115">   <img
src="https://github.com/AttilaAKINCI/ProjectFinder/blob/compose/images/7-dark.png" width="115">

# License

The code is licensed as:

```
Copyright 2021 Attila Akıncı

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

