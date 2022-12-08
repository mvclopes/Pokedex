package br.com.heiderlopes.pokemonwstemplatev2.presentation.formpokemon

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.heiderlopes.pokemonwstemplatev2.domain.ViewState
import br.com.heiderlopes.pokemonwstemplatev2.domain.model.Pokemon
import br.com.heiderlopes.pokemonwstemplatev2.domain.usecase.GetPokemonUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormPokemonViewModel(
    private val getPokemonUseCase: GetPokemonUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _pokemon = MutableLiveData<ViewState<Pokemon>>()
    val pokemon: LiveData<ViewState<Pokemon>>
        get() = _pokemon

    fun getPokemon(id: String) {
        _pokemon.postValue(ViewState.Loading)
        viewModelScope.launch(dispatcher) {
            runCatching {
                getPokemonUseCase(id)
            }.onSuccess { result ->
                val pokemon = result.getOrNull()
                Log.i("TAG", "getPokemon: $pokemon")
                pokemon?.let { _pokemon.postValue(ViewState.Success(it)) }
            }.onFailure { throwable ->
                _pokemon.postValue(ViewState.Failure(throwable))
            }
        }
    }

}