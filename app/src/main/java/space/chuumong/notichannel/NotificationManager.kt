package space.chuumong.notichannel

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.annotation.StringDef
import androidx.core.content.getSystemService
import kotlin.annotation.Retention

object NotificationManager {

    @Retention(AnnotationRetention.SOURCE)
    @StringDef(Channel.MESSAGE, Channel.POST, Channel.NOTICE)
    annotation class Channel {

        companion object {
            const val MESSAGE = "message"
            const val POST = "post"
            const val NOTICE = "notice"
        }
    }

    private const val NOTIFICATION_CHANNEL_TEST_GROUP = "Test Group"

    @RequiresApi(VERSION_CODES.O)
    fun createChannel(context: Context) {
        val manager = context.getSystemService<NotificationManager>() ?: return
        val channelGroup = NotificationChannelGroup(NOTIFICATION_CHANNEL_TEST_GROUP, NOTIFICATION_CHANNEL_TEST_GROUP)
        manager.createNotificationChannelGroup(channelGroup)

        val channelMessage = NotificationChannel(
            Channel.MESSAGE,
            context.getString(R.string.notification_channel_message_title),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.getString(R.string.notification_channel_message_description)
            group = NOTIFICATION_CHANNEL_TEST_GROUP
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            enableLights(true)
            lightColor = Color.GREEN
            enableVibration(true)
            vibrationPattern = longArrayOf(0, 1000, 500, 1000)
        }

        val channelPost = NotificationChannel(
            Channel.POST,
            context.getString(R.string.notification_channel_post_title),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.getString(R.string.notification_channel_post_description)
            group = NOTIFICATION_CHANNEL_TEST_GROUP
            lightColor = Color.BLUE
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        }

        val channelNotice = NotificationChannel(
            Channel.NOTICE,
            context.getString(R.string.notification_channel_notice_title),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = context.getString(R.string.notification_channel_notice_description)
            group = NOTIFICATION_CHANNEL_TEST_GROUP
            lightColor = Color.RED
            lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        }

        manager.createNotificationChannels(listOf(channelMessage, channelPost, channelNotice))
    }

    @RequiresApi(VERSION_CODES.O)
    fun sendNotification(context: Context, id: Int, @Channel channel: String, title: String, body: String) {
        val manager = context.getSystemService<NotificationManager>() ?: return
        val builder = Notification.Builder(context, channel)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(getSmallIcon())
            .setAutoCancel(true)

        manager.notify(id, builder.build())
    }

    @RequiresApi(VERSION_CODES.O)
    fun deleteChannel(context: Context, @Channel channel: String) {
        val manager = context.getSystemService<NotificationManager>() ?: return
        manager.deleteNotificationChannel(channel)
    }

    private fun getSmallIcon(): Int {
        return android.R.drawable.stat_notify_chat
    }
}