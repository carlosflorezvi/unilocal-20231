package co.edu.eam.unilocal.fragmentos

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.actividades.CrearLugarActivity
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.databinding.FragmentMisLugaresBinding
import co.edu.eam.unilocal.modelo.Lugar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MisLugaresFragment : Fragment() {

    lateinit var binding: FragmentMisLugaresBinding
    var lista:ArrayList<Lugar> = ArrayList()
    lateinit var adapter:LugarAdapter
    var user:FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMisLugaresBinding.inflate(inflater, container, false)

        binding.btnNuevoLugar.setOnClickListener { irACrearLugar() }

        user = FirebaseAuth.getInstance().currentUser

        if( user != null ){

            adapter = LugarAdapter(lista)
            binding.listaMisLugares.adapter = adapter
            binding.listaMisLugares.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

            val docRef = Firebase.firestore
                .collection("lugares")
                .whereEqualTo("idCreador", user!!.uid)

            docRef.addSnapshotListener{ value, e ->

                if(value != null){
                    for(doc in value){
                        doc.toObject(Lugar::class.java).let {
                            it.key = doc.id
                            lista.add(it)
                            adapter.notifyItemInserted( lista.size-1 )
                        }
                    }
                }

            }

            /*.addOnSuccessListener {

                for(doc in it){

                    val lugar = doc.toObject(Lugar::class.java)
                    lugar.key = doc.id

                    lista.add(lugar)
                    adapter.notifyItemInserted( lista.size-1 )

                }

            }*/

        }


        return binding.root
    }

    private fun irACrearLugar(){
        startActivity( Intent(activity, CrearLugarActivity::class.java) )
    }

}