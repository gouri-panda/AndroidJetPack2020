package com.example.plainolnotes4

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main_fragment.*

const val TAG = "MainFragment"

class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var noteListViewAdapter: NoteListViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        recyclerView.apply {
//            setHasFixedSize(true)
//            val divider = DividerItemDecoration(context,LinearLayoutManager(context).orientation)
//            addItemDecoration(divider)
//        }
        viewModel.noteList.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: note list $it")
            noteListViewAdapter = NoteListViewAdapter(it)
            Log.d(TAG, "onViewCreated: note view adapte size ${noteListViewAdapter.itemCount}")

            this.recyclerView.layoutManager = LinearLayoutManager(activity)
            this.recyclerView.adapter = noteListViewAdapter
            noteListViewAdapter.notifyDataSetChanged()
        }
        noteListViewAdapter = NoteListViewAdapter(SampleDataProvider.getNotes())
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = noteListViewAdapter

    }


}