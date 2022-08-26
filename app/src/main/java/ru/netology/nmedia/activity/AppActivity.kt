package ru.netology.nmedia.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.yandex.runtime.Runtime
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.FragmentNewMarkerByCoordinates.Companion.doubleArg1
import ru.netology.nmedia.activity.FragmentNewMarkerByCoordinates.Companion.doubleArg2
import ru.netology.nmedia.activity.FragmentNewMarkerByCoordinates.Companion.textArg

class AppActivity : AppCompatActivity(R.layout.activity_app) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}