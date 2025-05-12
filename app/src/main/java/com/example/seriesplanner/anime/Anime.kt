package com.example.seriesplanner.anime

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// --- Główna klasa opakowująca ---
@JsonClass(generateAdapter = true)
data class Anime(
    @Json(name = "data") val data: AnimeData
)

// --- Klasa przechowująca szczegółowe dane anime ---
@JsonClass(generateAdapter = true)
data class AnimeData(
    @Json(name = "mal_id") val malId: Int,
    @Json(name = "url") val url: String,
    @Json(name = "images") val images: Images,
    @Json(name = "trailer") val trailer: Trailer,
    @Json(name = "approved") val approved: Boolean,
    @Json(name = "titles") val titles: List<TitleEntry>,
    @Json(name = "title") val title: String,
    @Json(name = "title_english") val titleEnglish: String?, // Może być null
    @Json(name = "title_japanese") val titleJapanese: String?, // Może być null
    @Json(name = "title_synonyms") val titleSynonyms: List<String>,
    @Json(name = "type") val type: String?, // Np. "TV", "Movie", może być null
    @Json(name = "source") val source: String?, // Np. "Manga", "Original", może być null
    @Json(name = "episodes") val episodes: Int?, // Może być null (np. dla filmów lub w trakcie emisji)
    @Json(name = "status") val status: String?, // Np. "Finished Airing", może być null
    @Json(name = "airing") val airing: Boolean,
    @Json(name = "aired") val aired: Aired,
    @Json(name = "duration") val duration: String?, // Np. "24 min per ep", może być null
    @Json(name = "rating") val rating: String?, // Np. "G - All Ages", może być null
    @Json(name = "score") val score: Double?, // Może być null
    @Json(name = "scored_by") val scoredBy: Int?, // Może być null
    @Json(name = "rank") val rank: Int?, // Może być null
    @Json(name = "popularity") val popularity: Int,
    @Json(name = "members") val members: Int,
    @Json(name = "favorites") val favorites: Int,
    @Json(name = "synopsis") val synopsis: String?, // Może być null
    @Json(name = "background") val background: String?, // Może być null
    @Json(name = "season") val season: String?, // Np. "summer", może być null
    @Json(name = "year") val year: Int?, // Może być null
    @Json(name = "broadcast") val broadcast: Broadcast?, // Może być null
    @Json(name = "producers") val producers: List<MalUrlEntry>,
    @Json(name = "licensors") val licensors: List<MalUrlEntry>,
    @Json(name = "studios") val studios: List<MalUrlEntry>,
    @Json(name = "genres") val genres: List<MalUrlEntry>,
    @Json(name = "explicit_genres") val explicitGenres: List<MalUrlEntry>,
    @Json(name = "themes") val themes: List<MalUrlEntry>,
    @Json(name = "demographics") val demographics: List<MalUrlEntry>
)

// --- Klasy pomocnicze dla zagnieżdżonych obiektów i list ---

@JsonClass(generateAdapter = true)
data class Images(
    @Json(name = "jpg") val jpg: ImageUrls,
    @Json(name = "webp") val webp: ImageUrls
)

@JsonClass(generateAdapter = true)
data class ImageUrls(
    @Json(name = "image_url") val imageUrl: String?,
    @Json(name = "small_image_url") val smallImageUrl: String?,
    @Json(name = "large_image_url") val largeImageUrl: String?
)

@JsonClass(generateAdapter = true)
data class Trailer(
    @Json(name = "youtube_id") val youtubeId: String?,
    @Json(name = "url") val url: String?,
    @Json(name = "embed_url") val embedUrl: String?
)

@JsonClass(generateAdapter = true)
data class TitleEntry(
    @Json(name = "type") val type: String,
    @Json(name = "title") val title: String
)

@JsonClass(generateAdapter = true)
data class Aired(
    @Json(name = "from") val from: String?, // Data w formacie ISO 8601 lub null
    @Json(name = "to") val to: String?, // Data w formacie ISO 8601 lub null
    @Json(name = "prop") val prop: AiredProp,
    @Json(name = "string") val string: String? // Pełny string opisujący datę emisji
)

@JsonClass(generateAdapter = true)
data class AiredProp(
    @Json(name = "from") val from: AiredDate?,
    @Json(name = "to") val to: AiredDate?
)

@JsonClass(generateAdapter = true)
data class AiredDate(
    @Json(name = "day") val day: Int?,
    @Json(name = "month") val month: Int?,
    @Json(name = "year") val year: Int?
)

@JsonClass(generateAdapter = true)
data class Broadcast(
    @Json(name = "day") val day: String?, // Np. "Mondays"
    @Json(name = "time") val time: String?, // Np. "00:00"
    @Json(name = "timezone") val timezone: String?, // Np. "Asia/Tokyo"
    @Json(name = "string") val string: String? // Pełny string np. "Mondays at 00:00 (JST)"
)

// Klasa reużywalna dla list: producers, licensors, studios, genres, explicit_genres, themes, demographics
@JsonClass(generateAdapter = true)
data class MalUrlEntry(
    @Json(name = "mal_id") val malId: Int,
    @Json(name = "type") val type: String,
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)