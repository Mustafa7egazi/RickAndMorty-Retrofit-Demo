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
    private var _isLoading = MutableLiveData<Boolean>()


    init {
        _isLoading.value = true
        _allCharacters.value = null
        _singleCharacters.value = null
    }


    val allCharacters: LiveData<List<Results>?>
        get() = _allCharacters

    val singleCharacters: LiveData<Results?>
        get() = _singleCharacters

    val setOfCharacters:LiveData<List<Results>?>
        get() = _setOfCharacters

    val isLoading:LiveData<Boolean>
        get() = _isLoading





    fun getCharacterById(id: Int) {
        viewModelScope.launch {
            val result = repo.getCharacterById(id)
            _singleCharacters.value = result
            _isLoading.value = false
        }
    }

    fun getAllCharacters(pageNum:Int){
        viewModelScope.launch {
            val result = repo.getAllCharacters(pageNum)
            _allCharacters.value = result
            if (_allCharacters.value != null){
                _isLoading.value = false
            }
        }
    }

    fun getMultipleCharacters(multipleIds:String){
        viewModelScope.launch {
            val result = repo.getMultipleCharacters(multipleIds)
            _setOfCharacters.value = result
            _isLoading.value = false

        }
    }
}