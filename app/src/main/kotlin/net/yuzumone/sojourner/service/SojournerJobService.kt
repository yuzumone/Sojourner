package net.yuzumone.sojourner.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import net.yuzumone.sojourner.MainActivity
import net.yuzumone.sojourner.R
import net.yuzumone.sojourner.util.PrefUtil
import java.util.*

class SojournerJobService : JobService() {

    companion object {
        const val ID: Int = 3939
        fun schedule(context: Context) {
            val scheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val job = JobInfo.Builder(ID, ComponentName(context, SojournerJobService::class.java))
                    .setPeriodic(60 * 60 * 1000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
                    .build()
            scheduler.schedule(job)
        }
    }

    override fun onStartJob(params: JobParameters): Boolean {
        Thread(Runnable {
            if (check()) {
                notification()
            }
            jobFinished(params, true)
        }).start()
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        jobFinished(params, true)
        return false
    }

    private fun check(): Boolean {
        val preference = PrefUtil(applicationContext)
        val end = Calendar.getInstance().apply {
            time = Date(preference.hackDate)
            add(Calendar.HOUR_OF_DAY, 24)
        }.time.time
        val start = Calendar.getInstance().apply {
            time = Date(preference.hackDate)
            add(Calendar.HOUR_OF_DAY, 20)
        }.time.time
        val now = Date().time
        return (end > now) && (now > start)
    }

    private fun notification() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notification = NotificationCompat.Builder(this, "id")
                .setSmallIcon(R.drawable.ic_sojourner)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.might_stop))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .build()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, notification)
    }
}