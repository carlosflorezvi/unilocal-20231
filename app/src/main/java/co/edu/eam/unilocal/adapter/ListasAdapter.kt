package co.edu.eam.unilocal.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.edu.eam.unilocal.fragmentos.detallelugar.ComentariosLugarFragment
import co.edu.eam.unilocal.fragmentos.detallelugar.InfoLugarFragment
import co.edu.eam.unilocal.fragmentos.moderador.ListaLugaresFragment
import co.edu.eam.unilocal.modelo.EstadoLugar

class ListasAdapter(var fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ListaLugaresFragment.newInstance(EstadoLugar.SIN_REVISAR)
            1 -> ListaLugaresFragment.newInstance(EstadoLugar.RECHAZADO)
            else -> ListaLugaresFragment.newInstance(EstadoLugar.ACEPTADO)
        }
    }

}