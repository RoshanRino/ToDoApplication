package com.example.myapplication
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_front_page.*
import kotlinx.android.synthetic.main.recyclerview_item.view.*


class FragmentFrontPage : Fragment(),WordAdapter.OnClickListeners {
    private val wordModel: WordViewModel by viewModels {
        WordViewModelFactory((requireActivity().application as WordsApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_front_page, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        setItemTouchHelper()
        wordModel.words.observe(viewLifecycleOwner) { words ->
            words.let { recyclerView.adapter = WordAdapter(wordModel.words.value.orEmpty(),this)
            }
        }
            fabAdd.setOnClickListener() {
                findNavController().navigate(R.id.action_fragmentFrontPage_to_fragmentFirstPage)
        }
            fabDelete.setOnClickListener() {
                wordModel.deleteAll()
                Toast.makeText(context, "Deleted All Selected", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCheckBoxClicked(word: Word, isChecked: Boolean) {
        val updatedWord = Word(word.word,isChecked,word.id)
        wordModel.update(updatedWord)
    }

    override fun onUpdateEditPressed(word:Word) {
        Navigation.findNavController(requireView()).navigate(FragmentFrontPageDirections.actionFragmentFrontPageToFragmentUpdatePage2(word.word,word.id,word.isChecked))
    }

    override fun onSwipeDelete(word: Word) {
        wordModel.deleteSwiped(word)
        Toast.makeText(context,"Deleted Swiped Word",Toast.LENGTH_SHORT).show()
    }

    private fun setItemTouchHelper(){
        ItemTouchHelper(object : ItemTouchHelper.Callback(){
            private val limitScrollX = 150
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val swipeFlag = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(0,swipeFlag)
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                    var scrollOffset = viewHolder.itemView.scrollX + (-dX).toInt()
                    if(scrollOffset>limitScrollX){
                        scrollOffset=limitScrollX
                    }
                    else if(scrollOffset<0){
                        scrollOffset=0
                    }
                    viewHolder.itemView.scrollTo(scrollOffset,0)
                        viewHolder.itemView.checkBox.visibility=View.INVISIBLE
            }
        }).apply {
            this.attachToRecyclerView(recyclerView)
        }
    }
}


