package com.example.plainolnotes4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plainolnotes4.data.NoteEntity

open class NoteListViewAdapter(
    private val noteList: List<NoteEntity>,
    val onClick: (noteId: Int) -> Unit
) :
    RecyclerView.Adapter<NoteListViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = noteList[position].text
        holder.itemView.setOnClickListener {
            onClick(noteList[position].id)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.noteText)
    }
}
