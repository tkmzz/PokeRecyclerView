package br.com.luiz.recyclerview.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import br.com.luiz.recyclerview.R
import br.com.luiz.recyclerview.api.getPokemonAPI
import br.com.luiz.recyclerview.model.Pokemon
import br.com.luiz.recyclerview.model.PokemonResponse
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_lista.*

class ListaActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)

        loadData()

    }

    private fun showList(pokemons: List<Pokemon>){
        rvPokemons.adapter = ListaPokemonAdapter(this, pokemons, {
            Toast.makeText(this, it.nome, Toast.LENGTH_SHORT).show()
        })
        rvPokemons.layoutManager = LinearLayoutManager(this)
    }

    private fun showError(error: String){
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    private fun loadData() {
        getPokemonAPI()
                .search(151)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<PokemonResponse> {
                    override fun onComplete() {
                        Log.i("ListaActivity", "COMPLETE")
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    override fun onNext(t: PokemonResponse) {
                        showList(t.content)
                    }

                    override fun onError(e: Throwable) {
                        showError(e.message!!)
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
