package com.example.testapp.receiver


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.testapp.R
import java.util.*


class BroadcastNotes: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(Intent.ACTION_TIME_TICK == intent?.action){

            val presentTime = Calendar.getInstance()
            val timeMatch = Calendar.getInstance()
            presentTime.get(Calendar.HOUR_OF_DAY)
            presentTime.get(Calendar.MINUTE)
            timeMatch.set(Calendar.HOUR_OF_DAY, 7)
            timeMatch.set(Calendar.MINUTE, 1)
            //check time if match
            //set to 7am for now
            if(presentTime == timeMatch){
                val notifyUtils = NotificationUtils(context)
                //create notification
                notifyUtils.getNotificationBuilder()
                notifyUtils.getManager()
            }

        }

    }

}
class NotificationUtils(context: Context?) : ContextWrapper(context){

    private val CHANNEL_ID ="channel_Id_01"
    private val notificaionId = 101

    fun getNotificationBuilder(){
        val name = "Channel Name"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    fun getManager(){
//        val intent =Intent(this, AddTodoActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("My Notes App")
            .setContentText("Description")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .setContentIntent(pendingIntent)
        with(NotificationManagerCompat.from(this)){
            notify(notificaionId, builder.build())
        }
    }
}