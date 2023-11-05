package co.edu.eam.unilocal.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.databinding.FragmentFavoritosBinding
import co.edu.eam.unilocal.modelo.Lugar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FavoritosFragment : Fragment() {

    lateinit var binding: FragmentFavoritosBinding
    var lista:ArrayList<Lugar> = ArrayList()
    lateinit var adapter:LugarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritosBinding.inflate(inflater, container, false)

        adapter = LugarAdapter(lista)
        binding.listaFavoritos.adapter = adapter
        binding.listaFavoritos.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        Firebase.firestore
            .collection("usuarios")
            .document( FirebaseAuth.getInstance().currentUser!!.uid )
            .collection("favoritos")
            .get()
            .addOnSuccessListener {

                for( doc in it ){

                    Firebase.firestore
                        .collection("lugares")
                        .document( doc.id )
                        .get()
                        .addOnSuccessListener { l ->
                            val lugar = l.toObject(Lugar::class.java)
                            lugar!!.key = l.id
                            lista.add( lugar )
                            adapter.notifyItemInserted( lista.size-1 )
                        }

                }

            }

        return binding.root
    }


}