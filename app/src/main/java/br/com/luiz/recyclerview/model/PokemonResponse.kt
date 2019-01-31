package br.com.luiz.recyclerview.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(val content: List<Pokemon>)

data class Pokemon(

    //qd mudar o nome do atributo da resposta no json
    @SerializedName("number") val numero: String,
    @SerializedName("name") val nome: String,
    val imageURL: String

)