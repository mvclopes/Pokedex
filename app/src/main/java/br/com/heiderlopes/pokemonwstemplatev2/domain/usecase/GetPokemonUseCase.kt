package br.com.heiderlopes.pokemonwstemplatev2.domain.usecase

import br.com.heiderlopes.pokemonwstemplatev2.domain.model.Pokemon
import br.com.heiderlopes.pokemonwstemplatev2.domain.repository.PokemonRepository

class GetPokemonUseCase(private val repository: PokemonRepository) {

    suspend operator fun invoke(id: String): Result<Pokemon> {
        return repository.getPokemonById(id)
    }
}