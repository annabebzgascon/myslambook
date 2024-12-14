package com.example.myslambook

data class userinfo(
    val name: String,
    val lastName: String,
    val nickname: String,
    val age: String,
    val birthdate: String,
    val gender: String,
    val address: String,
    val school: String,
    val zodiac: String

)
data class song(
        val songtitle: String = "",
        val artist: String = ""

        )
data class movie(
    val title: String = "",
    val moviegenre: String = ""
)
