package com.yeh35.android.stop_it.page.permission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.yeh35.android.stop_it.BuildConfig
import com.yeh35.android.stop_it.R
import kotlinx.android.synthetic.main.activity_alert_window_permission.*
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime

/**
 * If you want to run this activity, use the {@function showWindowPermission} function.
 */
class AlertWindowPermissionActivity : AppCompatActivity() {

    companion object {
        private const val ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 2323

        private fun canWindowPermission(context: Context): Boolean {
            return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Settings.canDrawOverlays(context)
            } else {
                true
            }
        }

        fun showWindowPermission(context: Context) {
            if (!canWindowPermission(context)) {
                val startIntent = Intent(context, AlertWindowPermissionActivity::class.java)
                context.startActivity(startIntent)
            }
        }
    }

    private var lastBackPressedTime: DateTime = DateTime.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_window_permission)
    }

    fun onClickGranted(v: View) {
        if (BuildConfig.DEBUG && v != btn_granted) {
            error("Assertion failed")
        }
        requestPermission()
    }

    override fun onBackPressed() {
        if (lastBackPressedTime.plusMillis(1000).isBeforeNow) {
            lastBackPressedTime = DateTime.now()
            Snackbar.make(fragment_container, "요청하신 기능을 못 사용할 수도 있습니다.", Snackbar.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }

    private fun requestPermission() {
        // Check if Android M or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Show alert dialog to the user saying a separate permission is needed
            // Launch the settings activity if the user prefers
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + this.packageName)
            )
            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    Snackbar.make(fragment_container, "요청하신 기능을 못 사용할 수도 있습니다.", Snackbar.LENGTH_SHORT).show()
                } else {
                    // Permission Granted-System will work
                    finish()
                }
            }
        }
    }
}
