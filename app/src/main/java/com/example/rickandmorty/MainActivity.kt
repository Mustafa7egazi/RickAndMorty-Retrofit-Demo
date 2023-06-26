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
//        viewModel.getCharacterById(1)
//
//        val listOfParams = setOf(1,2,3,4,5,6)
//        var listOfParamsString= ""
//        for (i in listOfParams){
//            if (i == listOfParams.last())
//            listOfParamsString += "$i"
//            else
//                listOfParamsString += "$i,"
//        }
//
//        Log.d("7egzz", "onCreate: $listOfParamsString")
//        viewModel.getMultipleCharacters(listOfParamsString)
//        viewModel.setOfCharacters.observe(this){
//            Log.d("7egzzz", it.toString())
//            if (it != null) {
//                var out = ""
//                for (character in it){
//                    out += "${character.id}: ${character.name} is ${character.status} \n"
//                }
//                binding.text.text = out
//            }
//        }

    }
}