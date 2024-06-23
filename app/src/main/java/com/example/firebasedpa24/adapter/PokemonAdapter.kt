package com.example.firebasedpa24.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasedpa24.R
import com.example.firebasedpa24.model.PokemonModel
import com.squareup.picasso.Picasso

class PokemonAdapter(private var pokemonList: List<PokemonModel>)
    :RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
        class ViewHolder(itemView: View)
            : RecyclerView.ViewHolder(itemView){
                val ivPokemon: ImageView = itemView.findViewById(R.id.ivPokemon)
                val tvPokemonName: TextView = itemView.findViewById(R.id.tvPokemonName)
            }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_pokemon,parent,false))
    }
    override fun getItemCount(): Int {
        return pokemonList.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPokemon = pokemonList[position]
        holder.tvPokemonName.text = itemPokemon.name
        Picasso.get().load(itemPokemon.url).into(holder.ivPokemon)
    }

}