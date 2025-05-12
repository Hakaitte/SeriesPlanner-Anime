package com.example.seriesplanner.anime

import com.google.gson.annotations.SerializedName

// Uproszczony model na podstawie odpowiedzi Jikan API v4 dla /anime?q=...
data class AnimeSearchResponse(
    val data: List<AnimeDto>
    // Można dodać pole 'pagination' jeśli potrzebne
)

data class AnimeDto(
    @SerializedName("mal_id") // Mapuje nazwę JSON na nazwę pola
    val malId: Int,
    val url: String?,
    val images: ImagesDto?,
    val title: String,
    @SerializedName("title_english")
    val titleEnglish: String?,
    @SerializedName("title_japanese")
    val titleJapanese: String?,
    val type: String?,
    val source: String?,
    val episodes: Int?,
    val status: String?,
    val airing: Boolean?,
    val score: Double?,
    val rank: Int?,
    val popularity: Int?,
    val synopsis: String?,
    val background: String?,
    val season: String?,
    val year: Int?,
    val genres: List<GenreDto>?,
    val themes: List<ThemeDto>?,
    val demographics: List<DemographicDto>?
)

data class ImagesDto(
    val jpg: ImageUrlDto?,
    val webp: ImageUrlDto?
)

data class ImageUrlDto(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("small_image_url")
    val smallImageUrl: String?,
    @SerializedName("large_image_url")
    val largeImageUrl: String?
)

data class GenreDto(
    @SerializedName("mal_id")
    val malId: Int,
    val type: String?,
    val name: String,
    val url: String?
)

// Podobnie można zdefiniować ThemeDto i DemographicDto, jeśli są potrzebne
data class ThemeDto(
    @SerializedName("mal_id")
    val malId: Int,
    val name: String
)

data class DemographicDto(
    @SerializedName("mal_id")
    val malId: Int,
    val name: String
)