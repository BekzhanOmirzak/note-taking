package com.android.notetaking.presentation

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import com.android.notetaking.R
import com.android.notetaking.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

    fun settingUpToolBar(toolBar:androidx.appcompat.widget.Toolbar) {
        setSupportActionBar(toolBar)
        toolBar.overflowIcon?.setTint(Color.WHITE)
    }



}