package com.androidnative.koinone

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnative.model.Car

class MyViewModel : ViewModel() {

    val mCarList =  MutableLiveData<MutableList<Car>>()

    fun performAction(mutableList: MutableList<Car>){
        mCarList.value = mutableList
    }


}