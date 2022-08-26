package com.example.crud


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.e("token", "mi token es: " + token)
        guardarToken(token)


    }

    private fun guardarToken(token: String) {
        val ref: DatabaseReference = FirebaseDatabase.getInstance().reference.child("token")
        ref.child("firebase").setValue(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val de: String? = remoteMessage.from

        if (remoteMessage.data.size > 0) {
            Log.e("Tag", "Mi titulo es: " + remoteMessage.data.get("titulo"))
            Log.e("Tag", "Mi detalle  es: " + remoteMessage.data.get("detalle"))
            Log.e("Tag", "el color es: " + remoteMessage.data.get("color"))


        }


    }

    private fun mayorandroid6() {
        var id: String = "mensaje"
        var nm: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notification = NotificationCompat.Builder(this, id)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var nc = NotificationChannel(id, "nuevo", NotificationManager.IMPORTANCE_HIGH)
            nc.setShowBadge(true)
            nm.createNotificationChannel(nc)

        } else {
            notification.setAutoCancel(true).setWhen(
                System
                    .currentTimeMillis()
            )
                .setContentTitle("")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("")
                .setContentInfo("nuevo")
            val random = (0..8000).random()
            var intnotify: Int = random

            nm.notify(intnotify, notification.build())

            TODO("VERSION.SDK_INT < O")
        }


    }
}


//token : ct-E5Xu-QbWtSsmEssr5Fb:APA91bHKch0WnQ23roEYo8qjkz1ycusadJYIRfIVmBRBm9D8Ewq5LeXSfAAi5drlK-s7SKBogEMVzwovtWq5bHgVda6XiixY0pPFyTOsUifGmQ93yVrjW6yshuPFP06-WLGmT131mZyc