package co.edu.eam.unilocal.fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.actividades.CrearLugarActivity
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.bd.Lugares
import co.edu.eam.unilocal.databinding.FragmentMisLugaresBinding
import co.edu.eam.unilocal.modelo.Lugar

class MisLugaresFragment : Fragment() {

    lateinit var binding: FragmentMisLugaresBinding
    var lista:ArrayList<Lugar> = ArrayList()
    var codigoUsuario:Int = 0
    lateinit var adapter:LugarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMisLugaresBinding.inflate(inflater, container, false)

        binding.btnNuevoLugar.setOnClickListener { irACrearLugar() }

        val sp = requireActivity().getSharedPreferences("sesion", Context.MODE_PRIVATE)
        codigoUsuario = sp.getInt("codigo_usuario", -1)

        if( codigoUsuario != -1 ){
            lista = ArrayList()

            adapter = LugarAdapter(lista)
            binding.listaMisLugares.adapter = adapter
            binding.listaMisLugares.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()

        var misLugares = Lugares.listarPorPropietario(codigoUsuario)
        lista.clear()

        if(misLugares.isNotEmpty()){
            lista.addAll(misLugares)
        }

        adapter.notifyDataSetChanged()

    }

    private fun irACrearLugar(){
        startActivity( Intent(activity, CrearLugarActivity::class.java) )
    }

}