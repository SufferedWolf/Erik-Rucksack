package com.captaindeer.erik_rucksack.ui.viewmodels

/**
 * Created by suffered on 25/03/25
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.captaindeer.erik_rucksack.data.apiRest.CharactersRepository

class CharacterViewModelFactory(
    private val repository: CharactersRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
