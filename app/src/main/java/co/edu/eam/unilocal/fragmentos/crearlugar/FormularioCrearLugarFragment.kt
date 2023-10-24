package co.edu.eam.unilocal.fragmentos.crearlugar

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.bd.Ciudades
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.FragmentFomularioCrearLugarBinding
import co.edu.eam.unilocal.modelo.*
import com.google.android.material.snackbar.Snackbar

class FormularioCrearLugarFragment : Fragment() {

    lateinit var binding:FragmentFomularioCrearLugarBinding
    private var codigoUsuario:Int = 0
    var posCiudad:Int = -1
    var posCategoria:Int = -1
    lateinit var ciudades:ArrayList<Ciudad>
    lateinit var categorias:ArrayList<Categoria>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments != null){
            codigoUsuario = requireArguments().getInt("id_usuario")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFomularioCrearLugarBinding.inflate(inflater, container, false)

        ciudades = Ciudades.listar()
        categorias = Categorias.listar()

        cargarCiudades()
        cargarCategorias()

        return binding.root
    }

    fun cargarCiudades(){
        var lista = ciudades.map { c -> c.nombre }
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.ciudadLugar.adapter = adapter

        binding.ciudadLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                posCiudad = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }
    }

    fun cargarCategorias(){
        var lista = categorias.map { c -> c.nombre }
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, lista)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriaLugar.adapter = adapter

        binding.categoriaLugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                posCategoria = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }
    }

    fun crearNuevoLugar():Lugar?{

        val nombre = binding.nombreLugar.text.toString()
        val descripcion = binding.descripcionLugar.text.toString()
        val telefono = binding.telefonoLugar.text.toString()
        val direccion = binding.direccionLugar.text.toString()
        val idCiudad = ciudades[posCiudad].id
        val idCategoria = categorias[posCategoria].id
        var nuevoLugar:Lugar? = null

        if( nombre.isEmpty() ){
            binding.nombreLayout.error = getString(co.edu.eam.unilocal.R.string.es_obligatorio)
        }else{
            binding.nombreLayout.error = null
        }

        if( descripcion.isEmpty() ){
            binding.descripcionLayout.error = getString(co.edu.eam.unilocal.R.string.es_obligatorio)
        }else{
            binding.descripcionLayout.error = null
        }

        if( direccion.isEmpty() ){
            binding.direccionLayout.error = getString(co.edu.eam.unilocal.R.string.es_obligatorio)
        }else{
            binding.direccionLayout.error = null
        }

        if( telefono.isEmpty() ){
            binding.telefonoLayout.error = getString(co.edu.eam.unilocal.R.string.es_obligatorio)
        }else{
            binding.telefonoLayout.error = null
        }

        if(nombre.isNotEmpty() && descripcion.isNotEmpty() && telefono.isNotEmpty() && direccion.isNotEmpty() && idCiudad != -1 && idCategoria != -1)  {

            nuevoLugar = Lugar(
                nombre,
                descripcion,
                codigoUsuario,
                EstadoLugar.SIN_REVISAR,
                idCategoria,
                direccion,
                idCiudad
            )

            val telefonos: ArrayList<String> = ArrayList()
            telefonos.add(telefono)
            nuevoLugar!!.telefonos = telefonos
        }

        return nuevoLugar

    }

    companion object{

        fun newInstance(codigoUsuario: Int):FormularioCrearLugarFragment{
            val args = Bundle()
            args.putInt("id_usuario", codigoUsuario)

            val fragmento = FormularioCrearLugarFragment()
            fragmento.arguments = args
            return fragmento
        }

    }

}