package com.example.myslambook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myslambook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private var userNames: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve list of user names from SharedPreferences
        val sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE)
        userNames = sharedPreferences.getStringSet("userNames", mutableSetOf())?.toMutableList() ?: mutableListOf()

        // Set up RecyclerView to show user names in cards
        adapter = UserAdapter(this, userNames) { userName ->
            // When a card is clicked, pass the userName to UserDetailsActivity
            val intent = Intent(this, UserDetailsActivity::class.java)
            intent.putExtra("userName", userName)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Add swipe-to-delete functionality
        val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val userName = userNames[position]

                // Show a confirmation dialog before deleting
                showDeleteConfirmationDialog(userName, position)
            }
        }

        // Attach ItemTouchHelper to RecyclerView
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.add.setOnClickListener {
            // Navigate to form1 activity to add new entry
            val intent = Intent(this, form1::class.java)
            startActivity(intent)
        }
    }

    // Function to show a confirmation dialog before deleting
    private fun showDeleteConfirmationDialog(userName: String, position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you really want to delete \"$userName\"?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Proceed with deletion if "Yes"
                deleteUser(userName, position)
            }
            .setNegativeButton("No") { dialog, id ->
                // Restore the item if "No"
                adapter.notifyItemChanged(position)
            }

        val alert = builder.create()
        alert.show()
    }

    // Function to delete the user from the list and SharedPreferences
    private fun deleteUser(userName: String, position: Int) {
        // Remove the user from SharedPreferences
        removeFromSharedPreferences(userName)

        // Remove the user from the list and update the adapter
        userNames.removeAt(position)
        adapter.updateData(userNames)

        // Show a toast message
        Toast.makeText(this@MainActivity, "$userName deleted", Toast.LENGTH_SHORT).show()
    }

    // Helper function to remove the user from SharedPreferences
    private fun removeFromSharedPreferences(userName: String) {
        val sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Get the current user names list from SharedPreferences
        val userNamesSet = sharedPreferences.getStringSet("userNames", mutableSetOf())?.toMutableSet()

        // Remove the user name from the set
        userNamesSet?.remove(userName)

        // Update the SharedPreferences with the new set
        editor.putStringSet("userNames", userNamesSet)
        editor.apply()
    }
}
