# TvShowsList

## DESCRIPTION

Sample app. Was done as a test assignment for a company after job interview.

## ASSIGNMENT
#### TV Shows list

Create a view that contains an infinite scroll list with the most popular tv shows.
 Use the following endpoint: Most Popular Tv Shows
(https://developers.themoviedb.org/3/tv/get-popular-tv-shows)
 Each item of the list should contain at least an image, the tv show title and the vote average fields.
The list should paginate.
#### Show Detail
When a show is clicked on the list, create a show detail view that shows extra info for the show in a
separate screen.
This should contain at least: A big hero image, the title, the overview... (you can get that info
exploring the provided api).
The user should be able to navigate between similar tv shows by swiping horizontally or by adding
a section to the detail view with a horizontal recycler view that includes the similar shows.
Use this endpoint Similar shows (https://developers.themoviedb.org/3/tv/get-similar-tv-shows)
#### Bonus
Animations / transitions
Nice UI
#### Rules
We expect that you make production ready code
We prefer to get the code in a git repository
We enjoy clean git histories

### ARCHITECTURE

App is made followind Uncle Bob's ideas about clean architecture


Article: https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html

Video: https://www.youtube.com/watch?v=Nsjsiz2A9mg

Article: https://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/

### TESTS
Only instrumentation tests were written as it makes most of the fun

### LIBRARIES
Dagger, Retrofit, RxJava, Picasso

### BUGS
There are some IU issues to be fixed
