package net.yuzumone.sojourner

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import net.yuzumone.sojourner.util.PrefUtil
import java.util.*

class ShortcutsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pref = PrefUtil(this)
        pref.hackDate = Date().time
        Toast.makeText(this, R.string.hack_the_portal, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}