package com.yeh35.android.stop_it.page.permission

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.AppOpsManager.MODE_ALLOWED
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AppOpsManager.OPSTR_GET_USAGE_STATS
import com.yeh35.android.stop_it.R
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.yeh35.android.stop_it.BuildConfig
import kotlinx.android.synthetic.main.activity_alert_window_permission.*
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime

class UsageStatsPermissionActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "UsageStatsPermissionActivity"
        private const val ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 2323

        private fun checkForPermission(context: Context): Boolean {
            val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager

            @Suppress("DEPRECATION")
            val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                appOps.unsafeCheckOpNoThrow(OPSTR_GET_USAGE_STATS, Process.myUid(), context.packageName)
            } else {
                appOps.checkOpNoThrow(OPSTR_GET_USAGE_STATS, Process.myUid(), context.packageName)
            }
            return mode == MODE_ALLOWED
        }

        fun showForPermission(context: Context) {
            if (!checkForPermission(context)) {
                val startIntent = Intent(context, UsageStatsPermissionActivity::class.java)
                context.startActivity(startIntent)
            }
        }
    }


    private var lastBackPressedTime: DateTime = DateTime.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usage_stats_permission)
    }

    override fun onBackPressed() {
        if (lastBackPressedTime.plusMillis(1000).isBeforeNow) {
            lastBackPressedTime = DateTime.now()
            Toast.makeText(this,"요청하신 기능을 못 사용할 수도 있습니다.", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }

    fun onClickGranted(v: View) {
        if (BuildConfig.DEBUG && v != btn_granted) {
            error("Assertion failed")
        }
        requestPermission()
    }

    @SuppressLint("LongLogTag")
    private fun requestPermission() {
        Log.d(TAG, "The user may not allow the access to apps usage. ")
        Toast.makeText(
            this,
            "Failed to retrieve app usage statistics. " +
                    "You may need to enable access for this app through " +
                    "Settings > Security > Apps with usage access",
            Toast.LENGTH_LONG
        ).show()
        startActivityForResult(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkForPermission(this)) {
                    Toast.makeText(this,"요청하신 기능을 못 사용할 수도 있습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    // Permission Granted-System will work
                    finish()
                }
            }
        }
    }
}