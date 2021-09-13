package com.elico.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.elico.pokedex.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PokeAdpter
    private var pokemonList = mutableListOf<PokesResult>()
    private var GLM:GridLayoutManager? = null


    val retrofit = Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service: APIService2 = retrofit.create(APIService2::class.java)
    var dato: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PokeAdpter(pokemonList)
        //ViewRecyclerView.layoutManager = LinearLayoutManager(this)
        //ViewRecyclerView.adapter = adapter

        GLM = GridLayoutManager(applicationContext,3,LinearLayoutManager.VERTICAL,false)
        ViewRecyclerView.layoutManager = GLM
        ViewRecyclerView.adapter = adapter




            showData()



        ViewRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(!ViewRecyclerView.canScrollVertically(1)){
                    showData()
                }
            }
        })


    }




    private fun showData() {
        val call = service.getPokemonList(12, dato)
        call.enqueue(object : Callback<PokesResponse> {
            override fun onResponse(call: Call<PokesResponse>, response: Response<PokesResponse>) {
                response.body()?.results.let { list ->
                    pokemonList.addAll(list!!)
                    dato += 12
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<PokesResponse>, t: Throwable) {
                call.cancel()
            }
        })
    }
}