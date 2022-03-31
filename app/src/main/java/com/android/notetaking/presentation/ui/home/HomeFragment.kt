package com.android.notetaking.presentation.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mydaggerproject.di.factory.ViewModelFactory
import com.android.notetaking.R
import com.android.notetaking.databinding.HomeFragmentBinding
import com.android.notetaking.domain.entities.NoteDto
import com.android.notetaking.presentation.tools.VLog
import com.android.notetaking.presentation.tools.showToastMessage
import com.android.notetaking.presentation.ui.MainActivity
import com.android.notetaking.presentation.ui.viewBinding
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
    private val homeViewModel by viewModels<HomeFragmentViewModel> { viewModelFactory }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        observingAllNotes()
    }

    private fun observingAllNotes() {
        lifecycleScope.launch {
            homeViewModel.allNotes.collect {
                it?.let {
                    setAllNotesToRecView(it)
                }
            }
        }
    }

    private fun setAllNotesToRecView(it: List<NoteDto>) {
        if (it.isEmpty()) {
            showEmptyNoteTxtHideRecView()
        } else {
            hideEmptyNoteShowRecView()
            noteAdapter.updateNotesList(it)
            binding.txtTitleToolbar.text = "Заметки : ${it.size}"
        }
    }


    private fun hideEmptyNoteShowRecView() {
        binding.apply {
            noNotesTxt.visibility = View.GONE
            noNoteAddTxt.visibility = View.GONE
            notesRecView.visibility = View.VISIBLE
        }
    }

    private fun showEmptyNoteTxtHideRecView() {
        binding.apply {
            noNotesTxt.visibility = View.VISIBLE
            noNoteAddTxt.visibility = View.VISIBLE
            notesRecView.visibility = View.GONE
        }
    }

    private fun settingNotesAdapter() {
        noteAdapter = NoteAdapter(requireContext(), this)
        binding.notesRecView.layoutManager = LinearLayoutManager(requireContext())
        binding.notesRecView.adapter = noteAdapter
    }

    private fun registerButtonClicks() {

        binding.apply {


            btnSearch.setOnClickListener {
                btnSearch.startAnimation(animFadeInSort)
                curActivity().showToastMessage("Поиск в требования не включен")
            }

            btnSort.setOnClickListener {
                btnSort.startAnimation(animFadeInSearch)
                curActivity().showToastMessage("Сортировка в требования не включен")
            }

            btnCreate.setOnClickListener {
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


    override fun onNoteClicked(note: NoteDto) {
        curActivity().navigate2NoteDetailsFragmentHor(note.id)
    }


    override fun checkedNotes(notes: List<NoteDto>) {
        binding.txtTitleToolbar.setText("Выбрано : ${notes.size}")
    }

    override fun onOnLongClicked() {
        if (!noteAdapter.checkedBoxVisible) {
            homeViewModel.observerAllNotes()
        }
    }

    private val animFadeInSort: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.imageview_effect)
    }
    private val animFadeInSearch: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.imageview_effect)
    }

}