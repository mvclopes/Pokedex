package br.com.heiderlopes.pokemonwstemplatev2.presentation.listpokemons

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import br.com.heiderlopes.pokemonwstemplatev2.databinding.ActivityListPokemonsBinding
import br.com.heiderlopes.pokemonwstemplatev2.domain.ViewState
import br.com.heiderlopes.pokemonwstemplatev2.domain.model.Pokemon
import br.com.heiderlopes.pokemonwstemplatev2.presentation.formpokemon.FormPokemonActivity
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ListPokemonsActivity : AppCompatActivity() {

    private val viewModel: ListPokemonsViewModel by viewModel()
    private val picasso: Picasso by inject()
    private val binding: ActivityListPokemonsBinding by lazy {
        ActivityListPokemonsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getPokemons()
        registerObserver()
    }

    private fun registerObserver() {
        viewModel.pokemonResult.observe(this) { state ->
            when (state) {
                is ViewState.Success -> {
                    isContainerLoadingVisible(false)
                    setPokemonsAdapter(state.data)
                    binding.rvPokemons.layoutManager = GridLayoutManager(this, 3)
                }
                is ViewState.Loading -> isContainerLoadingVisible(true)
                is ViewState.Failure -> {
                    isContainerLoadingVisible(false)
                    Toast.makeText(this, state.throwable.message, Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun setPokemonsAdapter(pokemons: List<Pokemon>) {
        binding.rvPokemons.adapter = ListPokemonsAdapter(pokemons, picasso) {
            val intent = Intent(this, FormPokemonActivity::class.java)
            intent.putExtra("POKEMON", it.number)
            startActivity(intent)
        }
    }

    private fun isContainerLoadingVisible(isVisible: Boolean) {
        binding.loading.containerLoading.isVisible = isVisible
    }
}