package com.example.primetea

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.primetea.R

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(ctx: Context, intent: Intent) {
        val texto = intent.getStringExtra("texto") ?: "¡Entrenamiento!"
        val permisoOk = Build.VERSION.SDK_INT < 33 ||
                ContextCompat.checkSelfPermission(
                    ctx, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
        if (!permisoOk) return

        val notif = NotificationCompat.Builder(ctx, "CALENDAR_CH")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(texto)
            .setContentText("¡Hora de entrenar!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(ctx).notify(texto.hashCode(), notif)
    }
}
