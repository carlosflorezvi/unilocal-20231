package co.edu.eam.unilocal.fragmentos.detallelugar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.ComentarioAdapter
import co.edu.eam.unilocal.bd.Comentarios
import co.edu.eam.unilocal.databinding.FragmentComentariosLugarBinding
import co.edu.eam.unilocal.modelo.Comentario
import com.google.android.material.snackbar.Snackbar

class ComentariosLugarFragment : Fragment() {

    lateinit var binding:FragmentComentariosLugarBinding
    var lista:ArrayList<Comentario> = ArrayList()
    private var codigoLugar:Int = 0
    private lateinit var adapter: ComentarioAdapter
    private var codigoUsuario:Int = 0
    private var colorPorDefecto: Int = 0
    private var estrellas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments != null){
            codigoLugar = requireArguments().getInt("id_lugar")
            codigoUsuario = requireArguments().getInt("id_usuario")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComentariosLugarBinding.inflate(inflater, container, false)

        colorPorDefecto = binding.estrellas.e1.textColors.defaultColor

        lista = Comentarios.listar(codigoLugar)
        adapter = ComentarioAdapter(lista)
        binding.listaComentarios.adapter = adapter
        binding.listaComentarios.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        if(lista.isEmpty()){
            binding.txtSinComentarios.visibility = View.VISIBLE
        }

        binding.comentarLugar.setOnClickListener { hacerComentario() }

        for ( i in 0 until binding.estrellas.lista.childCount){
            (binding.estrellas.lista[i] as TextView).setOnClickListener { presionarEstrella(i) }
        }

        return binding.root
    }

    fun hacerComentario(){

        val texto = binding.mensajeComentario.text.toString()

        if( texto.isNotEmpty() && estrellas > 0){
            val comentario = Comentarios.crear( Comentario(texto, codigoUsuario, codigoLugar, estrellas) )

            limpiarFormulario()
            Snackbar.make(binding.root, getString(R.string.comentario_realizado), Snackbar.LENGTH_LONG ).show()

            lista.add(comentario)
            adapter.notifyItemInserted(lista.size-1)

        }else{
            Snackbar.make(binding.root, getString(R.string.comentario_error), Snackbar.LENGTH_LONG ).show()
        }

    }

    private fun limpiarFormulario(){
        binding.mensajeComentario.setText("")
        borrarSeleccion()
        estrellas = 0
    }

    private fun presionarEstrella(pos:Int){
        estrellas = pos+1
        borrarSeleccion()
        for( i in 0..pos ){
            (binding.estrellas.lista[i] as TextView).setTextColor( ContextCompat.getColor(requireContext(), R.color.yellow) )
        }
    }

    private fun borrarSeleccion(){
        for ( i in 0 until binding.estrellas.lista.childCount){
            (binding.estrellas.lista[i] as TextView).setTextColor( colorPorDefecto )
        }
    }

    companion object{

        fun newInstance(codigoLugar:Int, codigoUsuario:Int): ComentariosLugarFragment {

            val args = Bundle()
            args.putInt("id_lugar", codigoLugar)
            args.putInt("id_usuario", codigoUsuario)

            val fragmento = ComentariosLugarFragment()
            fragmento.arguments = args
            return fragmento

        }

    }

}