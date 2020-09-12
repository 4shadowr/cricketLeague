package com.example.cricketleague.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketleague.R
import com.example.cricketleague.adapters.TeamsAdapter
import com.example.cricketleague.adapters.TeamsAdapter.RecyclerItemClickListener
import com.example.cricketleague.databinding.FragmentHomeBinding
import com.example.cricketleague.viewmodels.HomeViewModel

class HomeFragment : Fragment(), RecyclerItemClickListener {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel:HomeViewModel by viewModels()
    private lateinit var teamsAdapter: TeamsAdapter
    private lateinit var rv:RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teamsAdapter = TeamsAdapter(this)
        rv = binding.standings.recyclerView

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = teamsAdapter

        viewModel.getStandings().observe(viewLifecycleOwner,{
            teamsAdapter.submitList(it)
        })
    }

    override fun onItemClick(position: Int, view: View) {
        val team = teamsAdapter.currentList[position]

        val action = HomeFragmentDirections.toTeamDetails(team)

        findNavController().navigate(action)
    }
}