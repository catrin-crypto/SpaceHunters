package com.example.spacehunters.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spacehunters.R
import com.example.spacehunters.main.model.entities.notes.NoteData
import com.example.spacehunters.main.model.entities.notes.OnListItemClickListener
import com.example.spacehunters.main.ui.adapters.NotesFragmentAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NotesFragment : Fragment() {
    val mNotesData = arrayListOf(
        NoteData("First note", "important details"),
        NoteData("Second note", "something", "14.10.2021"),
        NoteData("Third note", "hello", "11.10.2021"),
        NoteData("Forth note", "see you soon")
    )
    lateinit var fragmentView: View

    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.fragment_notes, container, false)

        recyclerView = fragmentView.findViewById(R.id.notes_fragment_recycler_view)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = NotesFragmentAdapter(
            object : OnListItemClickListener {
                override fun onItemClick(noteData: NoteData) {
                    Toast.makeText(this@NotesFragment.context, noteData.title, Toast.LENGTH_SHORT)
                        .show()
                }
            },
            mNotesData
        )
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        fragmentView.findViewById<FloatingActionButton>(R.id.recycler_view_notes_fab)
            .setOnClickListener { adapter.appendItem() }
    }

    companion object {
        fun newInstance() = NotesFragment()
    }
}