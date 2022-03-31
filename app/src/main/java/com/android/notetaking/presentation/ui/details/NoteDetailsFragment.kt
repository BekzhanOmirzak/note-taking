package com.android.notetaking.presentation.ui.details

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.android.mydaggerproject.di.factory.ViewModelFactory
import com.android.notetaking.R
import com.android.notetaking.databinding.NoteDetailsFragmentBinding
import com.android.notetaking.domain.entities.NoteDto
import com.android.notetaking.presentation.tools.STORAGE_PERMISSIONS
import com.android.notetaking.presentation.tools.VLog
import com.android.notetaking.presentation.tools.showToastMessage
import com.android.notetaking.presentation.ui.MainActivity
import com.android.notetaking.presentation.ui.viewBinding
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by bekjan on 28.03.2022.
 * email: bekjan.omirzak98@gmail.com
 */
class NoteDetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val binding: NoteDetailsFragmentBinding by viewBinding(NoteDetailsFragmentBinding::bind)
    var idNote = -1

    private val noteDetailsViewModel: NoteDetailsViewModel by viewModels { viewModelFactory }
    var noteDto = NoteDto(0, "", "", 0L, 0L, null)

    companion object {
        const val idNote: String = "idNote"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idNote = it.getInt(NoteDetailsFragment.idNote)
        }
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
        if (idNote != -1) {
            noteDetailsViewModel.getNoteById(idNote)
            observingNoteById()
        }
    }

    private fun observingNoteById() {
        lifecycleScope.launch {
            noteDetailsViewModel.noteById.collect {
                it?.let {
                    settingUpNoteDetails(it)
                }
            }
        }
    }

    private fun settingUpNoteDetails(note: NoteDto) {
        binding.apply {
            edtNoteTitle.setText(note.title)
            edtNoteDetails.setText(note.content)
            if (note.noteImage != null) {
                settingBitMap2ImageView(note.noteImage!!)
            }
            noteDto = NoteDto(
                note.id,
                note.title,
                note.content,
                note.dateCreated,
                note.dateUpdated,
                note.noteImage
            )
        }
    }


    private fun settingUpButtonClicks() {
        with(binding) {

            btnBackButton.setOnClickListener {
                btnBackButton.startAnimation(animFadeInBack)
                (requireActivity() as MainActivity).navigate2HomeFragment()

            }

            noteAttach.setOnClickListener {
                noteAttach.startAnimation(animFadeInAttach)
                if (checkStoragePermission()) {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "image/*"
                    val chooser = Intent.createChooser(intent, "Выбирите фотку")
                    requestChooseImage.launch(chooser)
                } else {
                    askStoragePermission()
                }
            }
            noteSave.setOnClickListener {
                noteSave.startAnimation(animFadeInSave)
                if (!parseNoteDetailsFromEditTextAndSave())
                    curActivity().showToastMessage("Название и текст обо не может быть пустой!")

            }
            imgDelete.setOnClickListener {
                imgDelete.startAnimation(animFadeInDelImgIcon)
                deleteChosenImage()
            }
            noteDelete.setOnClickListener {
                noteDelete.startAnimation(animFadeInNoteDel)
                deleteNoteFromDb()
            }

        }
    }

    private fun deleteNoteFromDb() {
        if (idNote != -1) {
            noteDetailsViewModel.deleteNote(noteDto)
            curActivity().navigate2HomeFragment()
        }
    }

    private fun parseNoteDetailsFromEditTextAndSave(): Boolean {
        binding.apply {
            val title = edtNoteTitle.text.toString()
            val content = edtNoteDetails.text.toString()
            noteDto.content = content
            noteDto.title = title
            noteDto.dateUpdated = System.currentTimeMillis()
            if (!title.isEmpty() || !content.isEmpty()) {
                if (idNote == -1) {
                    noteDto.dateCreated = System.currentTimeMillis()
                    noteDetailsViewModel.insertNote(noteDto)
                } else {
                    noteDetailsViewModel.updateNote(noteDto)
                }
                curActivity().navigate2HomeFragment()
                return true
            }
            return false
        }
    }

    private fun curActivity(): MainActivity = requireActivity() as MainActivity

    private fun deleteChosenImage() {
        binding.apply {
            imgDelete.visibility = View.GONE
            noteImage.visibility = View.GONE
            noteDto.noteImage = null
        }
    }


    private fun askStoragePermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            val intent = Intent()
            intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
            requestManageAllFiles.launch(intent)
        } else {
            requestPermissions.launch(STORAGE_PERMISSIONS)
        }
    }

    private fun checkStoragePermission(): Boolean {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager()
        } else {
            STORAGE_PERMISSIONS.forEach {
                val enabled = ContextCompat.checkSelfPermission(
                    requireActivity(),
                    it
                ) == PackageManager.PERMISSION_GRANTED
                if (!enabled)
                    return false
            }
        }
        return true
    }

    private val requestManageAllFiles =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }
    private val requestPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

        }

    private val requestChooseImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                setUpImageViewFromUri(it.data)
            }
        }

    private fun setUpImageViewFromUri(intent: Intent?) {
        if (intent == null) return
        val imageUri = intent.data ?: return
        kotlin.runCatching {
            val bitMap = if (SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        requireContext().contentResolver,
                        imageUri
                    )
                )
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
            }
            settingBitMap2ImageView(bitMap = bitMap)
        }.onFailure {
            VLog.d("Exception occured in setting image to view : ${it.message}")
        }
    }

    private fun settingBitMap2ImageView(bitMap: Bitmap) {
        binding.apply {
            noteImage.setImageBitmap(bitMap)
            imgDelete.visibility = View.VISIBLE
            noteImage.visibility = View.VISIBLE
        }
        noteDto.noteImage = bitMap
    }


    private val animFadeInBack: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.imageview_effect)
    }
    private val animFadeInDelImgIcon: Animation by lazy {
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