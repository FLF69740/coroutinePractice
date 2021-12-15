package com.androidnative.coroutineone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidnative.koinone.FourActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private val waitMessage = "WAIT 3 seconds ..."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = button1
        val txt = text1

        btn.setOnClickListener {
/*
            txt.text = waitMessage
            CoroutineScope(Main).launch {
                btn.isEnabled = false
                waitStarter()
                startActivity(Intent(this@MainActivity, SecondActivity::class.java))
            }

 */
            startActivity(Intent(this, FourActivity::class.java))

        }

    }

    private suspend fun waitStarter(): Unit = delay(3000)






}
