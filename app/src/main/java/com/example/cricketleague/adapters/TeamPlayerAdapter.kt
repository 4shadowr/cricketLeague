package com.example.cricketleague.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cricketleague.databinding.ItemPlayerBinding
import com.example.cricketleague.databinding.ItemTeamPlayerBinding
import com.example.cricketleague.models.player.Player
import java.util.Comparator
import java.util.function.Predicate

class TeamPlayerAdapter(
        private val listener: RecyclerItemClickListener
) : ListAdapter<Player, TeamPlayerAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var binding: ItemTeamPlayerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemTeamPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = currentList[position]

        binding.playerImg.transitionName = "image${current.id}"
        binding.playerName.transitionName = "name${current.id}"
        binding.playerType.transitionName = "type${current.id}"


        Glide.with(binding.playerImg)
                .load(current.playerProfile.profileImgUrl)
                .apply(RequestOptions().override(32, 32))
                .into(binding.playerImg)

        binding.playerName.text = current.playerProfile.fullName
        binding.playerType.text = current.playerStats.type
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

    companion object {
        private const val TAG = "TeamPlayerAdapter"
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