package com.example.cricketleague.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.cricketleague.R
import com.example.cricketleague.adapters.TeamPlayerAdapter
import com.example.cricketleague.databinding.FragmentTeamProfileBinding
import com.example.cricketleague.models.team.Team
import com.example.cricketleague.viewmodels.TeamListViewModel
import de.hdodenhof.circleimageview.CircleImageView

class TeamDetailsFragment : Fragment(), TeamPlayerAdapter.RecyclerItemClickListener {

    private lateinit var binding: FragmentTeamProfileBinding

    private val viewModel: TeamListViewModel by viewModels()

    private lateinit var playerAdapter: TeamPlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTeamProfileBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val args: TeamDetailsFragmentArgs by navArgs()

        val team: Team = args.team

        Glide.with(view).load(team.teamProfile.imageUrl).into(binding.teamImg)

        binding.teamName.text = team.teamProfile.name

        binding.matchesPlayedValue.text = team.teamStats.played.toString()
        binding.matchesWonValue.text = team.teamStats.won.toString()
        binding.matchesPointsValue.text = team.teamStats.won.toString()

        playerAdapter = TeamPlayerAdapter(this)

        viewModel.getTeamPlayers(team.id).observe(viewLifecycleOwner, {
            playerAdapter.submitList(it)
            playerAdapter.notifyDataSetChanged()
        })
        postponeEnterTransition()
        binding.playerRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = playerAdapter
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }

    }

    override fun onItemClick(position: Int, view: View) {
        val player = playerAdapter.currentList[position]

        val imgView: CircleImageView = view.findViewById(R.id.player_img)
        val name: TextView = view.findViewById(R.id.player_name)
        val type: TextView = view.findViewById(R.id.player_type)


        val action = TeamDetailsFragmentDirections.actionTeamDetailsToPlayerDetail(player)
        findNavController().navigate(action)
    }
}