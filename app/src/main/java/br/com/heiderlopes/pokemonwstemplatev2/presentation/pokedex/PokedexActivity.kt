package br.com.heiderlopes.pokemonwstemplatev2.presentation.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.heiderlopes.pokemonwstemplatev2.R
import br.com.heiderlopes.pokemonwstemplatev2.databinding.ActivityPokedexBinding
import br.com.heiderlopes.pokemonwstemplatev2.domain.ViewState
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

private const val POKEMON_DEFAULT_ID = "004"

class PokedexActivity : AppCompatActivity() {

    private val binding: ActivityPokedexBinding by lazy { ActivityPokedexBinding.inflate(layoutInflater) }
    private val pokemonId: String by lazy { intent.getStringExtra("POKEMON") ?: POKEMON_DEFAULT_ID }
    private val viewModel: PokedexViewModel by viewModel()
    private val picasso: Picasso by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getPokemon(pokemonId)
        registerObserver()
    }

    private fun registerObserver() {
        viewModel.pokemon.observe(this) { state ->
            when (state) {
                ViewState.Loading -> { binding}
                is ViewState.Failure -> {}
                is ViewState.Success -> {
                    binding.tvPokemonName.text = "$pokemonId ${state.data.name}"
                    picasso
                        .load("https://pokedexdx.herokuapp.com/${state.data.imageURL}")
                        .error(R.drawable.pokebola)
                        .placeholder(R.drawable.logo_pokemon)
                        .into(binding.ivPokemon)
                }
            }
        }
    }
}