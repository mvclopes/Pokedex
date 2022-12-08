package br.com.heiderlopes.pokemonwstemplatev2.presentation.listpokemons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.heiderlopes.pokemonwstemplatev2.domain.ViewState
import br.com.heiderlopes.pokemonwstemplatev2.domain.model.Pokemon
import br.com.heiderlopes.pokemonwstemplatev2.domain.usecase.GetFirstGenerationPokemonsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListPokemonsViewModel(
    private val getFirstGenerationPokemonsUseCase: GetFirstGenerationPokemonsUseCase
): ViewModel() {

    private val _pokemonResult = MutableLiveData<ViewState<List<Pokemon>>>()
    val pokemonResult: LiveData<ViewState<List<Pokemon>>>
        get() = _pokemonResult

    fun getPokemons() {
        _pokemonResult.postValue(ViewState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                getFirstGenerationPokemonsUseCase()
            }.onSuccess { result ->
                val pokemons = result.getOrNull()
                pokemons?.let { _pokemonResult.postValue(ViewState.Success(it)) }
            }.onFailure { throwable ->
                _pokemonResult.postValue(ViewState.Failure(throwable))
            }
        }
    }
}