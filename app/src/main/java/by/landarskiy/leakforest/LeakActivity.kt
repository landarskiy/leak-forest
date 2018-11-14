package by.landarskiy.leakforest

import android.annotation.SuppressLint
import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_leak.*
import java.util.concurrent.TimeUnit

/**
 * Created by Landarskiy on 14.11.18.
 */
class LeakActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak)
        LeakSingleton.instance.loadBitmap(this, object : LeakSingleton.LoadListener {
            override fun onLoadFinished(obj: LargeObject) {
                imageView.setImageBitmap(obj.bmp)
            }
        })
        launchTaskButton.setOnClickListener {
            launchTask()
        }
    }

    @SuppressLint("StaticFieldLeak")
    private fun launchTask() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                Thread.sleep(TimeUnit.SECONDS.toMillis(30))
                return null
            }
        }.execute()
    }
}