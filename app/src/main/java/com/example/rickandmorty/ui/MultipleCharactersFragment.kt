package com.example.rickandmorty.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.AllCharactersAdapter
import com.example.rickandmorty.databinding.FragmentMultipleCharactersBinding
import com.example.rickandmorty.util.isInternetAvailable
import com.example.rickandmorty.viewmodel.SharedViewModel


class MultipleCharactersFragment : Fragment() {

    private lateinit var binding: FragmentMultipleCharactersBinding
    private val collectionOfIds = mutableSetOf<Int>()
    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_multiple_characters,
            container,
            false
        )


        val adapterForMultipleCharactersBinding = AllCharactersAdapter()
        viewModel.setOfCharacters.observe(viewLifecycleOwner) { groupOfCharacters ->
            if (groupOfCharacters != null) {
                adapterForMultipleCharactersBinding.submitList(groupOfCharacters)
                binding.groupOfCharactersRecycler.apply {
                    adapter = adapterForMultipleCharactersBinding
                    visibility = View.VISIBLE
                }
                binding.isLoadingMultipleCharacters.visibility = View.GONE
                binding.noSpecifiedIdMessage.visibility = View.GONE
            }
        }

        viewModel.singleCharacters.observe(viewLifecycleOwner) { groupOfCharacters ->
            if (groupOfCharacters != null) {
                val character = mutableListOf(groupOfCharacters)
                adapterForMultipleCharactersBinding.submitList(character)
                binding.groupOfCharactersRecycler.apply {
                    adapter = adapterForMultipleCharactersBinding
                    visibility = View.VISIBLE
                }
                binding.isLoadingMultipleCharacters.visibility = View.GONE
                binding.noSpecifiedIdMessage.visibility = View.GONE
            }
        }


        binding.insertIdBtn.setOnClickListener {
            val id: Int? = binding.idsForMultipleCharactersET.text.toString().toIntOrNull()
            binding.idsForMultipleCharactersET.text.clear()
            if (id != null && id > 0 && id <= 826) {
                collectionOfIds.add(id)
            } else {
                Toast.makeText(requireContext(), "Enter a valid id!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.fetchInsertedIdsBtn.setOnClickListener {
            if (collectionOfIds.isEmpty()) {
                Toast.makeText(requireContext(), "Insert ids to be fetched!", Toast.LENGTH_SHORT)
                    .show()
            } else {

                if (isInternetAvailable(requireContext())) {
                    binding.isLoadingMultipleCharacters.visibility = View.VISIBLE
                    var arg = ""
                    for (id in collectionOfIds) {
                        arg += if (id != collectionOfIds.last()) {
                            "$id,"
                        } else {
                            "$id"
                        }
                    }
                    if (collectionOfIds.size == 1) {
                        viewModel.getCharacterById(arg.toInt())
                        binding.isLoadingMultipleCharacters.visibility = View.VISIBLE
                    } else {
                        viewModel.getMultipleCharacters(arg)
                        binding.isLoadingMultipleCharacters.visibility = View.VISIBLE
                    }

                } else {
                    binding.isLoadingMultipleCharacters.visibility = View.GONE
                    binding.noSpecifiedIdMessage.apply {
                        text = "Failed due to lost connection!"
                        visibility = View.VISIBLE
                    }
                    Toast.makeText(
                        requireContext(),
                        "No internet connection problem has occurred!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            }
        }

        return binding.root
    }


}