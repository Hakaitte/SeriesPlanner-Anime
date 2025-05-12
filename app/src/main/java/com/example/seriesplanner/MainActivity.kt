package com.example.seriesplanner

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.seriesplanner.databinding.ActivityMainBinding // Import ViewBinding
import ui.AnimeListAdapter
import com.example.seriesplanner.ui.MainViewModel
import com.example.seriesplanner.ui.SearchUiState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // Inicjalizacja ViewBinding
    private lateinit var binding: ActivityMainBinding

    // Inicjalizacja ViewModel za pomocą KTX extension
    private val viewModel: MainViewModel by viewModels()

    // Inicjalizacja adaptera
    private val animeAdapter = AnimeListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Użycie ViewBinding do ustawienia layoutu
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearch()
        observeUiState()
    }

    private fun setupRecyclerView() {
        binding.resultsRecyclerView.adapter = animeAdapter
        // LayoutManager jest już ustawiony w XML, ale można go też ustawić tutaj:
        // binding.resultsRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupSearch() {
        binding.searchButton.setOnClickListener {
            performSearch()
        }

        // Opcjonalnie: wyszukaj po naciśnięciu "Search" na klawiaturze
        binding.tagEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true // Zdarzenie obsłużone
            } else {
                false // Zdarzenie nieobsłużone
            }
        }
    }

    private fun performSearch() {
        val query = binding.tagEditText.text.toString().trim()
        viewModel.searchAnimeByTag(query)
        hideKeyboard() // Schowaj klawiaturę po wyszukaniu
    }


    private fun observeUiState() {
        // Używamy lifecycleScope i repeatOnLifecycle dla bezpiecznego zbierania danych z Flow
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    // Resetuj widoczność elementów przed pokazaniem odpowiedniego stanu
                    binding.progressBar.visibility = View.GONE
                    binding.errorTextView.visibility = View.GONE
                    binding.resultsRecyclerView.visibility = View.VISIBLE // Domyślnie widoczna

                    when (state) {
                        is SearchUiState.Idle -> {
                            // Stan początkowy, nic nie rób lub pokaż jakąś instrukcję
                            binding.resultsRecyclerView.visibility = View.INVISIBLE // Ukryj na starcie
                        }
                        is SearchUiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.resultsRecyclerView.visibility = View.INVISIBLE // Ukryj listę podczas ładowania
                        }
                        is SearchUiState.Success -> {
                            animeAdapter.submitList(state.animeList)
                        }
                        is SearchUiState.Error -> {
                            binding.errorTextView.text = state.message
                            binding.errorTextView.visibility = View.VISIBLE
                            binding.resultsRecyclerView.visibility = View.INVISIBLE // Ukryj listę przy błędzie
                            animeAdapter.submitList(emptyList()) // Wyczyść listę w razie błędu
                        }
                    }
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this) // Jeśli żaden widok nie ma fokusu, stwórz tymczasowy
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}