package by.landarskiy.leakforest

import android.content.Context
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by Landarskiy on 14.11.18.
 */
class LeakSingleton private constructor() {

    private val bitmapCache = Collections.synchronizedList(mutableListOf<LargeObject>())
    private val listeners = mutableListOf<LoadListener>()

    fun loadBitmap(context: Context, listener: LoadListener) {
        listeners.add(listener)
        val stream = context.assets.open("img.jpg")
        GlobalScope.launch {
            val obj = LargeObject(BitmapFactory.decodeStream(stream))
            bitmapCache.add(obj)
            GlobalScope.launch(Dispatchers.Main) {
                listeners.forEach {
                    it.onLoadFinished(obj)
                }
            }
        }
    }


    companion object {
        val instance: LeakSingleton = LeakSingleton()
    }

    interface LoadListener {
        fun onLoadFinished(obj: LargeObject)
    }
}