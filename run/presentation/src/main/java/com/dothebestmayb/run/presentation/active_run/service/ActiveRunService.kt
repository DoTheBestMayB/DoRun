package com.dothebestmayb.run.presentation.active_run.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import com.dothebestmayb.core.presentation.ui.formatted
import com.dothebestmayb.run.domain.RunningTracker
import com.dothebestmayb.run.presentation.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject

class ActiveRunService : Service() {

    private val notificationManager by lazy {
        getSystemService<NotificationManager>()!!
    }

    private val baseNotification by lazy {
        NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setOnlyAlertOnce(true)
            .setSmallIcon(com.dothebestmayb.core.presentation.designsystem.R.drawable.logo)
            .setContentTitle(getString(R.string.active_run))
    }

    private val runningTracker by inject<RunningTracker>()

    private var serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                val activityClass = intent.getStringExtra(EXTRA_ACTIVITY_CLASS)
                    ?: throw IllegalArgumentException("No activity class provided")
                start(Class.forName(activityClass))
            }

            ACTION_STOP -> stop()
        }
        return START_STICKY
    }

    private fun start(activityClass: Class<*>) {
        if (!isServiceActive) {
            isServiceActive = true
            createNotificationChannel()

            /**
             * 알림창을 누르면 active_run 화면으로 바로 이동하도록 deep link를 이용
             * 단순히 Intent를 이용할 수 없는 이유는 다음과 같다.
             * 1. MainActivity의 특정 screen을 요청할 수 없음
             * 2. 앱이 최근 목록에서 swipe out 되더라도, active_run 화면으로 이동할 수 있어야 함
             *  이때는 MainActivity의 첫 화면이 보이게 됨
             *
             * Deep Link를 수신할 수 있도록 App 모듈의 AndroidManifest intent-filter에 등록함
             */
            // App 모듈에 대한 의존성이 없기 때문에 MainActivity를 지칭할 수 없음
            // App 모듈이 run 모듈에 대한 의존성이 있기 때문에, run 모듈 내부에 있는 모듈에서 App 모듈에 대한 의존성을 가질 수 없음 -> 추가하면 순환 모듈 문제 발생
            // 그래서 이 코드를 호출하게 될 App 모듈에서 MainActivity 클래스를 파라미터로 전달하도록 설계함
            val activityIntent = Intent(applicationContext, activityClass).apply {
                // 일반적으로 웹브라우저를 통해서 진입할 수 있도록 URL 형식으로 작성하지만
                // 그러한 목적으로 만든 앱은 아니므로 아래와 같이 작성함
                data = "dorun://active_run".toUri()
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            val pendingIntent = TaskStackBuilder.create(applicationContext).run {
                addNextIntentWithParentStack(activityIntent)
                getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
            }
            val notification = baseNotification
                .setContentText("00:00:00")
                .setContentIntent(pendingIntent)
                .build()

            // id로 0은 지정하면 안 됨
            startForeground(NOTIFICATION_ID, notification)
            updateNotification()
        }
    }

    private fun updateNotification() {
        runningTracker.elapsedTime.onEach { elapsedTime ->
            val notification = baseNotification
                .setContentText(elapsedTime.formatted())
                .build()

            notificationManager.notify(NOTIFICATION_ID, notification)
        }.launchIn(serviceScope)
    }

    fun stop() {
        stopSelf()
        isServiceActive = false
        serviceScope.cancel()

        serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            getString(R.string.active_run),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        var isServiceActive = false

        private const val CHANNEL_ID = "active_run"
        private const val NOTIFICATION_ID = 1

        private const val ACTION_START = "ACTION_START"
        private const val ACTION_STOP = "ACTION_STOP"

        private const val EXTRA_ACTIVITY_CLASS = "EXTRA_ACTIVITY_CLASS"

        fun createStartIntent(context: Context, activityClass: Class<*>): Intent {
            return Intent(context, ActiveRunService::class.java).apply {
                action = ACTION_START
                putExtra(EXTRA_ACTIVITY_CLASS, activityClass.name)
            }
        }

        fun createStopIntent(context: Context): Intent {
            return Intent(context, ActiveRunService::class.java).apply {
                action = ACTION_STOP
            }
        }
    }
}
