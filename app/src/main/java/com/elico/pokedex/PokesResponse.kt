package com.elico.pokedex

import com.google.gson.annotations.SerializedName

data class PokesResponse(
    @SerializedName("next") var siguiente:String,
    @SerializedName("results") var results: List<PokesResult>
)

data class PokesResult(
    @SerializedName("name") var name:String,
    @SerializedName("url") var url:String
)
