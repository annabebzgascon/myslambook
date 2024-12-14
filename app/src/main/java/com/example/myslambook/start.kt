package com.example.myslambook

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myslambook.databinding.ActivityStartBinding

class start : AppCompatActivity() {

    // Declare the binding object
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nameEditText = findViewById<EditText>(R.id.name)
        val startButton = findViewById<Button>(R.id.start)

        startButton.setOnClickListener {
            val userName = nameEditText.text.toString()




                if (userName.isEmpty()) {
                    Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                } else {
                    // Proceed to the next activity and pass the name
                    val intent = Intent(this, form1::class.java)
                    intent.putExtra("USER_NAME", userName)
                    startActivity(intent)
                    finish()
                }
            }
        }

}
