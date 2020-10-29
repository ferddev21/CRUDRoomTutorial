package com.pcsferddev21.crudroomtutorial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pcsferddev21.crudroomtutorial.room.Note
import com.pcsferddev21.crudroomtutorial.room.NoteDB
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val db by lazy { NoteDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
              val notes =  db.noteDao().getNote()
            Log.d("MainActivity", "dbRespones: $notes")
        }
    }

    private fun setupListener() {
        button_create.setOnClickListener {
            startActivity(Intent(this, EditActivity::class.java))
        }
    }
}