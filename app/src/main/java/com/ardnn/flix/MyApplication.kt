package com.ardnn.flix

import android.app.Application
import com.ardnn.flix.core.di.CoreComponent
import com.ardnn.flix.core.di.DaggerCoreComponent
import com.ardnn.flix.di.AppComponent
import com.ardnn.flix.di.DaggerAppComponent

open class MyApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}