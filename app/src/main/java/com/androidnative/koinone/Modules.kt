package com.androidnative.koinone

import com.androidnative.model.Car
import com.androidnative.model.Engine
import com.androidnative.model.Wheel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { Engine() }
    factory { Wheel() }
    factory { Car(get(), get()) }
}

val viewmodelModule = module {
    viewModel { MyViewModel() }
}