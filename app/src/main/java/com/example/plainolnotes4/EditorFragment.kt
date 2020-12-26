package com.example.plainolnotes4

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.editor_fragment.*

class EditorFragment : Fragment() {


    private lateinit var viewModel: EditorViewModel
    private val args: EditorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHomeMenuButton()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    saveAndClose()
                }
            })
        return inflater.inflate(R.layout.editor_fragment, container, false)
    }

    private fun setHomeMenuButton() {
        (activity as AppCompatActivity).supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_check)
        }
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditorViewModel::class.java)
        Log.d(TAG, "onViewCreated: noteId ${args.noteId}")
        viewModel.currentNote.observe(viewLifecycleOwner, Observer {
            edit_text.setText(it.text)
        })
        viewModel.getNoteById(args.noteId)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> saveAndClose()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveAndClose(): Boolean {
        hidekeyBoard()
        viewModel.currentNote.value?.text = edit_text?.text.toString()
        viewModel.saveNote()
        return findNavController().navigateUp()
    }

    private fun hidekeyBoard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}