package com.androidnative.coroutineone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class SecondActivity : AppCompatActivity() {

    private val mStartBtnText = "START JOB"
    private val mCancelBtnText = "CANCEL JOB"
    private val mProgressText = "waitings..."

    private val mProgressMax = 100
    private val mProgressStart = 0
    private val mJobTime: Long = 5000 // ms
    private lateinit var mJob: CompletableJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        second_btn.setOnClickListener {
            if (!::mJob.isInitialized){
                launchJob()
            }
            second_progress.startJobOrCancel(mJob)

        }

    }

    private fun launchJob(){
        second_btn.text = mStartBtnText
        updateJobCompleteTextView("NOT STARTED")
        mJob = Job()
        mJob.invokeOnCompletion {
            it?.message.let { it1 ->
                var msg = it1
                if (msg.isNullOrBlank()){
                    msg = "Unknownm cancellation error"
                }
            //    showToast(msg)
            }
        }
        second_progress.max = mProgressMax
        second_progress.progress = mProgressStart
    }

    private fun showToast(text: String){
        GlobalScope.launch(Main){
            Toast.makeText(this@SecondActivity, text, Toast.LENGTH_SHORT).show()
        }
    }

    private fun ProgressBar.startJobOrCancel(job: Job){
        if (this.progress > 0){
            resetJob()
        } else {
            second_btn.text = mCancelBtnText
            CoroutineScope(IO + job).launch {
                for (i in mProgressStart..mProgressMax){
                    delay((mJobTime / mProgressMax))
                    this@startJobOrCancel.progress = i
                    updateJobCompleteTextView(mProgressText)
                }
                updateJobCompleteTextView("Job is complete")
                startActivity(Intent(this@SecondActivity, ThirdActivity::class.java))
            }
        }
    }

    private fun updateJobCompleteTextView(text: String){
        GlobalScope.launch(Main){
            second_text.text = text
  //          startActivity(Intent(this@SecondActivity, ThirdActivity::class.java))
        }
    }

    private fun resetJob() {
        if (mJob.isActive || mJob.isCompleted){
            mJob.cancel(CancellationException("RESET JOB"))
        }
        launchJob()
    }


}
