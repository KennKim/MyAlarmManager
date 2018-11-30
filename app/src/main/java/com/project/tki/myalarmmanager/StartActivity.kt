package com.project.tki.myalarmmanager

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.project.tki.myalarmmanager.databinding.ActivityStartBinding
import com.project.tki.myalarmmanager.swipe_layout.SwipeLayoutActivity
import com.project.tki.myalarmmanager.swipe_reveal.SwipeRevealActivity

class StartActivity : AppCompatActivity() {
      var b:     ActivityStartBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = DataBindingUtil.setContentView(this, R.layout.activity_start)
        b!!.activity = this
    }


    fun onClickAlarm(v: View) {
        val intent = Intent(this@StartActivity, MainActivity::class.java)
        startActivity(intent)

    }

    fun onClickSwipeReveal(v: View) {
        val intent = Intent(this@StartActivity, SwipeRevealActivity::class.java)
        startActivity(intent)
    }

    fun onClickSwipeLayout(v: View) {
        val intent = Intent(this@StartActivity, SwipeLayoutActivity::class.java)
        startActivity(intent)
    }
}
