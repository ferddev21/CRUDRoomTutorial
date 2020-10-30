package com.pcsferddev21.crudroomtutorial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pcsferddev21.crudroomtutorial.room.Note
import kotlinx.android.synthetic.main.adapter_note.view.*

class NoteAdapter (private val note: ArrayList<Note>, private val listener: OnAdapterListener ) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.adapter_note,parent,false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       val note = note[position]
        holder.view.text_title.text = note.title
        holder.view.text_title.setOnClickListener{
            listener.OnClick(note)
        }

        holder.view.icon_edit.setOnClickListener{
            listener.OnUpdate(note)
        }
        holder.view.icon_delete.setOnClickListener{
            listener.OnDelete(note)
        }
    }

    override fun getItemCount() = note.size

    class NoteViewHolder(val view: View):RecyclerView.ViewHolder(view)
    fun setData(list: List<Note>){
        note.clear()
        note.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun OnClick(note: Note)
        fun OnUpdate(note: Note)
        fun OnDelete(note: Note)
    }
}