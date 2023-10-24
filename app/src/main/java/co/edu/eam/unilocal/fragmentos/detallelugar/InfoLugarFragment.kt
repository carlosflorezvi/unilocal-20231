package co.edu.eam.unilocal.fragmentos.detallelugar

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.bd.Comentarios
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.FragmentInfoLugarBinding
import co.edu.eam.unilocal.modelo.Lugar
import co.edu.eam.unilocal.modelo.Usuario

class InfoLugarFragment : Fragment() {

    lateinit var binding:FragmentInfoLugarBinding
    private var lugar: Lugar? = null
    private var codigoLugar:Int = 0
    private var codigoUsuario:Int = 0
    private var esFavorito = false
    private var typefaceSolid:Typeface? = null
    private var typefaceRegular:Typeface? = null
    private lateinit var usuario:Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments != null){
            codigoLugar = requireArguments().getInt("id_lugar")
            codigoUsuario = requireArguments().getInt("id_usuario")

            usuario = Usuarios.obtener(codigoUsuario)!!
            esFavorito = usuario.esFavorito(codigoLugar)
        }

        typefaceSolid = ResourcesCompat.getFont(requireContext(), R.font.font_awesome_6_free_solid_900)
        typefaceRegular = ResourcesCompat.getFont(requireContext(), R.font.font_awesome_6_free_regular_400)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentInfoLugarBinding.inflate(inflater, container, false)

        lugar = Lugares.obtener(codigoLugar)

        if(lugar != null) {
            cargarInformacion(lugar!!)
            dibujarEstrellas(lugar!!)

            binding.btnFavorito.setOnClickListener { marcarFavorito(esFavorito) }

            if(esFavorito){
                binding.btnFavorito.typeface = typefaceSolid
                binding.btnFavorito.text = '\uf004'.toString()
            }

        }

        return binding.root
    }

    fun cargarInformacion(lugar:Lugar){

        binding.nombreLugar.text = lugar.nombre
        binding.descripcionLugar.text = lugar.descripcion
        binding.direccionLugar.text = lugar.direccion
        binding.iconoCategoria.text = Categorias.obtener(lugar.idCategoria)!!.icono

        var telefonos = ""

        if(lugar.telefonos.isNotEmpty()) {
            for (tel in lugar.telefonos) {
                telefonos += "$tel, "
            }
            telefonos = telefonos.substring(0, telefonos.length - 2)
        }else{
            telefonos = "No hay teléfono"
        }

        binding.telefonoLugar.text = telefonos

        var horarios = ""

        for( horario in lugar.horarios ){
            for(dia in horario.diaSemana){
                horarios += "${dia.toString().lowercase().replaceFirstChar { it.uppercase() }}: ${horario.horaInicio}:00 - ${horario.horaCierre}:00\n"
            }
        }

        binding.horariosLugar.text = horarios

    }

    fun dibujarEstrellas(lugar:Lugar){

        val calificacion = lugar.obtenerCalificacionPromedio( Comentarios.listar(lugar.id) )

        for( i in 0..calificacion ){
            (binding.estrellas.lista[i] as TextView).setTextColor( ContextCompat.getColor(requireContext(), R.color.yellow) )
        }

    }

    fun marcarFavorito(valor:Boolean){

        if(!valor){
            esFavorito = true
            binding.btnFavorito.typeface = typefaceSolid
            binding.btnFavorito.text = '\uf004'.toString()
            usuario.favoritos.add( codigoLugar )
        }else{
            esFavorito = false
            binding.btnFavorito.typeface = typefaceRegular
            binding.btnFavorito.text = '\uf004'.toString()
            usuario.favoritos.remove( codigoLugar )
        }

    }

    companion object{

        fun newInstance(codigoLugar:Int, codigoUsuario:Int): InfoLugarFragment {
            val args = Bundle()
            args.putInt("id_lugar", codigoLugar)
            args.putInt("id_usuario", codigoUsuario)

            val fragmento = InfoLugarFragment()
            fragmento.arguments = args
            return fragmento
        }

    }


}