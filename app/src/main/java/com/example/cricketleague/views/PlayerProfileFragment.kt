package com.example.cricketleague.views

import android.os.Bundle
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.cricketleague.databinding.FragmentPlayerProfileBinding

class PlayerProfileFragment : Fragment() {

    private lateinit var binding: FragmentPlayerProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move )
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move )
        postponeEnterTransition()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlayerProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val args: PlayerProfileFragmentArgs by navArgs()

        val player = args.player

        Glide.with(view)
                .load(player.playerProfile.profileImgUrl)
                .into(binding.circleImageView)
        binding.playerName.text = player.playerProfile.fullName
        binding.playerType.text = player.playerStats.type

        binding.circleImageView.transitionName = "image${player.id}"
        binding.playerName.transitionName = "name${player.id}"
        binding.playerType.transitionName = "type${player.id}"


        startPostponedEnterTransition()
    }
}