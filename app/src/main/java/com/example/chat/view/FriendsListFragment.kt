package com.example.chat.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.chat.R
import com.example.chat.databinding.ActivityMainBinding
import com.example.chat.databinding.FragmentFriendsListBinding
import com.example.chat.view.MainActivity as MainActivity

class FriendsListFragment : Fragment() {

    lateinit var binding: FragmentFriendsListBinding

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
    }
}