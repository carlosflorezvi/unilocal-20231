package co.edu.eam.unilocal.actividades

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityMainBinding
import co.edu.eam.unilocal.fragmentos.FavoritosFragment
import co.edu.eam.unilocal.fragmentos.InicioFragment
import co.edu.eam.unilocal.fragmentos.MisLugaresFragment
import co.edu.eam.unilocal.utils.Idioma
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener

class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding
    private var MENU_INICIO = "inicio"
    private var MENU_MIS_LUGARES = "mis_lugares"
    private var MENU_FAVORITOS = "favoritos"
    private lateinit var sh:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sh = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigoUsuario = sh.getInt("codigo_usuario", 0)

        if( codigoUsuario != 0 ){
            val usuario = Usuarios.obtener(codigoUsuario)
            val encabezado = binding.navView.getHeaderView(0)

            encabezado.findViewById<TextView>(R.id.enc_nombre_usuario).text = usuario!!.nombre
            encabezado.findViewById<TextView>(R.id.enc_correo_usuario).text = usuario.correo
        }

        reemplazarFragmento(1, MENU_INICIO)

        binding.barraInferior.setOnItemSelectedListener {
            when(it.itemId){

                R.id.menu_inicio -> reemplazarFragmento(1, MENU_INICIO)
                R.id.menu_mis_lugares -> reemplazarFragmento(2, MENU_MIS_LUGARES)
                R.id.menu_favoritos -> reemplazarFragmento(3, MENU_FAVORITOS)

            }
            true
        }

        val actionBarDrawerToggle: ActionBarDrawerToggle =
            object : ActionBarDrawerToggle(this, binding.drawerLayout, R.string.abrir, R.string.cerrar) {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    super.onDrawerSlide(drawerView, slideOffset)
                    val slideX: Float = drawerView.width * slideOffset
                    binding.layoutContent.translationX = slideX
                }
            }

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)

        binding.navView.setNavigationItemSelectedListener(this)

    }

    fun reemplazarFragmento(valor:Int, nombre:String){

        var fragmento:Fragment = when(valor) {
            1 -> InicioFragment()
            2 -> MisLugaresFragment()
            else -> FavoritosFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace( binding.contenidoPrincipal.id, fragmento )
            .addToBackStack(nombre)
            .commit()

    }

    fun mostrarBarraNavegacion(){
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        val count = supportFragmentManager.backStackEntryCount

        if(count > 0) {
            val nombre = supportFragmentManager.getBackStackEntryAt(count - 1).name
            when (nombre) {
                MENU_INICIO -> binding.barraInferior.menu.getItem(0).isChecked = true
                MENU_MIS_LUGARES -> binding.barraInferior.menu.getItem(1).isChecked = true
                else -> binding.barraInferior.menu.getItem(2).isChecked = true
            }
        }

    }

    fun cerrarSesion(){
        val spe = sh.edit()
        spe.clear()
        spe.commit()
        finish()
    }

    fun irLugaresRechazados(){
        //TODO falta
    }

    fun irPerfil(){
        //TODO falta
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_lugares_rechazados -> irLugaresRechazados()
            R.id.menu_perfil -> irPerfil()
            R.id.menu_cambiar_idioma -> cambiarIdioma()
            R.id.menu_cerrar_sesion -> cerrarSesion()
        }

        item.isChecked = true
        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return true

    }

    fun cambiarIdioma(){
        Idioma.selecionarIdioma(this)

        val intent = intent
        if (intent != null) {
            intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            finish()
            startActivity(intent)
        }

    }

    override fun attachBaseContext(newBase: Context?) {
        val localeUpdatedContext: ContextWrapper? = Idioma.cambiarIdioma(newBase!!)
        super.attachBaseContext(localeUpdatedContext)
    }


}