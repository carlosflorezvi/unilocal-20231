package co.edu.eam.unilocal.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.edu.eam.unilocal.fragmentos.detallelugar.ComentariosLugarFragment
import co.edu.eam.unilocal.fragmentos.detallelugar.InfoLugarFragment

class ViewPagerAdapter(var fragment:FragmentActivity, private var codigoLugar:String): FragmentStateAdapter(fragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> InfoLugarFragment.newInstance(codigoLugar)
            else -> ComentariosLugarFragment.newInstance(codigoLugar)
        }
    }


}