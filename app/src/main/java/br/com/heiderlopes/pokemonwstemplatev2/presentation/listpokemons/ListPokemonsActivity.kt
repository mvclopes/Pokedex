package br.com.heiderlopes.pokemonwstemplatev2.presentation.listpokemons

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import br.com.heiderlopes.pokemonwstemplatev2.R
import br.com.heiderlopes.pokemonwstemplatev2.databinding.ActivityListPokemonsBinding
import br.com.heiderlopes.pokemonwstemplatev2.domain.ViewState
import br.com.heiderlopes.pokemonwstemplatev2.presentation.formpokemon.FormPokemonActivity
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

private val TAG = ListPokemonsActivity::class.simpleName

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
                    Log.i(TAG, "pokemons: ${state.data}")
                    binding.loading.containerLoading.isVisible = false
                    binding.rvPokemons.adapter = ListPokemonsAdapter(state.data, picasso) {
                        val intent = Intent(this, FormPokemonActivity::class.java)
                        intent.putExtra("POKEMON", it.number)
                        startActivity(intent)
                    }
                    binding.rvPokemons.layoutManager = GridLayoutManager(this, 3)
                }
                is ViewState.Loading -> {
                    binding.loading.containerLoading.isVisible = true
                }
                is ViewState.Failure -> {
                    binding.loading.containerLoading.isVisible = false
                    Toast.makeText(this, state.throwable.message, Toast.LENGTH_LONG).show()
                }

            }
        }
    }
}