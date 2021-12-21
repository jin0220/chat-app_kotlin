package com.example.chat.view.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.R
import com.example.chat.databinding.FragmentChattingBinding
import com.example.chat.viewModel.ChatViewModel

class ChattingFragment : Fragment() {

    lateinit var binding:FragmentChattingBinding
    lateinit var adapter: ChattingAdapter
    lateinit var viewModel: ChatViewModel

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

        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        adapter = ChattingAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.chatList.observe(viewLifecycleOwner, Observer {
            adapter.addItem(it)
        })

    }
}