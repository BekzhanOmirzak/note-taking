package com.android.notetaking.presentation.ui

import android.graphics.Color
import android.os.Bundle
import androidx.navigation.findNavController
import com.android.notetaking.R
import com.android.notetaking.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private val navController by lazy { findNavController(R.id.main_nav) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


    fun settingUpToolBar(toolBar: androidx.appcompat.widget.Toolbar) {
        setSupportActionBar(toolBar)
        toolBar.overflowIcon?.setTint(Color.WHITE)
    }

    fun navigate2NoteDetailsFragment() {
        navController.navigate(R.id.action_homeFragment_to_noteDetailsFragment)
    }

    fun navigate2HomeFragment() {
        navController.navigate(R.id.action_noteDetailsFragment_to_homeNoteFragment)
    }

    override fun onBackPressed() {
        val curFragment = navController.currentDestination?.id
        if (curFragment == R.id.noteDetailsFragment) {
            navigate2HomeFragment()
        } else
            super.onBackPressed()
    }


}