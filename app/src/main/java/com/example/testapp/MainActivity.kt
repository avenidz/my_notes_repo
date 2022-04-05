package com.example.testapp


import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.testapp.activity.AddTodoActivity
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.receiver.BroadcastNotes
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class MainActivity : AppCompatActivity() {

    private val CHANNELID = "channel_Id_01"
    private val notificationId = 101

    val br = BroadcastNotes()



    fun setUpViews(){
        var navController = findNavController(R.id.fragNavHost)
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavView)
        bottomNavView.setupWithNavController(navController)

        var appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.thisHome,
                R.id.thisCheck,
                R.id.thisUncheck,
            )
        )
        setupActionBarWithNavController(navController,appBarConfiguration)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);//disable night mode


        setContentView(R.layout.activity_main)
        setUpViews()

        sendBroadcast()
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(br)
    }
    private fun sendBroadcast(){
        val intentFilter = IntentFilter()
            intentFilter.addAction(Intent.ACTION_TIME_TICK)
            registerReceiver(br,intentFilter)
    }


}

