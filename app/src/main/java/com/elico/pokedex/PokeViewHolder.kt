package com.elico.pokedex

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.elico.pokedex.databinding.ItemListBinding
import com.squareup.picasso.Picasso

class PokeViewHolder(view: View):RecyclerView.ViewHolder(view){
    private val binding = ItemListBinding.bind(view)
    private val url:String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

    fun bind(poke:String,numero:Int){
        binding.recyclerNombre.text = poke
        Picasso.get().load("${url}${numero}.png").into(binding.recyclerImagen)
        binding.recyclerNum.text = "${numero}"
        binding.recyclerImagen.setOnClickListener {
            println("-------")
            println("nombre: ${poke}    -----   numero:${numero}")
            println("-------")
        }
    }
}