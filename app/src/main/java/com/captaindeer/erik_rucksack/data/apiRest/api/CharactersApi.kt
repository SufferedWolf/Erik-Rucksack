package com.captaindeer.erik_rucksack.data.apiRest.api

import com.captaindeer.erik_rucksack.data.apiRest.model.CharacterList
import retrofit2.http.GET

/**
 * Created by suffered on 24/03/25
 */

interface CharactersApi {
    @GET("character")
    suspend fun getCharacters(): CharacterList
}