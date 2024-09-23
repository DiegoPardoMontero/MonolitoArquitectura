import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.puj.proyectoensenarte.R

class AlphabetAdapter(
    private val letters: List<String>,
    private val onLetterClick: (String) -> Unit
) : RecyclerView.Adapter<AlphabetAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivLetter: ImageView = view.findViewById(R.id.ivLetter)
        val tvLetter: TextView = view.findViewById(R.id.tvLetter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alphabet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val letter = letters[position]
        holder.tvLetter.text = letter

        // Cargar imagen desde Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("abecedario/${letter}.png")

        imageRef.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(holder.ivLetter.context)
                .load(uri)
                .placeholder(R.drawable.vestuario)
                .error(R.drawable.inteligencia)
                .into(holder.ivLetter)
        }.addOnFailureListener {
            holder.ivLetter.setImageResource(R.drawable.inteligencia)
        }

        // Configurar el clic en el elemento
        holder.itemView.setOnClickListener {
            onLetterClick(letter)
        }
    }

    override fun getItemCount() = letters.size
}