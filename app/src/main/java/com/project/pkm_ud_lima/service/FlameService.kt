package com.project.pkm_ud_lima.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.project.pkm_ud_lima.R
import com.project.pkm_ud_lima.data.retrofit.ApiConfig
import com.project.pkm_ud_lima.ui.activity.MainActivity
import com.project.pkm_ud_lima.ui.activity.PeringatanActivity
import kotlinx.coroutines.*

class FlameService : Service() {
    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    private var shouldContinue = true

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopServiceIntent = Intent(this, FlameService::class.java)
        stopServiceIntent.action = "Matikan"
        val stopServicePendingIntent = PendingIntent.getService(
            this,
            0, stopServiceIntent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification: Notification = NotificationCompat.Builder(this, "flameServiceChannel")
            .setContentTitle("FireGuard")
            .setContentText("Mendeteksi api...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(R.drawable.ic_launcher_foreground, "Matikan", stopServicePendingIntent)
            .build()

        startForeground(1, notification)

        serviceScope.launch {
            while (shouldContinue) {
                fetchFlameData()
                delay(5000)  // delay for 5 seconds
            }
        }

        if (intent?.action == "Matikan") {
            shouldContinue = false
            stopSelf()
            return START_NOT_STICKY
        }

        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Flame Service Channel"
            val descriptionText = "Channel for flame service"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("flameServiceChannel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }

    private suspend fun fetchFlameData() {
        withContext(Dispatchers.IO) {
            val apiService = ApiConfig.getFlameService()
            try {
                val response = apiService.getFlame().execute()
                if (response.isSuccessful) {
                    val flameResponse = response.body()
                    if (flameResponse != null) {
                        val value = flameResponse.valueApi
                        val jarak = flameResponse.jarakApi
                        Log.d ("FlameService", "Value: $value, Jarak: $jarak")
                        if (value in 900..4095 || jarak in 0..50 && shouldContinue) {
                            sendNotification(
                                "Api terdeteksi!",
                                "Segera lakukan tindakan pencegahan."
                            )
                        } else {
                            Log.d ("FlameService", "Tidak ada api terdeteksi.")
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun sendNotification(title: String, message: String) {
        val notificationIntent: Intent
        var requestCode = 0

        if (title == "Api terdeteksi") {
            notificationIntent = Intent(this, PeringatanActivity::class.java)
            requestCode = 1
        } else {
            notificationIntent = Intent(this, MainActivity::class.java)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopServiceIntent = Intent(this, FlameService::class.java)
        val stopServicePendingIntent = PendingIntent.getService(
            this,
            0, stopServiceIntent, PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification: Notification = NotificationCompat.Builder(this, "flameServiceChannel")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(R.drawable.ic_launcher_foreground, "Matikan", stopServicePendingIntent)
            .build()

        startForeground(1, notification)
    }

}