package com.androidnative.model

data class Car (
    val engine: Engine,
    val wheel: Wheel) {

    fun drive(): String {
        return "${engine.start()}\n${wheel.move()}\nDriving ..."
    }

    var name: String? = null

}