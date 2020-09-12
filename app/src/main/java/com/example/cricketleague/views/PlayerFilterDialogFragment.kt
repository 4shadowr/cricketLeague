package com.example.cricketleague.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.core.view.get
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.transition.TransitionInflater
import com.example.cricketleague.R
import com.example.cricketleague.databinding.FragmentDialogFilterPlayerBinding
import com.example.cricketleague.viewmodels.PlayerListViewModel
import com.google.android.material.chip.Chip
import kotlin.IllegalStateException

class PlayerFilterDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogFilterPlayerBinding
    private var filterTextView: AutoCompleteTextView? = null

    private val viewModel: PlayerListViewModel by viewModels({
        requireActivity()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.fade)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.fade)

        super.onCreate(savedInstanceState)
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialogTheme
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDialogFilterPlayerBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*if (requireActivity().actionBar != null) {
            requireActivity().actionBar!!.title = "Sort & Filter"
        }*/
        val fields = setOf(binding.typeAutoTv, binding.teamAutoTv, binding.countryAutoTv)
        fields.forEach {
            it.visibility = View.GONE
        }

        binding.ascDescChipGroup.check(viewModel.orderSelected.value ?: binding.ascChip.id)

        viewModel.filterSelected.value?.let {
            binding.filterRadioGroup.check(it)
        }


        binding.ascDescChipGroup.setOnCheckedChangeListener { group, checkedId ->
            viewModel.orderSelected.value = checkedId
        }

        binding.sortingChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip:Chip = group.findViewById(checkedId)
            viewModel.sortSelected.value = chip.text.toString().toLowerCase()
        }

        binding.applyBtn.setOnClickListener {
            if (filterTextView != null) {
                if (!(filterTextView!!.text.isNullOrEmpty())) {
                    viewModel.filterQuery.value = filterTextView!!.text.toString()
                }
            }
            dismiss()
        }

        binding.resetBtn.setOnClickListener {
            viewModel.isReset.value = true
        }

        binding.filterRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            fields.forEach {
                it.visibility = View.GONE
            }
            viewModel.filterSelected.value = checkedId
            when (checkedId) {
                binding.typeFilter.id -> binding.typeAutoTv
                binding.teamFilter.id -> binding.teamAutoTv
                binding.countryFilter.id -> binding.countryAutoTv

                else -> throw IllegalStateException("Filter Not Found")
            }.apply {
                filterTextView = this
                filterTextView!!.visibility = View.VISIBLE
            }
        }
    }


    companion object {
        const val TAG = "PlayerFilterDialogFragm"
    }

}