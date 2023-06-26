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
    private var _setOfCharacters = MutableLiveData<List<Results>?>()

    val allCharacters: LiveData<List<Results>?>
        get() = _allCharacters

    val singleCharacters: LiveData<Results?>
        get() = _singleCharacters

    val setOfCharacters:LiveData<List<Results>?>
        get() = _setOfCharacters



    fun getCharacterById(id: Int) {
        viewModelScope.launch {
            val result = repo.getCharacterById(id)
            _singleCharacters.value = result
        }
    }

    fun getAllCharacters(pageNum:Int){
        viewModelScope.launch {
            val result = repo.getAllCharacters(pageNum)
            _allCharacters.value = result
        }
    }

    fun getMultipleCharacters(multipleIds:String){
        viewModelScope.launch {
            val result = repo.getMultipleCharacters(multipleIds)
            _setOfCharacters.value = result

        }
    }
}