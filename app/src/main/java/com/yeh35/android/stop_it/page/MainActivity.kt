package com.yeh35.android.stop_it.page

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.siblingelement.location_alarm_android_app.ui.baase.BaseActivity
import com.siblingelement.location_alarm_android_app.util.preference.SharedPreferenceKey
import com.yeh35.android.stop_it.R
import com.yeh35.android.stop_it.broadcast.ScreenReceiver
import com.yeh35.android.stop_it.page.home.HomeFragment
import com.yeh35.android.stop_it.page.user.UserFragment
import com.yeh35.android.stop_it.util.preference.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime


class MainActivity : BaseActivity(), View.OnClickListener {

    //TODO 나중에 옮겨야할지도.
    val ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 2323

    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private var viewStatus: ViewStatus? = null
    private var onHomeBackPressedTime: DateTime = DateTime.now()
    private val homeFragment = HomeFragment()
    private val userFragment = UserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_home.setOnClickListener(this)
        btn_user.setOnClickListener(this)

        replaceFragment(ViewStatus.HOME)

        ScreenReceiver.registerReceiver(this)

        sharedPreferenceManager = SharedPreferenceManager(this)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Settings.canDrawOverlays(this)) {
            RequestPermission()
        }
    }

    override fun onStart() {
        super.onStart()
        sharedPreferenceManager.set(SharedPreferenceKey.IS_MAIN_RUNNING, true)
    }

    override fun onStop() {
        super.onStop()
        sharedPreferenceManager.set(SharedPreferenceKey.IS_MAIN_RUNNING, false)
    }

    override fun onClick(v: View?) {
        when (v) {
            btn_home -> {
                replaceFragment(ViewStatus.HOME)
            }
            btn_user -> {
                replaceFragment(ViewStatus.USER)
            }
            else -> {
                error("정의 하지 않은 View 입니다.")
            }
        }
    }

    override fun onBackPressed() {
        if (viewStatus == ViewStatus.HOME) {
            if (onHomeBackPressedTime.plusMillis(1000).isBeforeNow) {
                onHomeBackPressedTime = DateTime.now()
                Snackbar.make(fragment_container, "한번더 누르면 앱이 종료됩니다.", Snackbar.LENGTH_SHORT).show()
            } else {
                finish()
            }

        } else {
            replaceFragment(ViewStatus.HOME)
        }
    }

    private fun replaceFragment(viewStatus: ViewStatus) {
        if (this.viewStatus == viewStatus) {
            return
        }

        this.viewStatus = viewStatus

        when (viewStatus) {
            ViewStatus.HOME -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, homeFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            ViewStatus.USER -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, userFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            else -> {
                error("정의되지 않은 상태입니다.")
            }
        }

        btn_home.setImageResource(if (viewStatus == ViewStatus.HOME) R.drawable.icons_home_full else R.drawable.button_home_states)
        btn_user.setImageResource(if (viewStatus == ViewStatus.USER) R.drawable.icons_user_full else R.drawable.button_user_states)
    }

    private enum class ViewStatus {
        HOME,
        USER
    }

    private fun RequestPermission() {
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
//                    PermissionDenied()
                    Log.d("권한", "권한을 못받았습니다.")
                } else {
                    // Permission Granted-System will work
                }
            }
        }
    }



}