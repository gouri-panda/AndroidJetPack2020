package com.example.plainolnotes4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plainolnotes4.data.NoteEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton

open class NoteListViewAdapter(
    private val noteList: List<NoteEntity>,
    val onClick: (note: NoteEntity) -> Unit,
    val onLongClick: () -> Unit
) :
    RecyclerView.Adapter<NoteListViewAdapter.ViewHolder>() {
    var selectedNotes = mutableListOf<NoteEntity>()
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
            onClick(noteList[position])
        }
        holder.itemView.setOnLongClickListener {
            val note = noteList[position]
            if (selectedNotes.contains(note)) {
                selectedNotes.remove(note)
                holder.fabButton.setImageResource(R.drawable.ic_add)
            } else {
                selectedNotes.add(note)
                holder.fabButton.setImageResource(R.drawable.ic_check)
            }
            //Recycler view recycles itemView so we need to store the persistence of imageView
            holder.fabButton.setImageResource(if (selectedNotes.contains(note)) R.drawable.ic_check else R.drawable.ic_add)
            onLongClick()
            return@setOnLongClickListener true
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.noteText)
        val fabButton: FloatingActionButton = itemView.findViewById(R.id.floatingActionButton)
    }
}
