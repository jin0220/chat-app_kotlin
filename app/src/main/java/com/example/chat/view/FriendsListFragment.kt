package com.example.chat.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.R
import com.example.chat.databinding.FragmentFriendsListBinding
import com.example.chat.model.data.Users

class FriendsListFragment : Fragment() {

    lateinit var binding: FragmentFriendsListBinding
    lateinit var adapter: FriendsAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFriendsListBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolBar.inflateMenu(R.menu.friends_menu)

        adapter = FriendsAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        adapter.addItem(dataTest())
    }

    fun dataTest(): MutableList<Users>{
        var list:MutableList<Users> = arrayListOf()
        for(i in 0..15){
            list.add(Users("${i}번"))
        }
        return list
    }
}