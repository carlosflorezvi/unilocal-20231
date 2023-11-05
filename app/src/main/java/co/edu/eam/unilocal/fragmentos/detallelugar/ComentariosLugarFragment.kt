package co.edu.eam.unilocal.fragmentos.detallelugar

import android.os.Bundle
import android.util.Log
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
import co.edu.eam.unilocal.databinding.FragmentComentariosLugarBinding
import co.edu.eam.unilocal.modelo.Comentario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ComentariosLugarFragment : Fragment() {

    lateinit var binding:FragmentComentariosLugarBinding
    lateinit var lista:ArrayList<Comentario>
    private var codigoLugar:String = ""
    private lateinit var adapter: ComentarioAdapter
    private var colorPorDefecto: Int = 0
    private var estrellas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments != null){
            codigoLugar = requireArguments().getString("id_lugar", "")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComentariosLugarBinding.inflate(inflater, container, false)

        colorPorDefecto = binding.estrellas.e1.textColors.defaultColor

        lista = ArrayList()
        adapter = ComentarioAdapter(lista)
        binding.listaComentarios.adapter = adapter
        binding.listaComentarios.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        Firebase.firestore
            .collection("lugares")
            .document(codigoLugar)
            .collection("comentarios")
            .get()
            .addOnSuccessListener {

                if(it.isEmpty){
                    binding.txtSinComentarios.visibility = View.VISIBLE
                }else {

                    for (doc in it) {
                        val comentario = doc.toObject(Comentario::class.java)
                        if (comentario != null) {
                            comentario.key = doc.id
                            lista.add(comentario)
                            adapter.notifyItemInserted(lista.size - 1)
                        }
                    }

                }

            }
            .addOnFailureListener {
                Log.e("DETALLE_LUGAR", "${it.message}")
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

            val user = FirebaseAuth.getInstance().currentUser

            if(user!=null) {

                val comentario = Comentario(texto, user.uid, estrellas)

                Firebase.firestore
                    .collection("lugares")
                    .document(codigoLugar)
                    .collection("comentarios")
                    .add(comentario)
                    .addOnSuccessListener {

                        binding.txtSinComentarios.visibility = View.GONE
                        limpiarFormulario()
                        Snackbar.make(
                            binding.root,
                            getString(R.string.comentario_realizado),
                            Snackbar.LENGTH_LONG
                        ).show()

                        lista.add(comentario)
                        adapter.notifyItemInserted(lista.size - 1)

                    }
                    .addOnFailureListener {
                        Snackbar.make(binding.root, "${it.message}", Snackbar.LENGTH_LONG).show()
                    }

            }

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

        fun newInstance(codigoLugar:String): ComentariosLugarFragment {

            val args = Bundle()
            args.putString("id_lugar", codigoLugar)

            val fragmento = ComentariosLugarFragment()
            fragmento.arguments = args
            return fragmento

        }

    }

}