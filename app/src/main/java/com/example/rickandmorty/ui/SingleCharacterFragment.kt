package com.example.rickandmorty.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentSingleCharacterBinding
import com.example.rickandmorty.util.isInternetAvailable
import com.example.rickandmorty.viewmodel.SharedViewModel
import java.io.IOException

class SingleCharacterFragment : Fragment() {

    private lateinit var binding: FragmentSingleCharacterBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    private var enteredId: Int? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_single_character,
            container,
            false
        )

        binding.loadSingleCharacter.setOnClickListener {
            enteredId = binding.idToBeEntered.text.toString().toIntOrNull()
            if (enteredId == null) {
                Toast.makeText(requireContext(), "Please enter a valid ID!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (enteredId!! < 1 || enteredId!! > 826) {
                    Toast.makeText(
                        requireContext(),
                        "Id is out of specified range above!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    if (isInternetAvailable(requireContext())) {
                        try {
                            viewModel.getCharacterById(enteredId!!)

                            binding.loadingIndicatorSingleCharacter.visibility = View.VISIBLE
                            binding.hintMessage.visibility = View.INVISIBLE

                            viewModel.singleCharacters.observe(viewLifecycleOwner) { character ->
                                if (character != null) {
                                    binding.singleCharacterName.text = character.name
                                    Glide.with(requireContext())
                                        .load(character.image)
                                        .placeholder(R.drawable.loading)
                                        .error(R.drawable.failed)
                                        .into(binding.singleCharacterImage)

                                    if (character.status == "Alive") {
                                        binding.singleCharacterStatusImage.setImageResource(R.drawable.online)
                                    }

                                    if (character.gender == "Female") {
                                        binding.singleCharacterGenderIcon.setImageResource(R.drawable.female)
                                        binding.singleCharacterGender.text = "Female"
                                    } else {
                                        binding.singleCharacterGenderIcon.setImageResource(R.drawable.male)
                                        binding.singleCharacterGender.text = "Male"
                                    }

                                    binding.singleCharacterStatus.text =
                                        "${character.status} - ${character.species}"

                                    binding.singleCharacterLocation.text =
                                        character.location.name
                                    binding.singleCharacterCreationTime.text = character.created

                                    binding.loadingIndicatorSingleCharacter.visibility = View.GONE
                                    binding.singleCharacterParentLayout.visibility = View.VISIBLE
                                   return@observe

                                }
                            }

                        } catch (e: IOException) {
                            Toast.makeText(
                                requireContext(),
                                "Request timed out. Please check your internet connection and try again.!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        binding.loadingIndicatorSingleCharacter.visibility = View.INVISIBLE
                        binding.hintMessage.apply {
                            text = "Failed due to lost connection!"
                            visibility = View.VISIBLE
                        }
                        Toast.makeText(
                            requireContext(),
                            "No internet connection problem has occurred!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }




        return binding.root
    }
}