package co.edu.eam.unilocal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.ActivityDetalleLugarBinding
import co.edu.eam.unilocal.modelo.Lugar

class DetalleLugarActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetalleLugarBinding
    private var codigoLugar:Int = 0
    var lugar:Lugar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        codigoLugar = intent.extras!!.getInt("codigo")

        lugar = Lugares.obtener(codigoLugar)

        if(lugar != null){
            binding.nombreLugar.text = lugar!!.nombre
            binding.descripcionLugar.text = lugar!!.descripcion
            binding.telefonoLugar.text = lugar!!.telefonos.toString()
            binding.direccionLugar.text = lugar!!.direccion
            binding.iconoCategoria.text = Categorias.obtener(lugar!!.idCategoria)!!.icono
        }

    }
}