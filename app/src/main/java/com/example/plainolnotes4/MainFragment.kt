package com.example.plainolnotes4

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.noteList.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: note list $it")
            noteListViewAdapter = NoteListViewAdapter(it) { noteId ->
                Log.d(TAG, "onViewCreated: item clicked $noteId")
                val directions =
                    MainFragmentDirections.actionMainFragmentToEditorFragment(
                        noteId = noteId
                    )
                findNavController().navigate(directions)
            }
            Log.d(TAG, "onViewCreated: note view adapte size ${noteListViewAdapter.itemCount}")

            this.recyclerView.layoutManager = LinearLayoutManager(activity)
            this.recyclerView.adapter = noteListViewAdapter
        }
    }
}