package com.example.rickandmorty.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.R
import com.example.rickandmorty.adapter.AllCharactersAdapter
import com.example.rickandmorty.databinding.FragmentShowAllCharactersBinding
import com.example.rickandmorty.util.isInternetAvailable
import com.example.rickandmorty.viewmodel.SharedViewModel


class ShowAllCharactersFragment : Fragment() {
    private lateinit var binding: FragmentShowAllCharactersBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    private var page: Int? = 0


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_show_all_characters,
            container,
            false
        )

        val adapter = AllCharactersAdapter()
        viewModel.allCharacters.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it)
                binding.allCharactersRecycler.adapter = adapter
            } else {
                binding.allCharactersRecycler.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE
            }
        }


        if (page == 0) {
            binding.errorMessage.text = "No page selected yet"
            binding.errorMessage.visibility = View.VISIBLE
        }

        binding.loadPageBtn.setOnClickListener {
            binding.loadingIndicator.visibility = View.VISIBLE
            binding.errorMessage.visibility = View.GONE
            page = binding.pageToBeSelected.text.toString().toIntOrNull()
            if (page == null) {
                Toast.makeText(requireContext(), "Please enter a valid number!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (page!! < 1 || page!! > 42) {
                    Toast.makeText(
                        requireContext(),
                        "Page is out of range, look at upper hint!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (isInternetAvailable(requireContext())) {
                        binding.loadingIndicator.visibility = View.VISIBLE
                        binding.errorMessage.visibility = View.GONE
                        viewModel.getAllCharacters(page!!)
                        binding.allCharactersRecycler.visibility = View.VISIBLE
                        binding.loadingIndicator.visibility = View.GONE


                    } else {
                        binding.errorMessage.apply {
                            text = "Failed due to lost connection!"
                            visibility = View.VISIBLE
                        }
                        binding.loadingIndicator.visibility = View.GONE
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