package com.randev.belajar_mvi

import android.app.Application
import com.randev.belajar_mvi.data.User
import com.randev.belajar_mvi.di.MainModule
import com.randev.belajar_mvi.event.MutableStateEventManager
import com.randev.belajar_mvi.event.StateEvent
import kotlinx.coroutines.flow.Flow
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

/**
 * @author Raihan Arman
 * @date 07/08/22
 */

class MainApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(MainModule().module)
        }
    }
}
