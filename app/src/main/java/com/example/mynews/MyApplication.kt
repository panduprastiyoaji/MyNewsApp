package com.example.mynews

import android.app.Application
import com.example.mynews.core.di.databaseModule
import com.example.mynews.core.di.networkModule
import com.example.mynews.core.di.repositoryModule
import com.example.mynews.di.useCaseModule
import com.example.mynews.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}