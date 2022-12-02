package br.com.heiderlopes.pokemonwstemplatev2.domain.usecase

import br.com.heiderlopes.pokemonwstemplatev2.domain.repository.PokemonRepository

class GetFirstGenerationPokemonsUseCase(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke() = repository.getPokemons(
        size = 150,
        sort = "number,asc"
    )

}
