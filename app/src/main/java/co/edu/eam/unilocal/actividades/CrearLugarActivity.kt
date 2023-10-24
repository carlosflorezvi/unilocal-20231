package co.edu.eam.unilocal.actividades

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.get
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.CrearLugarAdapter
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.ActivityCrearLugarBinding
import co.edu.eam.unilocal.fragmentos.crearlugar.FormularioCrearLugarFragment
import co.edu.eam.unilocal.fragmentos.crearlugar.HorariosCrearLugarFragment
import co.edu.eam.unilocal.fragmentos.crearlugar.MapaCrearLugarFragment
import co.edu.eam.unilocal.modelo.EstadoLugar
import co.edu.eam.unilocal.modelo.Lugar
import com.google.android.material.snackbar.Snackbar

class CrearLugarActivity : AppCompatActivity(){

    lateinit var binding: ActivityCrearLugarBinding
    var lugar:Lugar? = null
    var posicionActual:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCrearLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lugar = Lugar()

        val sh = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigoUsuario = sh.getInt("codigo_usuario", 0)

        binding.itemsForm.adapter = CrearLugarAdapter(this, codigoUsuario)
        binding.itemsForm.isUserInputEnabled = false

        binding.btnSgte.setOnClickListener { pasarSiguienteFormulario() }

    }

    fun pasarSiguienteFormulario(){

        val myFragment = supportFragmentManager.findFragmentByTag("f" + binding.itemsForm.currentItem)

        if(posicionActual==0){
            lugar = (myFragment as FormularioCrearLugarFragment).crearNuevoLugar()

            if(lugar == null){
                Snackbar.make(binding.root, getString(R.string.todos_obligatorios), Snackbar.LENGTH_LONG).show()
            }else{
                binding.itemsForm.setCurrentItem(1, true)
                posicionActual++
                binding.barraProgreso.progress = 2
            }

        }else if(posicionActual==1){

            val horarios = (myFragment as HorariosCrearLugarFragment).horarios

            if( horarios.isEmpty() ){
                Snackbar.make(binding.root, getString(R.string.horario_validacion), Snackbar.LENGTH_LONG).show()
            }else{
                lugar!!.horarios = horarios
                binding.itemsForm.setCurrentItem(2, true)
                posicionActual++
                binding.barraProgreso.progress = 3
            }

        }else{

            val posicion = (myFragment as MapaCrearLugarFragment).posicion

            if(posicion == null){
                Snackbar.make(binding.root, getString(R.string.mensaje_mapa), Snackbar.LENGTH_LONG).show()
            }else{
                lugar!!.posicion = posicion

                Lugares.crear(lugar!!)
                Snackbar.make(binding.root, getString(R.string.lugar_creado), Snackbar.LENGTH_LONG).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                }, 4000)
            }

        }

    }

}