package com.pcsferddev21.crudroomtutorial

import android.content.Intent
import android.content.LocusId
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.pcsferddev21.crudroomtutorial.room.Constant
import com.pcsferddev21.crudroomtutorial.room.Note
import com.pcsferddev21.crudroomtutorial.room.NoteDB
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { NoteDB(this) }
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
       loadNote()
    }

    private fun loadNote(){
        CoroutineScope(Dispatchers.IO).launch {
            val notes =  db.noteDao().getNote()
            Log.d("MainActivity", "dbRespones: $notes")
            withContext(Dispatchers.Main){
                noteAdapter.setData(notes)
            }
        }
    }

    private fun setupListener() {
        button_create.setOnClickListener {
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }

    fun intentEdit(noteId: Int, intentType: Int){
        startActivity(
            Intent(applicationContext, EditActivity::class.java)
                .putExtra("intent_id",noteId)
                .putExtra("intent_type", intentType)
        )
    }

    private fun setupRecyclerView(){
        noteAdapter = NoteAdapter(arrayListOf(), object : NoteAdapter.OnAdapterListener{
            override fun OnClick(note: Note) {
//               Toast.makeText(applicationContext, note.title, Toast.LENGTH_SHORT).show()
                //read
                intentEdit(note.id,Constant.TYPE_READ)
            }

            override fun OnUpdate(note: Note) {
                intentEdit(note.id,Constant.TYPE_UPDATE)
            }

            override fun OnDelete(note: Note) {
                deleteDialog(note)
            }

        })
        list_note.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = noteAdapter
        }
    }

    private fun deleteDialog(note: Note){
        val alartDialog = AlertDialog.Builder(this)
        alartDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin hapus ${note.title} ?")
            setNegativeButton("Batal") {
                dialogInterface, i -> dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") {
                dialogInterface, i -> dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.noteDao().deleteNote(note)
                    loadNote()
                }
            }
        }
        alartDialog.show()
    }
}