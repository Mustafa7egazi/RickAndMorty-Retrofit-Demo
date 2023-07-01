package com.example.rickandmorty.data
import com.example.rickandmorty.pojo.Characters
import com.example.rickandmorty.pojo.Results
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://rickandmortyapi.com/api/"

private val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

const val timeoutDuration = 5L // Timeout duration in seconds
val client: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(timeoutDuration, TimeUnit.SECONDS)
    .readTimeout(timeoutDuration, TimeUnit.SECONDS)
    .writeTimeout(timeoutDuration, TimeUnit.SECONDS)
    .build()

var retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()


interface RickAndMortyApi {
    /*
    e.g
    @GET("character/300")
      fun getCharacterById():Call<CharacterModel>
     */

    @GET("character/")
    suspend fun getAllCharacters(@Query("page") pageNum: Int): Response<Characters>


    @GET("character/{char-id}")
    suspend fun getCharacterById(@Path("char-id") id:Int):Response<Results>

    @GET("character/{multiple-ids}")
    suspend fun getMultipleCharacters(@Path("multiple-ids") multipleIds:String):Response<List<Results>>
}

object ApiService {
    val retrofitService: RickAndMortyApi by lazy {
        retrofit.create(RickAndMortyApi::class.java)
    }
}