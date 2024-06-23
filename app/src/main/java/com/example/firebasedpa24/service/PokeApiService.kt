package com.example.firebasedpa24.service

import com.example.firebasedpa24.model.PokemonModelResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon")
    fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Call<PokemonModelResponse>
}