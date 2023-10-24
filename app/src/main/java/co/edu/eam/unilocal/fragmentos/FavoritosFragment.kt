package co.edu.eam.unilocal.fragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.FragmentFavoritosBinding
import co.edu.eam.unilocal.modelo.Lugar

class FavoritosFragment : Fragment() {

    lateinit var binding: FragmentFavoritosBinding
    var lista:ArrayList<Lugar> = ArrayList()
    var codigoUsuario:Int = 0
    lateinit var adapter:LugarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritosBinding.inflate(inflater, container, false)

        val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        codigoUsuario = sp.getInt("codigo_usuario", -1)

        if( codigoUsuario != -1 ){
            lista = ArrayList()

            adapter = LugarAdapter(lista)
            binding.listaFavoritos.adapter = adapter
            binding.listaFavoritos.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val favs = Usuarios.obtener(codigoUsuario)!!.favoritos
        lista.clear()

        if(favs.isEmpty()){
            binding.txtVacio.visibility = View.VISIBLE
            binding.listaFavoritos.visibility = View.GONE
        }else{
            favs.map { l -> Lugares.obtener(l)!! }.toCollection(lista)
            binding.txtVacio.visibility = View.GONE
            binding.listaFavoritos.visibility = View.VISIBLE
        }

        adapter.notifyDataSetChanged()

    }

}