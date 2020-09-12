package com.example.cricketleague.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricketleague.databinding.ItemTeamBinding
import com.example.cricketleague.models.team.Team

class TeamsAdapter(
        private val listener: RecyclerItemClickListener
) : ListAdapter<Team, TeamsAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var binding: ItemTeamBinding
    private var allTeams:List<Team>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemTeamBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding.root, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team = currentList[position]

        binding.teamName.text = team.teamProfile.name

        binding.matchesPlayed.text = team.teamStats.played.toString()
        binding.matchesWon.text = team.teamStats.won.toString()
        binding.matchesLost.text = team.teamStats.lost.toString()
        binding.totalPoints.text = team.teamStats.points.toString()

        Glide.with(binding.teamImg)
                .load(team.teamProfile.imageUrl)
                .into(binding.teamImg)

    }

    class ViewHolder constructor(
            itemView: View,
            private val listener: RecyclerItemClickListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position,v!!)
            }
        }
    }


    fun resetFilters() {
        submitList(allTeams)
    }

    override fun submitList(list: List<Team>?) {
        if (allTeams ==null && list != null){
            allTeams = ArrayList(list)
        }
        super.submitList(list)
    }

    fun getInitialList(): List<Team>? {
        return allTeams
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Team> = object : DiffUtil.ItemCallback<Team>() {
            override fun areItemsTheSame(oldTeam: Team, newTeam: Team): Boolean {
                return oldTeam.id == newTeam.id
            }

            override fun areContentsTheSame(oldTeam: Team, newTeam: Team): Boolean {
                return oldTeam == newTeam
            }
        }
    }

    interface RecyclerItemClickListener {
        fun onItemClick(position: Int,view:View)
    }
}