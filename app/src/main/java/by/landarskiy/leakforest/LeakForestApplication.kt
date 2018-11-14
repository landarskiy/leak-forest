package by.landarskiy.leakforest

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Created by Landarskiy on 14.11.18.
 */
class LeakForestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}