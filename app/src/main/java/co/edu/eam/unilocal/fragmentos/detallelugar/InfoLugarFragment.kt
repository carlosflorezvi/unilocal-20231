package co.edu.eam.unilocal.fragmentos.detallelugar

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.FragmentInfoLugarBinding
import co.edu.eam.unilocal.modelo.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

class InfoLugarFragment : Fragment() {

    lateinit var binding:FragmentInfoLugarBinding
    private var codigoLugar:String = ""
    private var esFavorito = false
    private var typefaceSolid:Typeface? = null
    private var typefaceRegular:Typeface? = null
    private var user:FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = FirebaseAuth.getInstance().currentUser

        if(arguments != null){
            codigoLugar = requireArguments().getString("id_lugar", "")
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

        Firebase.firestore
            .collection("lugares")
            .document(codigoLugar)
            .get()
            .addOnSuccessListener {
                var lugarF = it.toObject(Lugar::class.java)

                if(lugarF != null){
                    lugarF.key = it.id

                    cargarInformacion(lugarF)
                    dibujarEstrellas()

                }

            }
            .addOnFailureListener{
                Log.e("DETALLE_LUGAR", "${it.message}")
            }

        user?.let {
            Firebase.firestore
                .collection("usuarios")
                .document(it.uid)
                .collection("favoritos")
                .document(codigoLugar)
                .get()
                .addOnSuccessListener { l ->

                    if(l.exists()){
                        esFavorito = true
                        binding.btnFavorito.typeface = typefaceSolid
                        binding.btnFavorito.text = '\uf004'.toString()
                    }

                }
        }

        binding.btnFavorito.setOnClickListener { marcarFavorito(esFavorito) }

        return binding.root
    }

    fun cargarInformacion(lugar:Lugar){

        binding.nombreLugar.text = lugar.nombre
        binding.descripcionLugar.text = lugar.descripcion
        binding.direccionLugar.text = lugar.direccion

        Firebase.firestore
            .collection("categorias")
            .whereEqualTo("id", lugar.idCategoria)
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    binding.iconoCategoria.text = doc.toObject(Categoria::class.java).icono
                }
            }

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

    fun dibujarEstrellas(){

        Firebase.firestore
            .collection("lugares")
            .document(codigoLugar)
            .collection("comentarios")
            .get()
            .addOnSuccessListener {
                if(!it.isEmpty) {
                    var sumaCalificacion = 0

                    for (doc in it) {
                        val com = doc.toObject(Comentario::class.java)
                        sumaCalificacion += com.calificacion
                    }

                    var calificacion = sumaCalificacion/it.size()

                    for (i in 0..calificacion) {
                        (binding.estrellas.lista[i] as TextView).setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.yellow
                            )
                        )
                    }
                }
            }

    }

    fun marcarFavorito(valor:Boolean){

        val fecha = HashMap<String, Date>()
        fecha.put("fecha", Date())

        if(!valor){
            esFavorito = true
            binding.btnFavorito.typeface = typefaceSolid
            binding.btnFavorito.text = '\uf004'.toString()

            Firebase.firestore
                .collection("usuarios")
                .document(user!!.uid)
                .collection("favoritos")
                .document(codigoLugar)
                .set( fecha )

        }else{
            esFavorito = false
            binding.btnFavorito.typeface = typefaceRegular
            binding.btnFavorito.text = '\uf004'.toString()

            Firebase.firestore
                .collection("usuarios")
                .document(user!!.uid)
                .collection("favoritos")
                .document(codigoLugar)
                .delete()

        }

    }

    companion object{

        fun newInstance(codigoLugar:String): InfoLugarFragment {
            val args = Bundle()
            args.putString("id_lugar", codigoLugar)

            val fragmento = InfoLugarFragment()
            fragmento.arguments = args
            return fragmento
        }

    }


}