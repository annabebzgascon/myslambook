package com.example.myslambook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myslambook.databinding.ItemUserBinding

class UserAdapter(
    private val context: Context,
    private var userNames: List<String>, // Make userNames mutable
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameText: TextView = itemView.findViewById(R.id.textViewUserName)

        init {
            itemView.setOnClickListener {
                val userName = userNames[adapterPosition]
                onClick(userName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_card_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.userNameText.text = userNames[position]
    }

    override fun getItemCount(): Int = userNames.size

    // Method to update data in the adapter
    fun updateData(newData: List<String>) {
        userNames = newData
        notifyDataSetChanged() // Notify the adapter to refresh the view
    }
}
