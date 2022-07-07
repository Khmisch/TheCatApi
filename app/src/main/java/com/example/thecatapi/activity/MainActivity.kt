package com.example.thecatapi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.thecatapi.R
import com.example.thecatapi.fragment.AllCatsFragment
import com.example.thecatapi.fragment.MyCatsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fl_Fragment: FrameLayout
    private lateinit var bt_add: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        throw RuntimeException("Test Crash") // Force a crash

        initViews()

    }

    private fun initViews() {
        val allCatsFragment = AllCatsFragment()
        val myCatsFragment = MyCatsFragment()

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fl_Fragment = findViewById(R.id.fl_Fragment)
        bt_add = findViewById(R.id.bt_add)

        replaceFragment(allCatsFragment)

        bt_add.setOnClickListener {
            callCreateActivity()
        }
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_all -> replaceFragment(allCatsFragment)
                R.id.menu_my -> replaceFragment(myCatsFragment)

            }
            true
        }
    }

    private fun callCreateActivity() {
        var intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)
    }

    fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val manager = supportFragmentManager
        val fragmentPopped = manager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped) {
            val ft = manager.beginTransaction()
            ft.replace(R.id.fl_Fragment, fragment)
            ft.addToBackStack(backStateName)
            ft.commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1)
            finish()
        else
            super.onBackPressed()
    }
}