package com.androidnative.koinone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.androidnative.coroutineone.R
import com.androidnative.model.Car
import com.androidnative.model.Wheel
import kotlinx.android.synthetic.main.activity_four.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.KoinContextHandler.get
import java.lang.StringBuilder

class FourActivity : AppCompatActivity() {

    private val mFordFocus: Car by inject()
    private val viewModel: MyViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_four)

        var myListOfCar = mutableListOf<Car>()

        if (savedInstanceState == null) {
            viewModel.performAction(getCarsListWithNewOne(myListOfCar, mFordFocus))
        } else {
            myListOfCar = viewModel.mCarList.value!!
        }



        button_four.setOnClickListener {
            editText_four?.let { it1 ->
                val newCar = get<Car>()
                newCar.name = it1.text.toString()
                viewModel.performAction(getCarsListWithNewOne(myListOfCar, newCar))
            }
        }

        mFordFocus.name = "Ford Focus"



        viewModel.mCarList.observe(this, Observer {
            text_four.text = getCarsListString(it)
        })

    }

    private fun getCarsListWithNewOne(mutableList: MutableList<Car>, car: Car): MutableList<Car>{
        mutableList.add(car)
        return mutableList
    }

    private fun getCarsListString(mutableList: MutableList<Car>): String{
        var names: String = ""

        mutableList.forEach{
            names += "${it.name}\n"
        }

        return names
    }






}
