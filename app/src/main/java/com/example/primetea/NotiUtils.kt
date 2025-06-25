package com.example.primetea

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

object NotiUtils {
    fun crearCanal(ctx: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                "CALENDAR_CH",
                "Recordatorios de entrenamiento",
                NotificationManager.IMPORTANCE_HIGH
            ).apply { enableVibration(true) }
            val nm = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(canal)
        }
    }

    fun programar(ctx: Context, ev: EventEntity) {
        // Construye el instante en millis
        val triggerMillis = LocalDateTime
            .of(ev.fecha, LocalTime.of(ev.hora24, ev.minuto))
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        // No programes alarmas en el pasado
        if (triggerMillis <= System.currentTimeMillis()) return

        val intent = Intent(ctx, ReminderReceiver::class.java)
            .putExtra("texto", ev.texto)
        val pi = PendingIntent.getBroadcast(
            ctx,
            ev.fecha.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val am = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // En Android 12+ comprueba permiso de exact alarms
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !am.canScheduleExactAlarms()) {
            // aquí podrías mostrar un diálogo o redirigir a ajustes, pero simplemente salimos
            return
        }

        try {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerMillis, pi)
        } catch (_: SecurityException) {
            // si no tiene permiso, lo ignoramos
        }
    }
}
