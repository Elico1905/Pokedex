package com.elico.pokedex

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface APIService3 {
    @GET("pokemon")
    fun getPokemonList(@Query("limit") limit:Int, @Query("offset") offset:Int): Call<PokesResponse>
}

interface APIService2 {
    @GET("pokemon")
    fun getPokemonList(@Query("limit") limit:Int, @Query("offset") offset:Int): Call<PokesResponse>
}

