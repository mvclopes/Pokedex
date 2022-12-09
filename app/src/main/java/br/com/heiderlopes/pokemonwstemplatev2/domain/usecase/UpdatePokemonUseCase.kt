package br.com.heiderlopes.pokemonwstemplatev2.domain.usecase

import br.com.heiderlopes.pokemonwstemplatev2.domain.model.Pokemon
import br.com.heiderlopes.pokemonwstemplatev2.domain.repository.PokemonRepository

class UpdatePokemonUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke(pokemon:Pokemon): Result<Pokemon> {
        return repository.update(pokemon)
    }
}