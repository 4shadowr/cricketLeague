package com.example.cricketleague.views

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.example.cricketleague.R
import com.example.cricketleague.adapters.PlayersAdapter
import com.example.cricketleague.databinding.FragmentListPlayersBinding
import com.example.cricketleague.models.player.Player
import com.example.cricketleague.models.player.PlayerProfile
import com.example.cricketleague.models.player.PlayerStats
import com.example.cricketleague.viewmodels.PlayerListViewModel
import java.util.*


class PlayerListFragment : Fragment(), SearchView.OnQueryTextListener, PlayersAdapter.RecyclerItemClickListener {

    private lateinit var binding: FragmentListPlayersBinding

    private val viewModel: PlayerListViewModel by viewModels({
        requireActivity()
    })

    private lateinit var allPlayers: List<Player>
    private lateinit var adapter: PlayersAdapter
    private val playerFilterDialog = PlayerFilterDialogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListPlayersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PlayersAdapter(this)
        binding.playerRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.playerRecycler.adapter = adapter

        setHasOptionsMenu(true)

        binding.extendedFab.setOnClickListener {
            if (!playerFilterDialog.isVisible) {
                playerFilterDialog.show(requireActivity().supportFragmentManager, PlayerFilterDialogFragment.TAG)
            }
        }

        viewModel.allPlayers.observe(viewLifecycleOwner, { list: List<Player>? ->
            if (list != null) {
                allPlayers = ArrayList(list)
                adapter.submitList(allPlayers)
            }
        })

        viewModel.sortSelected.observe(viewLifecycleOwner, { checkId ->
            with(adapter) {
                getList().apply {
                    sortWith(
                            when (checkId) {
                                "age" -> compareBy { it.playerProfile.age }
                                "points" -> compareBy { it.playerStats.totalPoints }
                                "matches played" -> compareBy { it.playerStats.matchesPlayed }
                                "man of the match" -> compareBy { it.playerStats.manOfTheMatchWins }
                                "bowler of the match" -> compareBy { it.playerStats.bowlerOfTheMatchWins }
                                "best fielder" -> compareBy { it.playerStats.bestFieldWins }

                                else -> compareBy { it.playerProfile.fullName }
                            }
                    )
                    submitList(this)
                }
                notifyDataSetChanged()
            }
        })


        viewModel.filterQuery.observe(viewLifecycleOwner,
                {
                    if (viewModel.filterQuery.value != null) {
                        val selectedId: Int = viewModel.filterSelected.value!!
                        filterList(selectedId, it)
                    }
                })

        viewModel.filterSelected.observe(viewLifecycleOwner,
                {
                    if (viewModel.filterQuery.value != null) {
                        val query = viewModel.filterQuery.value!!.toLowerCase().trim()
                        filterList(it, query)
                    }
                })
    }

    private fun filterList(id: Int, query: String) {
        with(adapter) {
            getList().filter {
                when (id) {
                    R.id.type_filter -> it.playerStats.type.toLowerCase() == query
                    R.id.team_filter -> it.playerProfile.teamId.toLowerCase() == query
                    R.id.country_filter -> it.playerProfile.country.toLowerCase() == query

                    else -> throw IllegalStateException("Invalid Filter Chip ID")
                }
            }.also { submitList(it) }
            notifyDataSetChanged()
        }
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        val item: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        super.onPrepareOptionsMenu(menu)
    }


    companion object {
        private const val TAG = "PlayerListFragment"
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchWith(query)
        return true;
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

        val searchList: List<Player> = adapter.getInitialList()!!.filter {
            it.playerProfile.firstName.toLowerCase().startsWith(searchText) ||
                    it.playerProfile.lastName.toLowerCase().startsWith(searchText)
        }
        adapter.submitList(searchList)

    }

    override fun onItemClick(position: Int, view: View) {
        val player = adapter.currentList[position]
        val action = PlayerListFragmentDirections.toPlayerDetail(player)

        findNavController().navigate(action)
    }
}