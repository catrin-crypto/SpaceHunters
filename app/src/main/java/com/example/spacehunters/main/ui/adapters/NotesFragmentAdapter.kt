package com.example.spacehunters.main.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spacehunters.R
import com.example.spacehunters.main.model.entities.notes.NoteData
import com.example.spacehunters.main.model.entities.notes.OnListItemClickListener

class NotesFragmentAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var notesData: MutableList<NoteData>
) : RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            SIMPLE_TYPE -> SimpleNoteViewHolder(
                inflater.inflate(
                    R.layout.notes_recycler_view_simple_item,
                    parent,
                    false
                ) as View
            )
            else -> NoteWithDateViewHolder(
                inflater.inflate(
                    R.layout.notes_recycler_view_item_with_data,
                    parent,
                    false
                ) as View
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(notesData[position])
    }


    override fun getItemCount(): Int {
        return notesData.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (notesData[position].creationDate.isNullOrBlank()) SIMPLE_TYPE else TYPE_WITH_DATE
    }

    inner class SimpleNoteViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(noteData: NoteData) {
            itemView.findViewById<TextView>(R.id.note_title).text = noteData.title
            itemView.findViewById<TextView>(R.id.note_description).text = noteData.desription
            itemView.findViewById<TextView>(R.id.note_title).setOnClickListener {
                onListItemClickListener.onItemClick(noteData)
            }
            itemView.findViewById<ImageView>(R.id.add_item_icon).setOnClickListener { addItem() }
            itemView.findViewById<ImageView>(R.id.remove_item_icon)
                .setOnClickListener { removeItem() }
            itemView.findViewById<ImageView>(R.id.move_item_up_icon).setOnClickListener { moveUp() }
            itemView.findViewById<ImageView>(R.id.move_item_down_icon)
                .setOnClickListener { moveDown() }
        }

        private fun addItem() {
            notesData.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            notesData.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                notesData.removeAt(currentPosition).apply {
                    notesData.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < notesData.size - 1 }?.also { currentPosition ->
                notesData.removeAt(currentPosition).apply {
                    notesData.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }
    }

    inner class NoteWithDateViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(noteData: NoteData) {
            itemView.findViewById<TextView>(R.id.note_title).text = noteData.title
            itemView.findViewById<TextView>(R.id.note_description).text = noteData.desription
            itemView.findViewById<TextView>(R.id.note_creation_date)?.text = noteData.creationDate
            itemView.findViewById<TextView>(R.id.note_title).setOnClickListener {
                onListItemClickListener.onItemClick(noteData)
            }
            itemView.findViewById<ImageView>(R.id.add_item_icon).setOnClickListener { addItem() }
            itemView.findViewById<ImageView>(R.id.remove_item_icon)
                .setOnClickListener { removeItem() }
            itemView.findViewById<ImageView>(R.id.move_item_up_icon).setOnClickListener { moveUp() }
            itemView.findViewById<ImageView>(R.id.move_item_down_icon)
                .setOnClickListener { moveDown() }
        }

        private fun addItem() {
            notesData.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        private fun removeItem() {
            notesData.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                notesData.removeAt(currentPosition).apply {
                    notesData.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < notesData.size - 1 }?.also { currentPosition ->
                notesData.removeAt(currentPosition).apply {
                    notesData.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }
    }


    fun appendItem() {
        notesData.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = NoteData(
        "NoteTittle ${notesData.size}",
        "NoteDescription ${notesData.size}",
        "Date ${notesData.size}"
    )

    companion object {
        private const val SIMPLE_TYPE = 0
        private const val TYPE_WITH_DATE = 1
    }
}

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(noteData: NoteData)
//        if(layoutPosition != RecyclerView.NO_POSITION){
//            (itemView.findViewById(R.id.note_title) as TextView)?.text = noteData.title
//            (itemView.findViewById(R.id.note_description) as TextView)?.text = noteData.desription
//            (itemView.findViewById(R.id.add_item_icon) as ImageView).setOnClickListener { addItem() }
//            (itemView.findViewById(R.id.remove_item_icon) as ImageView).setOnClickListener { removeItem() }
//            (itemView.findViewById(R.id.move_item_up_icon) as ImageView).setOnClickListener { moveUp() }
//            (itemView.findViewById(R.id.move_item_down_icon) as ImageView).setOnClickListener { moveDown() }
//        }
//
//    }
//
//    private fun addItem() {
//        notesData.add(layoutPosition, generateItem())
//        notifyItemInserted(layoutPosition)
//    }
//
//    private fun removeItem() {
//        notesData.removeAt(layoutPosition)
//        notifyItemRemoved(layoutPosition)
//    }
//
//    private fun moveUp() {
//        layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
//            notesData.removeAt(currentPosition).apply {
//                notesData.add(currentPosition - 1, this)
//            }
//            notifyItemMoved(currentPosition, currentPosition - 1)
//        }
//    }
//
//    private fun moveDown() {
//        layoutPosition.takeIf { it < notesData.size - 1 }?.also { currentPosition ->
//            notesData.removeAt(currentPosition).apply {
//                notesData.add(currentPosition + 1, this)
//            }
//            notifyItemMoved(currentPosition, currentPosition + 1)
//        }
//    }
}