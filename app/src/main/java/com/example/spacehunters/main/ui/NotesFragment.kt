package com.example.spacehunters.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.spacehunters.R
import com.example.spacehunters.main.model.entities.notes.NoteData
import com.example.spacehunters.main.model.entities.notes.OnListItemClickListener
import com.example.spacehunters.main.ui.adapters.NotesFragmentAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotesFragment : Fragment() {
    val mNotesData = arrayListOf(
        NoteData("First note", "important details"),
        NoteData("Second note", "something", "14.10.2021"),
        NoteData("Third note", "hello", "11.10.2021"),
        NoteData("Forth note", "see you soon")
    )

    lateinit var  recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentNotes = inflater.inflate(R.layout.fragment_notes, container, false)
        recyclerView = fragmentNotes.findViewById(R.id.notes_fragment_recycler_view)
        return fragmentNotes
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val adapter  = NotesFragmentAdapter(
            object : OnListItemClickListener{
                override fun onItemClick(noteData: NoteData) {
                    Toast.makeText(this@NotesFragment.context, noteData.title, Toast.LENGTH_SHORT).show()
                }
            },
            mNotesData
        )
        recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance() = NotesFragment()
    }
}