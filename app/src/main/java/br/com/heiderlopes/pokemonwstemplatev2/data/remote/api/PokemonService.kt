package br.com.heiderlopes.pokemonwstemplatev2.data.remote.api

import br.com.heiderlopes.pokemonwstemplatev2.data.remote.model.PokemonListResponse
import br.com.heiderlopes.pokemonwstemplatev2.data.remote.model.PokemonRequest
import br.com.heiderlopes.pokemonwstemplatev2.data.remote.model.PokemonResponse
import retrofit2.http.*

interface PokemonService {

    @GET("/api/pokemon")
    suspend fun getPokemons(
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): PokemonListResponse

    @GET("/api/pokemon/{number}")
    suspend fun getPokemonById(
        @Path("number") number: String
    ): PokemonResponse

    @PUT("/api/pokemon")
    suspend fun updatePokemon(
        @Body pokemon: PokemonRequest
    ): PokemonResponse
}