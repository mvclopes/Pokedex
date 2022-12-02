package br.com.heiderlopes.pokemonwstemplatev2

import android.app.Application
import br.com.heiderlopes.pokemonwstemplatev2.di.pokemonModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(pokemonModules)
        }
    }
}