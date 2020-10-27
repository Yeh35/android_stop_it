package com.yeh35.android.stop_it.page

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.yeh35.android.stop_it.page.baase.BaseActivity
import com.yeh35.android.stop_it.util.preference.SharedPreferenceKey
import com.yeh35.android.stop_it.R
import com.yeh35.android.stop_it.page.home.HomeFragment
import com.yeh35.android.stop_it.page.permission.AlertWindowPermissionActivity
import com.yeh35.android.stop_it.page.user.UserFragment
import com.yeh35.android.stop_it.service.OnLockService
import com.yeh35.android.stop_it.util.preference.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime


@Suppress("REDUNDANT_ELSE_IN_WHEN")
class MainActivity : BaseActivity(), View.OnClickListener {

    private lateinit var sharedPreferenceManager: SharedPreferenceManager
    private var viewStatus: ViewStatus? = null
    private var onHomeBackPressedTime: DateTime = DateTime.now()
    private val homeFragment = HomeFragment()
    private val userFragment = UserFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_home.setOnClickListener(this)
//        btn_user.setOnClickListener(this)

        replaceFragment(ViewStatus.HOME)

        sharedPreferenceManager = SharedPreferenceManager(this)

        if (!AlertWindowPermissionActivity.checkWindowPermission(this)) {
            AlertWindowPermissionActivity.showWindowPermission(this)
        }

        // TODO 서비스 serviceIntent로 체크하는게 아니라 현제 등록된 서비스를 읽어와서 체크 해야함
        if (OnLockService.serviceIntent == null) {
            val foregroundServiceIntent = Intent(this, OnLockService::class.java)
            startService(foregroundServiceIntent)
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
//            btn_user -> {
//                replaceFragment(ViewStatus.USER)
//            }
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
        //btn_user.setImageResource(if (viewStatus == ViewStatus.USER) R.drawable.icons_user_full else R.drawable.button_user_states)
    }

    private enum class ViewStatus {
        HOME,
        USER
    }
}