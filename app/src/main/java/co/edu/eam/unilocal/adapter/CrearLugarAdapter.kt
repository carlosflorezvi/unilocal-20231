package co.edu.eam.unilocal.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.edu.eam.unilocal.fragmentos.crearlugar.FormularioCrearLugarFragment
import co.edu.eam.unilocal.fragmentos.crearlugar.HorariosCrearLugarFragment
import co.edu.eam.unilocal.fragmentos.crearlugar.MapaCrearLugarFragment

class CrearLugarAdapter(var fragment: FragmentActivity, private val codigoUsuario:Int): FragmentStateAdapter(fragment) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FormularioCrearLugarFragment.newInstance(codigoUsuario)
            1 -> HorariosCrearLugarFragment.newInstance()
            else -> MapaCrearLugarFragment.newInstance()
        }
    }


}