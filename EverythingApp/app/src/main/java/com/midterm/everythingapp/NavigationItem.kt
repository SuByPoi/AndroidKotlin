package com.midterm.everythingapp

sealed class NavigationItem(var route:String,var icon : Int,var title:String){
    object Home: NavigationItem("home",R.drawable.ic_baseline_home_24,title = "Home")
    object Music: NavigationItem("Music",R.drawable.ic_baseline_music_note_24,title = "Music")
    object Movie: NavigationItem("Movie",R.drawable.ic_baseline_movie_24,title = "Movie")
    object Book: NavigationItem("Book",R.drawable.ic_baseline_menu_book_24,title = "Book")
    object Profile: NavigationItem("Profile",R.drawable.ic_baseline_person_24,title = "Profile")
}
