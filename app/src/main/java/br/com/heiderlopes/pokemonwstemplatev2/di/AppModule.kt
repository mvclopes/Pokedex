package br.com.heiderlopes.pokemonwstemplatev2.di

import br.com.heiderlopes.pokemonwstemplatev2.data.remote.api.PokemonService
import br.com.heiderlopes.pokemonwstemplatev2.data.remote.picasso.PicassoClient
import br.com.heiderlopes.pokemonwstemplatev2.data.remote.retrofit.HttpClient
import br.com.heiderlopes.pokemonwstemplatev2.data.remote.retrofit.RetrofitClient
import br.com.heiderlopes.pokemonwstemplatev2.data.repository.PokemonRepositoryImpl
import br.com.heiderlopes.pokemonwstemplatev2.domain.repository.PokemonRepository
import br.com.heiderlopes.pokemonwstemplatev2.domain.usecase.GetFirstGenerationPokemonsUseCase
import br.com.heiderlopes.pokemonwstemplatev2.domain.usecase.GetPokemonUseCase
import br.com.heiderlopes.pokemonwstemplatev2.domain.usecase.UpdatePokemonUseCase
import br.com.heiderlopes.pokemonwstemplatev2.presentation.formpokemon.FormPokemonViewModel
import br.com.heiderlopes.pokemonwstemplatev2.presentation.listpokemons.ListPokemonsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pokemonModules by lazy {
    listOf(
        presentationModules,
        domainModules,
        dataModules,
        networkModules
    )
}

private val presentationModules = module {
    viewModel { ListPokemonsViewModel(getFirstGenerationPokemonsUseCase = get()) }
    viewModel {
        FormPokemonViewModel(
            getPokemonUseCase = get(),
            updatePokemonUseCase = get()
        )
    }
}

private val domainModules = module {
    factory { GetFirstGenerationPokemonsUseCase(repository = get()) }
    factory { GetPokemonUseCase(repository = get()) }
    factory { UpdatePokemonUseCase(repository = get()) }
}

private val dataModules = module {
    factory<PokemonRepository> { PokemonRepositoryImpl(service = get()) }
}

private val networkModules = module {
    single { HttpClient(retrofit = get()) }
    single { PicassoClient(application = androidApplication()).newInstance() }
    single { RetrofitClient(application = androidApplication()).newInstance() }
    factory { get<HttpClient>().create(PokemonService::class.java) }
}