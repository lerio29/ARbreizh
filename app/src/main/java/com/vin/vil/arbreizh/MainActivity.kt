package com.vin.vil.arbreizh

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import javax.swing.text.View



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    fun maybeEnableArButton() {
        val availability = ArCoreApk.getInstance().checkAvailability(this)
        if (availability.isTransient()) {
            // Re-query at 5Hz while compatibility is checked in the background.
            Handler().postDelayed(object : Runnable {
                override fun run() {
                    maybeEnableArButton()
                }
            }, 200)
        }
        if (availability.isSupported()) {
            mArButton.setVisibility(View.VISIBLE)
            mArButton.setEnabled(true)
            // indicator on the button.
        } else { // Unsupported or unknown.
            mArButton.setVisibility(View.INVISIBLE)
            mArButton.setEnabled(false)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        // Enable AR related functionality on ARCore supported devices only.
        maybeEnableArButton()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
