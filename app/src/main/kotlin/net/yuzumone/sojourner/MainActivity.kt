package net.yuzumone.sojourner

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import net.yuzumone.sojourner.databinding.ActivityMainBinding
import net.yuzumone.sojourner.service.SojournerJobService
import net.yuzumone.sojourner.util.PrefUtil
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val preference by lazy { PrefUtil(this) }
    private val handler = Handler()
    private val run = object : Runnable {
        override fun run() {
            init()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.buttonHack.setOnClickListener {
            preference.hackDate = Date().time
            update()
        }
        SojournerJobService.schedule(this)
    }

    override fun onResume() {
        super.onResume()
        handler.post(run)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(run)
    }

    private fun init() {
        val time = preference.hackDate
        if (time != 0L) {
            update()
        }
    }

    private fun update() {
        val nextCal = Calendar.getInstance()
        nextCal.time = Date(preference.hackDate)
        nextCal.add(Calendar.HOUR_OF_DAY, 24)
        val time = nextCal.time.time
        val now = Date().time
        if (now > time) {
            binding.textTime.text = getString(R.string.end)
        } else {
            val hour = (time - now) / (1000 * 60 * 60)
            val minute = (time - now) / (1000 * 60) % 60
            val h = if (hour < 10) "0$hour" else hour.toString()
            val m = if (minute < 10) "0$minute" else minute.toString()
            binding.textTime.text = "$h:$m"
        }
    }
}
