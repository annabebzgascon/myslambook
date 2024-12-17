package com.example.myslambook

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myslambook.databinding.ActivityUserDetailsBinding
import java.util.*

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra("userName")
        val sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)

        // Fetch user details from SharedPreferences
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

        // Populate fields with fetched data
        binding.edtFirstName.setText(firstName)
        binding.edtLastName.setText(lastName)
        binding.edtNickname.setText(nickname)
        binding.edtAge.setText(age)
        binding.edtBirthdate.setText(birthdate)
        binding.radioGroupGender.check(
            if (gender == "Male") R.id.radioMale else R.id.radioFemale
        )
        binding.Address.setText(address)
        binding.School.setText(school)
        binding.Zodiac.setText(zodiac)
        binding.edtFavoriteSongs.setText(favoriteSongs)
        binding.Artist.setText(favoriteArtist)
        binding.edtFavoriteMovies.setText(favoriteMovies)
        binding.edtDiaryEntry.setText(diaryEntry)

        // Enable editing when "Edit" button is clicked
        binding.btnEdit.setOnClickListener {
            enableFields(true)
        }

        // Save the updated data to SharedPreferences when "Save" button is clicked
        binding.btnSave.setOnClickListener {
            Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
            enableFields(false)
        }

        // Cancel changes and restore original values
        binding.btnCancel.setOnClickListener {
            enableFields(false)
            // Revert any changes (this step is optional if fields are disabled on cancel)
            binding.edtFirstName.setText(firstName)
            binding.edtLastName.setText(lastName)
            binding.edtNickname.setText(nickname)
            binding.edtAge.setText(age)
            binding.edtBirthdate.setText(birthdate)
            binding.radioGroupGender.check(
                if (gender == "Male") R.id.radioMale else R.id.radioFemale
            )
            binding.Address.setText(address)
            binding.School.setText(school)
            binding.Zodiac.setText(zodiac)
            binding.edtFavoriteSongs.setText(favoriteSongs)
            binding.Artist.setText(favoriteArtist)
            binding.edtFavoriteMovies.setText(favoriteMovies)
            binding.edtDiaryEntry.setText(diaryEntry)
        }

        // Open DatePicker when user clicks on Birthdate field
        binding.edtBirthdate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun enableFields(enable: Boolean) {
        binding.edtFirstName.isEnabled = enable
        binding.edtLastName.isEnabled = enable
        binding.edtNickname.isEnabled = enable
        binding.edtAge.isEnabled = enable
        binding.edtBirthdate.isEnabled = enable
        binding.Address.isEnabled = enable
        binding.School.isEnabled = enable
        binding.Zodiac.isEnabled = enable
        binding.edtFavoriteSongs.isEnabled = enable
        binding.Artist.isEnabled = enable
        binding.edtFavoriteMovies.isEnabled = enable
        binding.edtDiaryEntry.isEnabled = enable
        binding.radioGroupGender.isEnabled = enable
        binding.btnSave.visibility = if (enable) Button.VISIBLE else Button.GONE
        binding.btnCancel.visibility = if (enable) Button.VISIBLE else Button.GONE
    }

    private fun calculateAge(birthdate: String): Int {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val birthDateArray = birthdate.split("-")
        if (birthDateArray.size != 3) return -1  // Invalid birthdate format

        val birthYear = birthDateArray[0].toIntOrNull() ?: return -1
        val birthMonth = birthDateArray[1].toIntOrNull()?.minus(1) ?: return -1
        val birthDay = birthDateArray[2].toIntOrNull() ?: return -1

        var age = currentYear - birthYear
        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            age--
        }

        return age
    }


    private fun saveUserInfo(userName: String?, gender: String, birthdate: String, age: Int) {
        val sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (userName != null) {
            editor.putString("firstName_$userName", binding.edtFirstName.text.toString())
            editor.putString("lastName_$userName", binding.edtLastName.text.toString())
            editor.putString("nickname_$userName", binding.edtNickname.text.toString())
            editor.putString("age_$userName", age.toString())
            editor.putString("birthdate_$userName", birthdate)
            editor.putString("gender_$userName", gender)
            editor.putString("address_$userName", binding.Address.text.toString())
            editor.putString("school_$userName", binding.School.text.toString())
            editor.putString("zodiac_$userName", binding.Zodiac.text.toString())
            editor.putString("favoriteSongs_$userName", binding.edtFavoriteSongs.text.toString())
            editor.putString("favoriteArtist_$userName", binding.Artist.text.toString())
            editor.putString("favoriteMovies_$userName", binding.edtFavoriteMovies.text.toString())
            editor.putString("diaryEntry_$userName", binding.edtDiaryEntry.text.toString())
            editor.apply()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                binding.edtBirthdate.setText("$selectedYear-${selectedMonth + 1}-$selectedDay")
            },
            year, month, day
        )
        val maxDateCalendar = Calendar.getInstance()
        maxDateCalendar.set(2020, Calendar.DECEMBER, 31) // December 31, 2023
        datePickerDialog.datePicker.maxDate = maxDateCalendar.timeInMillis

        datePickerDialog.show()
    }
}
