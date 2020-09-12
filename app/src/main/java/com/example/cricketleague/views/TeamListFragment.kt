package com.example.cricketleague.views

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketleague.R
import com.example.cricketleague.adapters.TeamsAdapter
import com.example.cricketleague.adapters.TeamsAdapter.RecyclerItemClickListener
import com.example.cricketleague.databinding.FragmentListTeamsBinding
import com.example.cricketleague.models.player.Player
import com.example.cricketleague.models.team.Team
import com.example.cricketleague.viewmodels.TeamListViewModel
import kotlinx.android.synthetic.main.fragment_list_teams.*


class TeamListFragment : Fragment(), RecyclerItemClickListener, SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentListTeamsBinding
    private lateinit var adapter: TeamsAdapter

    private val viewModel: TeamListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListTeamsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TeamsAdapter(this)

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.setHasFixedSize(true)

        recycler.adapter = adapter

        viewModel.allTeams.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchWith(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchWith(newText)
        return true
    }

    private fun searchWith(searchText: String?) {
        if (searchText.isNullOrBlank()) {
            adapter.resetFilters()
            return
        }

        val searchList: List<Team> = adapter.getInitialList()!!.filter {
            it.teamProfile.name.toLowerCase().startsWith(searchText)
        }
        adapter.submitList(searchList)

    }

    override fun onItemClick(position: Int,view:View) {

        val team: Team = adapter.currentList[position]

        val action = TeamListFragmentDirections.toTeamDetails(team)

        findNavController().navigate(action)
    }
}