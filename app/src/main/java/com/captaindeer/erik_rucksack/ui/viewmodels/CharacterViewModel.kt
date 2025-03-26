package com.captaindeer.erik_rucksack.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.captaindeer.erik_rucksack.data.apiRest.CharactersRepository
import com.captaindeer.erik_rucksack.data.apiRest.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by suffered on 24/03/25
 */

class CharacterViewModel(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _state = MutableStateFlow(emptyList<Character>())
    val state: StateFlow<List<Character>>
        get() = _state

    init {
        getCharacters()
    }

    fun getCharacters(){
        viewModelScope.launch {
            try {
                _state.value = repository.getCharacters().results
            } catch (e: Exception){
                println("Error al obtener los personajes: ${e.message}")

            }
        }
    }
}