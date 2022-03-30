package com.android.notetaking.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mydaggerproject.di.factory.ViewModelFactory
import com.android.notetaking.R
import com.android.notetaking.databinding.HomeFragmentBinding
import com.android.notetaking.domain.entities.NoteDto
import com.android.notetaking.presentation.ui.MainActivity
import com.android.notetaking.presentation.tools.VLog
import com.android.notetaking.presentation.ui.viewBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Created by bekjan on 28.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */
class HomeFragment : DaggerFragment(), NoteAdapter.NoteClickListener {

    private val binding: HomeFragmentBinding by viewBinding(HomeFragmentBinding::bind)


    lateinit var noteAdapter: NoteAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VLog.d("Injected ViewModelFactory : $viewModelFactory")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).settingUpToolBar(binding.toolbar)
        setHasOptionsMenu(true)
        registerButtonClicks()
        settingNotesAdapter()


    }


    private fun settingNotesAdapter() {
        noteAdapter = NoteAdapter(requireContext(), this)
        binding.notesRecView.layoutManager = LinearLayoutManager(requireContext())
        binding.notesRecView.adapter = noteAdapter
    }

    private fun registerButtonClicks() {

        binding.apply {
            btnBackButton.setOnClickListener {

            }
            btnSearch.setOnClickListener {
                btnSearch.startAnimation(animFadeInSort)
                showToastMessage("Поиск в требования не включен")
            }

            btnSort.setOnClickListener {
                btnSort.startAnimation(animFadeInSearch)
                showToastMessage("Сортировка в требования не включен")
            }

            btnFa.setOnClickListener {
                curActivity().navigate2NoteDetailsFragmentVer()
            }

        }

    }

    private fun curActivity(): MainActivity = requireActivity() as MainActivity

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

    override fun onNoteClicked(note: NoteDto) {
        curActivity().navigate2NoteDetailsFragmentHor()
    }

    private val animFadeInSort: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.imageview_effect)
    }
    private val animFadeInSearch: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.imageview_effect)
    }

}