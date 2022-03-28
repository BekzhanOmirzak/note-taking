package com.android.notetaking.presentation.home

import android.content.Context
import android.os.Bundle
import android.os.Message
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.notetaking.R
import com.android.notetaking.databinding.HomeFragmentBinding
import com.android.notetaking.presentation.MainActivity
import com.android.notetaking.presentation.viewBinding

/**
 * Created by bekjan on 28.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */
class HomeFragment : Fragment(R.layout.home_fragment) {

    private val binding: HomeFragmentBinding by viewBinding(HomeFragmentBinding::bind)
    private val animFadeIn: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.imageview_effect)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context as MainActivity

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).settingUpToolBar(binding.toolbar)
        setHasOptionsMenu(true)
        registerButtonClicks()
    }

    private fun registerButtonClicks() {
        with(binding) {

            btnBackButton.setOnClickListener {

            }
            iconSearch.setOnClickListener {
                iconSearch.startAnimation(animFadeIn)
                showToastMessage("Эта функция в требования не включен")
            }

            iconSort.setOnClickListener {
                iconSort.startAnimation(animFadeIn)
                showToastMessage("Эта функция в требования не включен")
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }

}