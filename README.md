# ProjectFinder -> Deprecated legacy branch discontinued support.
XML based Android Kotlin application to list github repositories. **Renewed Compose version** is in master Branch

[APK Link](https://drive.google.com/file/d/1mm8JB28nzSraKZ_Fm3KKdjM7dLMxtYSv/view?usp=sharing)

#### This project scope covers the substances below
* Kotlin 
* Single activity multiple fragment approach
* MVVM design parttern
* Graph Scoped ViewModels
* Databinding
* ROOM Databases
* Navigation Component
* Retrofit 2.9.0+
* Kotlin coroutines
* Suspend functions and LiveDataScopes
* RecylerView with ListAdapter and DiffUtil
* Facebook shimmer on listing page
* Glide for picture loading over internet

#### Possible future innovations after more functionality
* Live Data
* Single source of thruth strategy
* Dependency injection with HILT
* Repositories for View Models
* Search capability for recyclerList
* Paging for recyclerlist using JetPack Paging library
* Test cases both ui and viewModels

#### Application exposition
Main goal is creating a Github repository listing page and also an another page so as to view some details about it. 

Application initiates with a splash screen. After splash animation listing page has been shown. 

Listing page has a search bar in order to find resired repositories accourding to their owners. After repository owner input has been filled, with the submit button 
Details have been fetched via github web api. Considering to network delay, list loading animation has been provided by facebook shimmer library. 

Each row is placed with fused information from network and also local ROOM database. ROOM Database has the information which repository is marked as favorited by user.
Click action of each row navigates user to detail page of these repositories.

In Detail Page, user can add this repository to his/her favorite list in order to mark them with star icon. This star icon can be shown in detail page and also repository listing page.

#### ScreenShoots
<img src="https://github.com/AttilaAKINCI/repoListing/blob/legacy/app/appScreenShoots/device-2020-09-27-141948.png" width="200">   <img src="https://github.com/AttilaAKINCI/repoListing/blob/legacy/app/appScreenShoots/device-2020-09-27-142009.png" width="200">   <img src="https://github.com/AttilaAKINCI/repoListing/blob/legacy/app/appScreenShoots/device-2020-09-27-142147.png" width="200">   <img src="https://github.com/AttilaAKINCI/repoListing/blob/legacy/app/appScreenShoots/device-2020-09-27-142037.png" width="200">   <img src="https://github.com/AttilaAKINCI/repoListing/blob/legacy/app/appScreenShoots/device-2020-09-27-142207.png" width="200">   <img src="https://github.com/AttilaAKINCI/repoListing/blob/legacy/app/appScreenShoots/device-2020-09-27-142216.png" width="200">   <img src="https://github.com/AttilaAKINCI/repoListing/blob/legacy/app/appScreenShoots/device-2020-09-27-142224.png" width="200">   <img src="https://github.com/AttilaAKINCI/repoListing/blob/legacy/app/appScreenShoots/device-2020-09-27-142251.png" width="200">


