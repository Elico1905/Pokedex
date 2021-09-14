package com.elico.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
//import com.elico.pokedex.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    //private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: PokeAdpter
    private var pokemonList = mutableListOf<PokesResult>()
    private var GLM: GridLayoutManager? = null


    val retrofit = Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val service: APIService2 = retrofit.create(APIService2::class.java)
    var dato: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        setContentView(R.layout.activity_main)

        animacion.setAnimation(R.raw.load)
        animacion.visibility = View.GONE


        adapter = PokeAdpter(pokemonList)

        ViewRecyclerView.layoutManager = LinearLayoutManager(this)
        ViewRecyclerView.adapter = adapter

        //GLM = GridLayoutManager(applicationContext,3,LinearLayoutManager.VERTICAL,false)
        //ViewRecyclerView.layoutManager = GLM

        ViewRecyclerView.adapter = adapter
        showData()
        ViewRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!ViewRecyclerView.canScrollVertically(1)) {
                    showData()
                }
            }
        })
    }

    private var cargando: Boolean = false

    private fun showData() {
        if (cargando == false) {
            if (dato < 152) {
                iniciar()

                var call = service.getPokemonList(if (dato == 151){1}else{10}, dato)

                println("-------")
                println("${dato}")
                println("-------")
                call.enqueue(object : Callback<PokesResponse> {
                    override fun onResponse(call: Call<PokesResponse>,response: Response<PokesResponse>) {
                        cargando = true
                        response.body()?.results.let { list ->
                            val timer = object : CountDownTimer(2000, 1000) {
                                override fun onTick(millisUntilFinished: Long) {}
                                override fun onFinish() {
                                    pokemonList.addAll(list!!)
                                    ocultar()
                                    adapter.notifyDataSetChanged()

                                    dato += 10
                                    if (dato == 150) {
                                        dato = 151
                                    }
                                    var x:Int = 0
                                    while (x<pokemonList.size){
                                        message("${(x + 1)}"+pokemonList.get(x).name)
                                        x++
                                    }
                                    cargando = false
                                }
                            }
                            timer.start()

                        }
                    }

                    override fun onFailure(call: Call<PokesResponse>, t: Throwable) {
                        call.cancel()
                    }
                })
            }

        }else{
            message("bloqueado..")
        }



    }

    private fun message(cadena:String){
        println("-------")
        println(cadena)
        println("-------")
    }

    private fun iniciar() {
        animacion.loop(true)
        animacion.repeatCount
        animacion.playAnimation()
        animacion.visibility = View.VISIBLE
    }

    private fun ocultar() {
        animacion.pauseAnimation()
        animacion.visibility = View.GONE
    }
}