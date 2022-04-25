package com.android.notetaking.presentation.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.android.notetaking.R
import com.android.notetaking.databinding.ActivityMainBinding
import com.android.notetaking.presentation.tools.OnSwipeTouchListener
import com.android.notetaking.presentation.tools.VLog
import com.android.notetaking.presentation.ui.details.NoteDetailsFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : DaggerAppCompatActivity() {


    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    val navController by lazy { findNavController(R.id.main_nav) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.rootLinearLayout.setOnTouchListener(object : View.OnTouchListener {

            private val gestureDetector: GestureDetector


            private val SWIPE_THRESHOLD = 100
            private val SWIPE_VELOCITY_THRESHOLD = 100

            init {
                gestureDetector = GestureDetector(this@MainActivity, GestureListener())
            }

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                return gestureDetector.onTouchEvent(event)
            }

            private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {


                override fun onDown(e: MotionEvent): Boolean {
                    return true
                }

                override fun onFling(
                    e1: MotionEvent,
                    e2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    var result = false
                    try {
                        val diffY = e2.y - e1.y
                        val diffX = e2.x - e1.x
                        if (Math.abs(diffX) > Math.abs(diffY)) {
                            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                                if (diffX > 0) {
                                    onSwipeRight()
                                } else {
                                    onSwipeLeft()
                                }
                                result = true
                            }
                        } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffY > 0) {
                                onSwipeBottom()
                            } else {
                                onSwipeTop()
                            }
                            result = true
                        }
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }

                    return result
                }


            }

            fun onSwipeRight() {}

            fun onSwipeLeft() {}

            fun onSwipeTop() {}

            fun onSwipeBottom() {
                VLog.d("SwipeBottom got called ")
            }

        })
    }

    fun settingUpToolBar(toolBar: androidx.appcompat.widget.Toolbar) {
        setSupportActionBar(toolBar)
        toolBar.overflowIcon?.setTint(Color.WHITE)
    }

    fun navigate2NoteDetailsFragmentHor(idNote: Int) {
        val bundle = bundleOf(NoteDetailsFragment.idNote to idNote)
        navController.navigate(R.id.action_homeFragment_to_noteDetailsFragment_hor, bundle)
    }

    fun navigate2NoteDetailsFragmentVer() {
        navController.navigate(R.id.action_homeFragment_to_noteDetailsFragment_ver)
    }

    fun navigate2HomeFragment() {
        navController.navigate(R.id.action_noteDetailsFragment_to_homeNoteFragment)
    }

    override fun onBackPressed() {
        val curFragment = navController.currentDestination?.id
        if (curFragment == R.id.noteDetailsFragment) {
            navigate2HomeFragment()
        } else {
            super.onBackPressed()
        }
    }


}