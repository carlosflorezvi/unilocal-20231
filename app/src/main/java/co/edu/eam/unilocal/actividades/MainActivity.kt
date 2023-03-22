package co.edu.eam.unilocal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.ActivityMainBinding
import co.edu.eam.unilocal.fragmentos.FavoritosFragment
import co.edu.eam.unilocal.fragmentos.InicioFragment
import co.edu.eam.unilocal.fragmentos.MisLugaresFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.barraInferior.setOnItemSelectedListener {
            when(it.itemId){

                R.id.menu_inicio -> reemplazarFragmento(1)
                R.id.menu_mis_lugares -> reemplazarFragmento(2)
                R.id.menu_favoritos -> reemplazarFragmento(3)

            }
            true
        }

    }

    fun reemplazarFragmento(valor:Int){

        var fragmento:Fragment = if(valor == 1){
            InicioFragment()
        }else if(valor == 2){
            MisLugaresFragment()
        }else{
            FavoritosFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace( binding.contenidoPrincipal.id, fragmento )
            .addToBackStack("fragmento_$valor")
            .commit()

    }

}