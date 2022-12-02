package br.com.heiderlopes.pokemonwstemplatev2.domain.repository

import br.com.heiderlopes.pokemonwstemplatev2.domain.model.Pokemon

interface PokemonRepository {

    suspend fun getPokemons(
        size: Int,
        sort: String
    ): Result<List<Pokemon>>

}