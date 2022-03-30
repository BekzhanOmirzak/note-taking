package com.android.notetaking.presentation.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.android.mydaggerproject.di.factory.ViewModelFactory
import com.android.notetaking.R
import com.android.notetaking.databinding.NoteDetailsFragmentBinding
import com.android.notetaking.presentation.ui.MainActivity
import com.android.notetaking.presentation.ui.viewBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Created by bekjan on 28.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */
class NoteDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val binding: NoteDetailsFragmentBinding by viewBinding(NoteDetailsFragmentBinding::bind)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.note_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingUpButtonClicks()


    }



    private fun settingUpButtonClicks() {
        with(binding) {

            btnBackButton.setOnClickListener {
                btnBackButton.startAnimation(animFadeInBack)
                (requireActivity() as MainActivity).navigate2HomeFragment()

            }

            noteAttach.setOnClickListener {
                noteAttach.startAnimation(animFadeInAttach)

            }
            noteSave.setOnClickListener {
                noteSave.startAnimation(animFadeInSave)



            }
            imgDelete.setOnClickListener {
                imgDelete.startAnimation(animFadeInBack)

            }
            noteDelete.setOnClickListener {
                noteDelete.startAnimation(animFadeInNoteDel)

            }

        }
    }


    private val animFadeInBack: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.imageview_effect)
    }
    private val animFadeInAttach: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.imageview_effect)
    }
    private val animFadeInSave: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.imageview_effect)
    }
    private val animFadeInNoteDel: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.imageview_effect)
    }

}