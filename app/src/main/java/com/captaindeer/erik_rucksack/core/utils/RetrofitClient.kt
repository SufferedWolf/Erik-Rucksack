package com.captaindeer.erik_rucksack.core.utils

import com.captaindeer.erik_rucksack.core.utils.Constants.BASE_URL
import com.captaindeer.erik_rucksack.data.apiRest.api.CharactersApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    val charactersApi: CharactersApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharactersApi::class.java)
    }
}