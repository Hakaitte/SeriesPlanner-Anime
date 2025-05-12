package ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load // Import dla Coil
import com.example.seriesplanner.R // Importuj R
import com.example.seriesplanner.anime.AnimeDto
import com.example.seriesplanner.databinding.ItemAnimeBinding

class AnimeListAdapter : ListAdapter<AnimeDto, AnimeListAdapter.AnimeViewHolder>(AnimeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AnimeViewHolder(private val binding: ItemAnimeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(anime: AnimeDto) {
            binding.animeTitleTextView.text = anime.title
            binding.animeTypeTextView.text = "Type: ${anime.type ?: "N/A"}"
            binding.animeScoreTextView.text = "Score: ${anime.score ?: "N/A"}"

            // Ładowanie obrazka za pomocą Coil
            binding.animeImageView.load(anime.images?.jpg?.imageUrl) {
                crossfade(true) // Opcjonalnie: animacja przejścia
                placeholder(R.drawable.ic_launcher_background) // Opcjonalnie: obrazek podczas ładowania (stwórz własny)
                error(R.drawable.ic_launcher_foreground) // Opcjonalnie: obrazek w razie błędu (stwórz własny)
            }
        }
    }

    // DiffUtil pomaga RecyclerView efektywnie aktualizować listę
    class AnimeDiffCallback : DiffUtil.ItemCallback<AnimeDto>() {
        override fun areItemsTheSame(oldItem: AnimeDto, newItem: AnimeDto): Boolean {
            return oldItem.malId == newItem.malId // Porównaj po unikalnym ID
        }

        override fun areContentsTheSame(oldItem: AnimeDto, newItem: AnimeDto): Boolean {
            return oldItem == newItem // Porównaj całą zawartość obiektu
        }
    }
}