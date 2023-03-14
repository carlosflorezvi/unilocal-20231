package co.edu.eam.unilocal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textoBusqueda.setOnEditorActionListener { textView, i, keyEvent ->
            if( i == EditorInfo.IME_ACTION_SEARCH){
                Log.e("MainActivity", binding.textoBusqueda.text.toString())
            }
            true
        }

    }

    fun irALogin(v:View){
        /*val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)*/
        val intent = Intent(this, CrearLugarActivity::class.java)
        startActivity(intent)
    }

}