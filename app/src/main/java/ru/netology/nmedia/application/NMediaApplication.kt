package ru.netology.nmedia.application

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import ru.netology.nmedia.BuildConfig
import java.util.*

class NMediaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("${BuildConfig.API_KEY}")
        MapKitFactory.initialize(this);
    }
}