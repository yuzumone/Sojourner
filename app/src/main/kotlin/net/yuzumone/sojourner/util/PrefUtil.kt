package net.yuzumone.sojourner.util

import android.content.Context
import android.content.SharedPreferences

class PrefUtil(context: Context) {

    private val filename = "net.yuzumone.sojourner.prefs"
    private val date = "date"
    private val prefs: SharedPreferences = context.getSharedPreferences(filename, 0)

    var hackDate: Long
    get() = prefs.getLong(date, 0)
    set(value) = prefs.edit().putLong(date, value).apply()
}