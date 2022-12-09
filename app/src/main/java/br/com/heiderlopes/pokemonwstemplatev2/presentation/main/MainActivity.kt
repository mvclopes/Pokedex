package br.com.heiderlopes.pokemonwstemplatev2.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.heiderlopes.pokemonwstemplatev2.databinding.ActivityMainBinding
import br.com.heiderlopes.pokemonwstemplatev2.presentation.listpokemons.ListPokemonsActivity
import br.com.heiderlopes.pokemonwstemplatev2.presentation.scan.ScanActivity

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpListeners()
    }
    private fun setUpListeners () {
        with(binding) {
            btPokemonList.setOnClickListener {
                startActivity(Intent(this@MainActivity, ListPokemonsActivity::class.java))
            }
            btClose.setOnClickListener { finish() }
            btPokedex.setOnClickListener {
                startActivity(Intent(this@MainActivity, ScanActivity::class.java))
            }
        }
    }


}