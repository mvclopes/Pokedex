package br.com.heiderlopes.pokemonwstemplatev2.presentation.formpokemon

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.heiderlopes.pokemonwstemplatev2.R
import br.com.heiderlopes.pokemonwstemplatev2.databinding.ActivityFormPokemonBinding
import br.com.heiderlopes.pokemonwstemplatev2.domain.ViewState
import br.com.heiderlopes.pokemonwstemplatev2.domain.model.Pokemon
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

private const val POKEMON_DEFAULT_ID = "004"

class FormPokemonActivity : AppCompatActivity() {

    private val binding: ActivityFormPokemonBinding by lazy {
        ActivityFormPokemonBinding.inflate(layoutInflater)
    }
    private val pokemonId: String by lazy {
        intent.getStringExtra("POKEMON") ?: POKEMON_DEFAULT_ID
    }
    private val viewModel: FormPokemonViewModel by viewModel()
    private val picasso: Picasso by inject()

    private lateinit var pokemon: Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getPokemon(pokemonId)
        registerObserver()
        update()
    }

    private fun registerObserver() {
        viewModel.pokemon.observe(this) {
            when (it) {
                is ViewState.Success -> setValues(it.data)
                is ViewState.Loading -> {}
                is ViewState.Failure ->
                    Toast.makeText(this, it.throwable.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setValues(pokemon: Pokemon) {
        this.pokemon = pokemon
        binding.tvPokemonNameForm.text = pokemon.name
        picasso
            .load("https://pokedexdx.herokuapp.com${pokemon.imageURL}")
            .placeholder(R.drawable.logo_pokemon)
            .into(binding.ivPokemonForm)
        binding.sbAttack.progress = pokemon.attack
        binding.sbDefense.progress = pokemon.defense
        binding.sbPS.progress = pokemon.ps
        binding.sbVelocity.progress = pokemon.velocity
        binding.tvAttackValue.text = pokemon.attack.toString()
        binding.tvDefenseValue.text = pokemon.defense.toString()
        binding.tvPSValue.text = pokemon.ps.toString()
        binding.tvVelocityValue.text = pokemon.velocity.toString()
        setListener(binding.sbAttack, binding.tvAttackValue)
        setListener(binding.sbDefense, binding.tvDefenseValue)
        setListener(binding.sbVelocity, binding.tvVelocityValue)
        setListener(binding.sbPS, binding.tvPSValue)
    }

    private fun setListener(seekBar: SeekBar, textView: TextView) {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int, fromUser: Boolean
            ) {
                textView.text = progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun update() {
        binding.btSaveForm.setOnClickListener {
            pokemon.attack = binding.sbAttack.progress
            pokemon.defense = binding.sbDefense.progress
            pokemon.velocity = binding.sbVelocity.progress
            pokemon.ps = binding.sbPS.progress
            viewModel.update(pokemon)
        }
    }
}
