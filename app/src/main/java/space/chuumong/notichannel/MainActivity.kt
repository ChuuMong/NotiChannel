package space.chuumong.notichannel

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_message -> {
                NotificationManager.sendNotification(
                    this,
                    1,
                    NotificationManager.Channel.MESSAGE,
                    "Message title",
                    "Message body"
                )
            }
            R.id.btn_post -> {
                NotificationManager.sendNotification(
                    this,
                    2,
                    NotificationManager.Channel.POST,
                    "Post title",
                    "Post body"
                )
            }
            R.id.btn_notice -> {
                NotificationManager.sendNotification(
                    this,
                    3,
                    NotificationManager.Channel.NOTICE,
                    "Notice title",
                    "Notice body"
                )
            }
            R.id.btn_delete_post -> {
                NotificationManager.deleteChannel(this, NotificationManager.Channel.POST)
            }
            R.id.btn_setting_message -> {
                showNotificationSetting(NotificationManager.Channel.MESSAGE)
            }
            R.id.btn_setting -> {
                showNotificationSetting()
            }
        }
    }

    @TargetApi(VERSION_CODES.O)
    private fun showNotificationSetting(channel: String = "") {
        val intent = if (channel.isEmpty()) {
            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        } else {
            Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_CHANNEL_ID, channel)
            }
        }

        intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        startActivity(intent)
    }
}
