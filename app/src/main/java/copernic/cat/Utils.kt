package copernic.cat

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class Utils {
    companion object {
         fun notification(titulo:String, mensaje:String, context: Context) {
            val notification = NotificationCompat.Builder(context,"1").also{ noti ->
                noti.setContentTitle(titulo)
                noti.setContentText(mensaje)
                noti.setSmallIcon(R.drawable.logo)
            }.build()
            val notificationManageer = NotificationManagerCompat.from(context)
            notificationManageer.notify(1,notification)
        }
    }
}