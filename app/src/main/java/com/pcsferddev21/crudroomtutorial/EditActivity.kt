package com.pcsferddev21.crudroomtutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pcsferddev21.crudroomtutorial.room.Note
import com.pcsferddev21.crudroomtutorial.room.NoteDB
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy {NoteDB(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupListener()
    }

    private fun setupListener() {
        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.noteDao().addNote(
                    Note ( 0, edit_title.text.toString(),edit_note.text.toString())
                )
                finish()
            }
        }
    }
}