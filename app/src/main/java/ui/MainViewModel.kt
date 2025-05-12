package com.example.seriesplanner.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.src.main.java.com.example.seriesplanner.retrofit.JikanApiService
import com.example.seriesplanner.anime.AnimeDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

// Klasa pomocnicza do reprezentowania stanu UI
sealed class SearchUiState {
    object Idle : SearchUiState() // Stan początkowy/bezczynny
    object Loading : SearchUiState()
    data class Success(val animeList: List<AnimeDto>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}

class MainViewModel : ViewModel() {

    private val apiService = JikanApiService.create() // Tworzymy instancję serwisu

    // Prywatny MutableStateFlow do zarządzania stanem w ViewModel
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    // Publiczny, niemutowalny StateFlow do obserwacji w UI
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun searchAnimeByTag(tagQuery: String) {
        if (tagQuery.isBlank()) {
            _uiState.value = SearchUiState.Error("Wpisz tag do wyszukania")
            return
        }

        // Ustawiamy stan ładowania
        _uiState.value = SearchUiState.Loading

        // Uruchamiamy korutynę w viewModelScope (automatycznie anulowana gdy ViewModel jest niszczony)
        viewModelScope.launch {
            try {
                val response = apiService.searchAnime(query = tagQuery)
                if (response.isSuccessful && response.body() != null) {
                    val animeList = response.body()!!.data
                    if (animeList.isNotEmpty()) {
                        _uiState.value = SearchUiState.Success(animeList)
                    } else {
                        _uiState.value = SearchUiState.Error("Nie znaleziono wyników dla '$tagQuery'")
                    }
                } else {
                    // Błąd odpowiedzi API (np. 404, 500)
                    _uiState.value = SearchUiState.Error("Błąd API: ${response.code()} - ${response.message()}")
                }
            } catch (e: IOException) {
                // Błąd sieciowy (np. brak połączenia)
                _uiState.value = SearchUiState.Error("Błąd sieci: ${e.message}")
            } catch (e: Exception) {
                // Inny, nieoczekiwany błąd
                _uiState.value = SearchUiState.Error("Nieznany błąd: ${e.message}")
            }
        }
    }
}

