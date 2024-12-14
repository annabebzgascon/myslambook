import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myslambook.R

class CardAdapter(private val items: List<CardData>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    // ViewHolder class for binding views
    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.itemTitle)
        val itemDescription: TextView = itemView.findViewById(R.id.itemDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemTitle.text = currentItem.title
        holder.itemDescription.text = currentItem.description
    }

    override fun getItemCount(): Int = items.size
}

// Data model for the card
data class CardData(val title: String, val description: String)
