package com.example.rickandmorty.repository


import com.example.rickandmorty.data.ApiService
import com.example.rickandmorty.pojo.Characters
import com.example.rickandmorty.pojo.Results

class SharedRepository {

    suspend fun getAllCharacters(): List<Results>? {
        val request = ApiService.retrofitService.getAllCharacters()
        if (request.isSuccessful) {
            if (request.body() != null)
                return request.body()!!.results
        }
        return null
    }

    suspend fun getCharacterById(id: Int): Results? {
        val request = ApiService.retrofitService.getCharacterById(id)
        if (request.isSuccessful) {
            return request.body()
        }
        return null
    }
}