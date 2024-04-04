package com.kma.movies

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kma.movies.data.sources.remote.CommonSharedPreferences
import com.kma.movies.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AndroidThreeTen.init(this)
        CommonSharedPreferences.init(this)
        startKoin {
            androidContext(this@MoviesApp)
            modules(
                mainModule,
                popularMoviesModule,
                upcomingMoviesModule,
                favoriteMoviesModule,
                movieDetailsModule,
                registerMoviesModule,
                signUpMoviesModule,
                profileModule,
                getCommentModule,
                searchModule,
                postCvModule,
                getCvModule,
                postJobModule,
                getCvListModule
            )
        }
        setupTimberLog()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun setupTimberLog() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                return String.format(
                    "%s::%s:%s",
                    super.createStackElementTag(element),
                    element.methodName,
                    element.lineNumber
                )
            }
        })
    }
}