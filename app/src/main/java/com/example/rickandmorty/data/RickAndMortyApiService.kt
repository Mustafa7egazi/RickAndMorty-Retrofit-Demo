package com.example.rickandmorty.data
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory



private const val BASE_URL = "https://rickandmortyapi.com/api/"

private val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

var retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()


interface RickAndMortyApi {
    /*
    e.g
    @GET("character/300")
      fun getCharacterById():Call<CharacterModel>
     */

}

object ApiService {
    val retrofitService: RickAndMortyApi by lazy {
        retrofit.create(RickAndMortyApi::class.java)
    }
}