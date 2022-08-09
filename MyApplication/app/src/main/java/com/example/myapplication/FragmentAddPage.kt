package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_page.*

class FragmentAddPage : Fragment() {
    private val wordModel: WordViewModel by viewModels {
        WordViewModelFactory((requireActivity().application as WordsApplication).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_page, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etSecondPage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                btnSecondAdd.isEnabled = etSecondPage.text.isNotEmpty()
            }
        })
        btnSecondAdd.setOnClickListener {
            wordModel.insert(etSecondPage.text.toString())
            Toast.makeText(context,"Word Added", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_fragmentFirstPage_to_fragmentFrontPage)
        }
    }
}