# The New York Times Books

[The link to download the .apk file of the app for testing purposes](https://drive.google.com/drive/folders/1gwQXfTSa12lL8deDvqLArWuZDN8YEBJ1?usp=drive_link)

![screen_record]

<br>

### Application Description

Test task to create an application to display information about The New York Times Bestseller lists
with book reviews. Book reviews and book categories are downloaded from
the [New York Times Developer Network](https://developer.nytimes.com/) Web API

**When installing the application you will see:**

- a list with book categories is displayed on the first screen
- the second screen displays all books from the selected category
- when you click on the "Buy" button, a dialog window opens to select the desired store from the
  proposed list where you can purchase this book
- when you select a specific store, a built-in browser window opens and you go to the selected store
  for purchase

In order to update application content from the Internet, you can use the “swipe down” gesture. By
default, data is taken from the internal "Room" database.

The application contains an Internet connection check and corresponding notifications about the
connection status

<br>

---

<br>

### Libraries and features that were used in the project

- [Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material 3 Design](https://developer.android.com/jetpack/androidx/releases/compose-material3)
- Landscape/Portrait screen orientation
- Animated screen transitions
- Dark / Light modes
- [Multi-module project](https://developer.android.com/topic/modularization)
- [Jetpack compose default navigation](https://developer.android.com/jetpack/compose/navigation)
- MVVM architecture
- [Dagger2 for DI with ksp](https://dagger.dev/)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [Retrofit2](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/)
- [Room](https://developer.android.com/jetpack/androidx/releases/room) (1 to n relationship)
- [Swipe Refresh](https://google.github.io/accompanist/swiperefresh/)
- [Coil](https://coil-kt.github.io/coil/)
- The Internet connectivity check
- Base viewModel
- StateFlow
- Adaptive layout depending on screen size
- WebView in the application to open a bookstore
