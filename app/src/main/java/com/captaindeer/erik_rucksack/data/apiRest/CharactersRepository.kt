package com.captaindeer.erik_rucksack.data.apiRest

import com.captaindeer.erik_rucksack.core.utils.RetrofitClient
import com.captaindeer.erik_rucksack.data.apiRest.model.CharacterList

/**
 * Created by suffered on 24/03/25
 */

class CharactersRepository {
    private val charactersApi = RetrofitClient.charactersApi

    suspend fun getCharacters(): CharacterList{
        return charactersApi.getCharacters()
    }

}