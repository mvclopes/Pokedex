package br.com.heiderlopes.pokemonwstemplatev2.data.remote.mapper

import br.com.heiderlopes.pokemonwstemplatev2.data.remote.model.PokemonRequest
import br.com.heiderlopes.pokemonwstemplatev2.domain.model.Pokemon
import br.com.heiderlopes.pokemonwstemplatev2.utils.Mapper

class PokemonToPokemonRequestMapper : Mapper<Pokemon, PokemonRequest> {
    override fun map(source: Pokemon): PokemonRequest {
        return PokemonRequest(
            number = source.number,
            name = source.name,
            imageURL = source.imageURL,
            ps = source.ps,
            attack = source.attack,
            defense = source.defense,
            velocity = source.velocity
        )
    }
}