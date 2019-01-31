package br.com.luiz.recyclerview.api

import br.com.luiz.recyclerview.model.PokemonResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonAPI {

    @GET("/api/pokemon")
    fun search(@Query("size") size: Int): Observable<PokemonResponse>

}