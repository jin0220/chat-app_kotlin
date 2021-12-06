package com.example.chat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.R
import com.example.chat.databinding.FragmentChattingBinding
import com.example.chat.model.data.Users

class ChattingFragment : Fragment() {

    lateinit var binding:FragmentChattingBinding
    lateinit var adapter: ChattingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChattingBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolBar.inflateMenu(R.menu.friends_menu)

        adapter = ChattingAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        adapter.addItem(dataTest())
    }

    fun dataTest(): MutableList<Users>{
        var list: MutableList<Users> = arrayListOf()

        for (i in 0 .. 10){
            list.add(Users("${i}번"))
        }
        return list
    }
}