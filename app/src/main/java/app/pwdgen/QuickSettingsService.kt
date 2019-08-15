package app.pwdgen


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.service.quicksettings.TileService
import android.util.Log
import android.content.Context.CLIPBOARD_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.content.ClipData
import android.widget.Toast
import app.pwdgen.ui.main.LENGTH


@SuppressLint("Override")
@TargetApi(Build.VERSION_CODES.N)
class QuickSettingsService : TileService() {

    /**
     * Called when the tile is added to the Quick Settings.
     * @return TileService constant indicating tile state
     */

    override fun onTileAdded() {
        Log.d("QS", "Tile added")
    }

    /**
     * Called when this tile begins listening for events.
     */
    override fun onStartListening() {
        Log.d("QS", "Start listening")
    }

    /**
     * Called when the user taps the tile.
     */
    override fun onClick() {
        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val pwdGenerator = PasswordGenerator(prefs.getBoolean(LOWER, true),
                prefs.getBoolean(UPPER, true),
                prefs.getBoolean(DIGITS, true),
                prefs.getBoolean(SPECIAL, true))
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Generated password", pwdGenerator.generate(prefs.getInt(LENGTH, 8)))
        clipboard.primaryClip = clip
        Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    /**
     * Called when this tile moves out of the listening state.
     */
    override fun onStopListening() {
        Log.d("QS", "Stop Listening")
    }

    /**
     * Called when the user removes this tile from Quick Settings.
     */
    override fun onTileRemoved() {
        Log.d("QS", "Tile removed")
    }

    companion object {

        private val SERVICE_STATUS_FLAG = "serviceStatus"
        private val PREFERENCES_KEY = "com.google.android_quick_settings"
    }
}