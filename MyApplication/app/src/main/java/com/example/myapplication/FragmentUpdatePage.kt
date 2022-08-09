package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_update_page.*

class FragmentUpdatePage : Fragment() {
    private val wordModel: WordViewModel by viewModels {
        WordViewModelFactory((requireActivity().application as WordsApplication).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_update_page, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args : FragmentUpdatePageArgs by navArgs()
        val word = Word(args.word,args.isChecked,args.id)
        etUpdate.setText(word.word)
        etUpdate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                btnSecondUpdate.isEnabled = (etUpdate.text.isNotEmpty() && etUpdate.text.toString()!=word.word)
            }
        })
        btnSecondUpdate.setOnClickListener {
            val newWord = Word(etUpdate.text.toString(),word.isChecked,word.id)
            wordModel.update(newWord)
            Toast.makeText(context,"Word Updated", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_fragmentUpdatePage2_to_fragmentFrontPage)
        }
    }
}