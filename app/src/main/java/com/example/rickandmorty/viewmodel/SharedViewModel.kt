package com.example.rickandmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.pojo.Results
import com.example.rickandmorty.repository.SharedRepository
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    private val repo = SharedRepository()
    private var _allCharacters = MutableLiveData<List<Results>?>()
    private var _singleCharacters = MutableLiveData<Results?>()
    val allCharacters: LiveData<List<Results>?>
        get() = _allCharacters

    val singleCharacters: LiveData<Results?>
        get() = _singleCharacters


    fun getCharacterById(id: Int) {
        viewModelScope.launch {
            val result = repo.getCharacterById(id)
            _singleCharacters.value = result
        }
    }

    fun getAllCharacters(){
        viewModelScope.launch {
            val result = repo.getAllCharacters()
            _allCharacters.value = result
        }
    }
}