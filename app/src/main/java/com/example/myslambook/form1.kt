package com.example.myslambook

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myslambook.databinding.ActivityForm1Binding
import java.util.Calendar

class form1 : AppCompatActivity() {

    private lateinit var binding: ActivityForm1Binding
    private var selectedGender: String = ""

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

                    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                    val calculatedAge = currentYear - selectedYear
                    binding.age.setText(calculatedAge.toString())
                },
                year, month, day
            )

            val maxDateCalendar = Calendar.getInstance()
            maxDateCalendar.set(2020, Calendar.DECEMBER, 31) // December 31, 2023
            datePickerDialog.datePicker.maxDate = maxDateCalendar.timeInMillis

            datePickerDialog.show()
        }

        binding.genderRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedGender = when (checkedId) {
                R.id.radioMale -> "Male"
                R.id.radioFemale -> "Female"
                else -> ""
            }
            Toast.makeText(this, "Selected Gender: $selectedGender", Toast.LENGTH_SHORT).show()
        }

        binding.btninfoSubmit.setOnClickListener {
            val firstName = binding.FirstName.text.toString()
            val lastName = binding.LastName.text.toString()
            val nickname = binding.Nickname.text.toString()
            val age = binding.age.text.toString()
            val birthdate = binding.birthdate.text.toString()
            val address = binding.address.text.toString()
            val school = binding.school.text.toString()
            val zodiac = binding.zodiac.text.toString()
            val favoriteSongs = binding.edtFavoriteSongs.text.toString()
            val favoriteArtist = binding.favoriteArtist.text.toString()
            val diaryEntry = binding.edtDiaryEntry.text.toString()
            val favoriteMovies = binding.edtFavoriteMovies.text.toString()

            var isValid = true

            // Validate each required field
            when {
                firstName.isEmpty() -> {
                    binding.FirstName.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.FirstName.error = null
            }

            when {
                lastName.isEmpty() -> {
                    binding.LastName.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.LastName.error = null
            }

            when {
                nickname.isEmpty() -> {
                    binding.Nickname.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.Nickname.error = null
            }

            when {
                age.isEmpty() -> {
                    binding.age.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.age.error = null
            }

            when {
                birthdate.isEmpty() -> {
                    binding.birthdate.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.birthdate.error = null
            }

            when {
                address.isEmpty() -> {
                    binding.address.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.address.error = null
            }

            when {
                school.isEmpty() -> {
                    binding.school.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.school.error = null
            }

            when {
                favoriteSongs.isEmpty() -> {
                    binding.edtFavoriteSongs.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.edtFavoriteSongs.error = null
            }

            when {
                favoriteArtist.isEmpty() -> {
                    binding.favoriteArtist.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.favoriteArtist.error = null
            }

            when {
                diaryEntry.isEmpty() -> {
                    binding.edtDiaryEntry.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.edtDiaryEntry.error = null
            }

            when {
                favoriteMovies.isEmpty() -> {
                    binding.edtFavoriteMovies.error = "This field cannot be empty"
                    isValid = false
                }
                else -> binding.edtFavoriteMovies.error = null
            }

            if (birthdate.isNotEmpty() && age.isNotEmpty()) {
                val birthYear = birthdate.split("/")[2].toIntOrNull()
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                val calculatedAge = currentYear - (birthYear ?: 0)

                if (calculatedAge != age.toIntOrNull()) {
                    binding.age.error = "Age does not match birth year"
                    isValid = false
                } else {
                    binding.age.error = null
                }
            }
            // Proceed if all fields are valid
            if (isValid) {
                val sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                val userName = "$firstName $lastName"

                val existingUserName = sharedPreferences.getString("userName_$userName", null)

                if (existingUserName != null) {
                    Toast.makeText(this, "Name already exists! Please enter a different name.", Toast.LENGTH_SHORT).show()
                } else {

                    val editor = sharedPreferences.edit()

                    editor.putString("firstName_$userName", firstName)
                    editor.putString("lastName_$userName", lastName)
                    editor.putString("nickname_$userName", nickname)
                    editor.putString("age_$userName", age)
                    editor.putString("birthdate_$userName", birthdate)
                    editor.putString("address_$userName", address)
                    editor.putString("school_$userName", school)
                    editor.putString("zodiac_$userName", zodiac)
                    editor.putString("favoriteSongs_$userName", favoriteSongs)
                    editor.putString("favoriteMovies_$userName", favoriteMovies)
                    editor.putString("favoriteArtist_$userName", favoriteArtist)
                    editor.putString("diaryEntry_$userName", diaryEntry)

                    val userNames = sharedPreferences.getStringSet("userNames", mutableSetOf())
                        ?: mutableSetOf()
                    userNames.add(userName)
                    editor.putStringSet("userNames", userNames)

                    editor.apply()

                    Toast.makeText(this, "User Information Saved", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}
