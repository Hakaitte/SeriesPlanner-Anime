package app.src.main.java.com.example.seriesplanner.retrofit

import com.example.seriesplanner.anime.AnimeSearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface JikanApiService {

    // Szuka anime wg słowa kluczowego (użyjemy "tagu" jako słowa kluczowego)
    // sfw = true -> filtruje treści dla dorosłych (Safe For Work)
    @GET("anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("sfw") safeForWork: Boolean = false,
        @Query("limit") limit: Int = 25,
        @Query("order_by") orderBy: String = "rank"
    ): Response<AnimeSearchResponse> // Używamy Response dla lepszej obsługi błędów

    companion object {
        private const val BASE_URL = "https://api.jikan.moe/v4/"

        fun create(): JikanApiService {
            // Opcjonalny Interceptor do logowania zapytań i odpowiedzi (przydatne przy debugowaniu)
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC // Można zmienić na BODY dla szczegółów
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JikanApiService::class.java)
        }
    }
}