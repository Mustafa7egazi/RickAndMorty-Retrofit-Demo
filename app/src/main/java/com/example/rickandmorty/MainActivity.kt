package com.example.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel:SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        viewModel.getAllCharacters()
        viewModel.getCharacterById(1)
        viewModel.singleCharacters.observe(this){
            Log.d("7egzzz", it.toString())
            if (it != null) {
                binding.text.text = "${it.id}: ${it.name} is ${it.status}"
            }
        }

    }
}