package com.example.chat.view.friends

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat.R
import com.example.chat.databinding.FragmentFriendsListBinding
import com.example.chat.viewModel.UsersViewModel

class FriendsListFragment : Fragment() {

    lateinit var binding: FragmentFriendsListBinding
    lateinit var adapter: FriendsAdapter
    lateinit var viewModel: UsersViewModel

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

        viewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        adapter = FriendsAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getData().observe(viewLifecycleOwner, Observer {
            adapter.addItem(it)
        })
    }

}