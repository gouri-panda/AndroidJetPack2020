package com.example.plainolnotes4

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main_fragment.*

const val TAG = "MainFragment"

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var noteListViewAdapter: NoteListViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        activity?.title = getString(R.string.app_name)
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.noteList?.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: note list $it")
            noteListViewAdapter = NoteListViewAdapter(
                it,
                { note -> onClick(note.id) },
                { activity?.invalidateOptionsMenu() }
            )

            Log.d(TAG, "onViewCreated: note view adapter size ${noteListViewAdapter.itemCount}")

            this.recyclerView.layoutManager = LinearLayoutManager(activity)
            this.recyclerView.adapter = noteListViewAdapter
            noteListViewAdapter.selectedNotes =
                viewModel.selectNoteList.value ?: mutableListOf()
            noteListViewAdapter.notifyDataSetChanged()
        }
        newNoteFloatingActionButton.setOnClickListener {
            onClick(NEW_NOTE_ID)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuId =
            if (this::noteListViewAdapter.isInitialized && noteListViewAdapter.selectedNotes.isNotEmpty())
                R.menu.menu_main_selected else R.menu.menu_main
        inflater.inflate(menuId, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sample_note -> {
                viewModel.addSampleData()
                return true
            }
            R.id.action_sample_note_delete -> deleteSelectNotes()
            R.id.delete_all_notes_from_menu -> deleteAllNotes()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteAllNotes(): Boolean {
        return viewModel.deleteAllNotes()
    }

    private fun deleteSelectNotes(): Boolean {
        viewModel.deleteSelectNotes(noteListViewAdapter.selectedNotes) {
            noteListViewAdapter.selectedNotes.clear()
            activity?.invalidateOptionsMenu()
        }
        return true

    }

    private fun onClick(noteId: Int) {
        Log.d(TAG, "onViewCreated: item clicked $noteId")
        val directions =
            MainFragmentDirections.actionMainFragmentToEditorFragment(
                noteId = noteId
            )
        findNavController().navigate(directions)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (this::viewModel.isInitialized) {
            viewModel.selectNoteList.value = noteListViewAdapter.selectedNotes
        }
        super.onSaveInstanceState(outState)
    }


}