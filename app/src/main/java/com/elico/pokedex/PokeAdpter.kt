package com.elico.pokedex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PokeAdpter(private val pokemonList:MutableList<PokesResult>):RecyclerView.Adapter<PokeViewHolder>(){

    override fun getItemCount(): Int = pokemonList.size

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val item:String = pokemonList[position].name
        holder.bind(item,(position + 1))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val LayoutInflater =  LayoutInflater.from(parent.context)
        return PokeViewHolder(LayoutInflater.inflate(R.layout.item_list,parent,false))
    }
}