package co.edu.eam.unilocal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.databinding.ActivityResultadoBusquedaBinding
import co.edu.eam.unilocal.modelo.Lugar

class ResultadoBusquedaActivity : AppCompatActivity() {

    lateinit var binding:ActivityResultadoBusquedaBinding
    var textoBusqueda:String = ""
    lateinit var listaLugares:ArrayList<Lugar>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultadoBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textoBusqueda = intent.extras!!.getString("texto", "")
        listaLugares = ArrayList()

        if(textoBusqueda.isNotEmpty()){
           // listaLugares = Lugares.buscarNombre(textoBusqueda)
        }

        val adapter = LugarAdapter(listaLugares)
        binding.listaLugares.adapter = adapter
        binding.listaLugares.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}