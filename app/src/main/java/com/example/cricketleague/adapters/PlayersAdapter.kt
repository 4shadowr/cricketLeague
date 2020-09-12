package com.example.cricketleague.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cricketleague.models.player.Player
import com.example.cricketleague.databinding.ItemPlayerBinding
import java.util.*
import java.util.function.Predicate
import kotlin.collections.ArrayList

class PlayersAdapter(
        private val listener: RecyclerItemClickListener
) : ListAdapter<Player, PlayersAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var binding: ItemPlayerBinding
    private var allPlayers: List<Player>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root,listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = currentList[position]
        binding.playerName.text = current.playerProfile.fullName
        binding.playerType.text = current.playerStats.type
        binding.manOfTheMatch.text = current.playerStats.manOfTheMatchWins.toString()
        binding.matchesPlayed.text = current.playerStats.matchesPlayed.toString()
        binding.totalPoints.text = current.playerStats.totalPoints.toString()
        Glide.with(binding.profileImg)
                .load(current.playerProfile.profileImgUrl)
                .apply(RequestOptions().override(42, 42))
                .into(binding.profileImg)
    }

    fun applyFilters(predicate: Predicate<Player>) {
        submitList(currentList.filter { predicate.test(it) })
    }

    override fun submitList(list: List<Player>?) {
        if (list != null && allPlayers == null) {
            allPlayers = ArrayList(list)
        }
        super.submitList(list)
    }

    fun resetFilters() {
        submitList(allPlayers)
    }

    fun getList() = ArrayList(allPlayers)


    fun getInitialList():List<Player>?{
        return allPlayers
    }

    fun sortUsing(playerComparator: Comparator<Player>) {
        allPlayers!!.sortedWith(playerComparator)
        notifyDataSetChanged()
    }

    class ViewHolder constructor(itemView: View, private val listener: RecyclerItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

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

    companion object {
        private const val TAG = "PlayersAdapter"
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Player> = object : DiffUtil.ItemCallback<Player>() {
            override fun areItemsTheSame(oldPlayer: Player, newPlayer: Player): Boolean {
                return oldPlayer.id == newPlayer.id
            }

            override fun areContentsTheSame(oldPlayer: Player, newPlayer: Player): Boolean {
                return oldPlayer == newPlayer
            }
        }
    }

    interface RecyclerItemClickListener {
        fun onItemClick(position: Int,view:View)
    }
}