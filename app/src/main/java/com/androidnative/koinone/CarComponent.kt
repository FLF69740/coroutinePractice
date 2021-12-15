package com.androidnative.koinone

import com.androidnative.model.Car
import dagger.Component

interface CarComponent {

    fun getCar(): Car

}