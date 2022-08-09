package com.example.myapplication

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class WordAdapter(val words :List<Word>,val listners: OnClickListeners) : RecyclerView.Adapter<WordAdapter.WordViewHolder>(),
    PopupMenu.OnMenuItemClickListener {

    var adapter=0
    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.tvItem)
        init{
            itemView.checkBox.setOnClickListener{
                val current = words[adapterPosition]
                listners.onCheckBoxClicked(current,itemView.checkBox.isChecked)
            }
            itemView.deleteImage.setOnClickListener{
                val current = words[adapterPosition]
                listners.onSwipeDelete(current)
            }
            itemView.setOnLongClickListener{
                adapter=adapterPosition
                popupMenu(itemView)
            }
            itemView.setOnClickListener{
                itemView.checkBox.visibility = View.VISIBLE
                itemView.scrollTo(0,0)
            }
        }

        fun bind(current :Word) {
            wordItemView.text = current.word
            itemView.checkBox.isChecked = current.isChecked
        }
    }

    fun popupMenu(view: View): Boolean {
        val popupMenu = PopupMenu(view.context,view)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener(this)
        popupMenu.show()
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.bind(current)
    }
    interface OnClickListeners{
        fun onCheckBoxClicked(word: Word,isChecked:Boolean)
        fun onUpdateEditPressed(string: Word)
        fun onSwipeDelete(word: Word)
    }

    override fun getItemCount(): Int {
        return words.size
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        if(p0?.itemId==R.id.popupEdit){
           listners.onUpdateEditPressed(words[adapter])
            return true
        }
        else
            return false
    }
}