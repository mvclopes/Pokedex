package br.com.heiderlopes.pokemonwstemplatev2.data.repository

import br.com.heiderlopes.pokemonwstemplatev2.data.remote.api.PokemonService
import br.com.heiderlopes.pokemonwstemplatev2.data.remote.mapper.PokemonResponseListToPokemonListMapper
import br.com.heiderlopes.pokemonwstemplatev2.data.remote.mapper.PokemonResponseToPokemonMapper
import br.com.heiderlopes.pokemonwstemplatev2.data.remote.mapper.PokemonToPokemonRequestMapper
import br.com.heiderlopes.pokemonwstemplatev2.domain.model.Pokemon
import br.com.heiderlopes.pokemonwstemplatev2.domain.repository.PokemonRepository

class PokemonRepositoryImpl(private val service: PokemonService): PokemonRepository {
    private val pokemonListMapper = PokemonResponseListToPokemonListMapper()
    private val pokemonMapper = PokemonResponseToPokemonMapper()
    private val pokemonRequestMapper = PokemonToPokemonRequestMapper()

    override suspend fun getPokemons(size: Int, sort: String): Result<List<Pokemon>> {
        return Result.success(
            pokemonListMapper.map(
                service.getPokemons(
                    size = size,
                    sort = sort
                ).pokemons
            )
        )
    }

    override suspend fun getPokemonById(id: String): Result<Pokemon> {
        return Result.success(
            pokemonMapper.map(
                service.getPokemonById(id)
            )
        )
    }

    override suspend fun update(pokemon: Pokemon): Result<Pokemon> {
        val pokemonRequest = service.updatePokemon(pokemonRequestMapper.map(pokemon))
        return Result.success(
            pokemonMapper.map(pokemonRequest)
        )
    }
}