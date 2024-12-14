package com.example.myslambook

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myslambook.databinding.ActivityUserDetailsBinding

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra("userName")
        val sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)

        // Fetch the corresponding user info using the name
        val firstName = sharedPreferences.getString("firstName_$userName", "")
        val lastName = sharedPreferences.getString("lastName_$userName", "")
        val nickname = sharedPreferences.getString("nickname_$userName", "")
        val age = sharedPreferences.getString("age_$userName", "")
        val birthdate = sharedPreferences.getString("birthdate_$userName", "")
        val gender = sharedPreferences.getString("gender_$userName", "")
        val address = sharedPreferences.getString("address_$userName", "")
        val school = sharedPreferences.getString("school_$userName", "")
        val zodiac = sharedPreferences.getString("zodiac_$userName", "")
        val favoriteSongs = sharedPreferences.getString("favoriteSongs_$userName", "")
        val favoriteArtist = sharedPreferences.getString("favoriteArtist_$userName", "")
        val favoriteMovies = sharedPreferences.getString("favoriteMovies_$userName", "")
        val diaryEntry = sharedPreferences.getString("diaryEntry_$userName", "")

        // Display the full details
        binding.textViewDetails.text = """
            Name: $firstName $lastName
            Nickname: $nickname
            Age: $age
            Birthdate: $birthdate
            Gender: $gender
            Address: $address
            School: $school
            Zodiac: $zodiac
            Favorite Songs: $favoriteSongs
    Favorite Artist: $favoriteArtist
    Favorite Movies: $favoriteMovies
    Diary Entry: $diaryEntry
        """.trimIndent()
    }
}

