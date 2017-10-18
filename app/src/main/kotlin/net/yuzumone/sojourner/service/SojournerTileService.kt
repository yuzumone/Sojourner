package net.yuzumone.sojourner.service

import android.content.Intent
import android.service.quicksettings.TileService
import net.yuzumone.sojourner.ShortcutsActivity

class SojournerTileService : TileService() {

    override fun onClick() {
        val intent = Intent(this, ShortcutsActivity::class.java)
        startActivityAndCollapse(intent)
    }
}