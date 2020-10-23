package com.yeh35.android.stop_it.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.yeh35.android.stop_it.R
import com.yeh35.android.stop_it.page.home.HomeFragment
import com.yeh35.android.stop_it.page.user.UserFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.DateTime

class MainActivity : AppCompatActivity(), View.OnClickListener {

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

}