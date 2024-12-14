package com.example.myslambook

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myslambook.databinding.ActivityForm1Binding
import java.util.Calendar

class form1 : AppCompatActivity() {

    private lateinit var binding: ActivityForm1Binding
    private val songList = mutableListOf<song>()
    private val movieList = mutableListOf<movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForm1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val headerTextView = findViewById<TextView>(R.id.headertext)

        // Get the name from the Intent
        val name = intent.getStringExtra("USER_NAME")

        // Update the TextView with the user's name
        if (!name.isNullOrEmpty()) {
            headerTextView.text = "Hello, $name! Please fill up all the information below"
        }

        binding.birthdate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}" // Format as DD/MM/YYYY
                    binding.birthdate.setText(formattedDate)
                },
                year, month, day
            )

            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
        }



        binding.btninfoSubmit.setOnClickListener {
            val firstName = binding.FirstName.text.toString()
            val lastName = binding.LastName.text.toString()
            val nickname = binding.Nickname.text.toString()
            val age = binding.age.text.toString()
            val birthdate = binding.birthdate.text.toString()
            val gender = binding.gender.text.toString()
            val address = binding.address.text.toString()
            val school = binding.school.text.toString()
            val zodiac = binding.zodiac.text.toString()
            val favoriteSongs = binding.edtFavoriteSongs.text.toString()
            val favoriteArtist = binding.favoriteArtist.text.toString()
            val diaryEntry = binding.edtDiaryEntry.text.toString()
            val favoriteMovies = binding.edtFavoriteMovies.text.toString()

            if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
                val sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val userName = "$firstName $lastName"

                editor.putString("firstName_$userName", firstName)
                editor.putString("lastName_$userName", lastName)
                editor.putString("nickname_$userName", nickname)
                editor.putString("age_$userName", age)
                editor.putString("birthdate_$userName", birthdate)
                editor.putString("gender_$userName", gender)
                editor.putString("address_$userName", address)
                editor.putString("school_$userName", school)
                editor.putString("zodiac_$userName", zodiac)
                editor.putString("favoriteSongs_$userName", favoriteSongs)
                editor.putString("favoriteMovies_$userName", favoriteMovies)
                editor.putString("favoriteArtist_$userName", favoriteArtist) // Save the artist
                editor.putString("diaryEntry_$userName", diaryEntry)

                val userNames = sharedPreferences.getStringSet("userNames", mutableSetOf()) ?: mutableSetOf()
                userNames.add(userName)
                editor.putStringSet("userNames", userNames)

                editor.apply()

                Toast.makeText(this, "User Information Saved", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please fill out all required fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    }

