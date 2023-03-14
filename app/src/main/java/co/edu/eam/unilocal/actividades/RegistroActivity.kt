package co.edu.eam.unilocal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ToggleButton
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityRegistroBinding
import co.edu.eam.unilocal.modelo.Usuario
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class RegistroActivity : AppCompatActivity() {

    lateinit var binding:ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistro.setOnClickListener { registrarUsuario() }

    }

    fun registrarUsuario(){

        val nombre = binding.nombreUsuario.text.toString()
        val nickname = binding.nicknameUsuario.text.toString()
        val email = binding.emailUsuario.text.toString()
        val password = binding.passUsuario.text.toString()

        if( nombre.isEmpty() ){
            binding.nombreLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.nombreLayout.error = null
        }

        if( nickname.isEmpty() ){
            binding.nicknameLayout.error = getString(R.string.es_obligatorio)
        }else{

            if( nickname.length > 10 ){
                binding.nicknameLayout.error = getString(R.string.maximo_caracteres)
            }else{
                binding.nicknameLayout.error = null
            }

        }

        if( email.isEmpty() ){
            binding.emailLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.emailLayout.error = null
        }

        if( password.isEmpty() ){
            binding.passwordLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.passwordLayout.error = null
        }

        if( nombre.isNotEmpty() && nickname.isNotEmpty() && nickname.length <= 10 && email.isNotEmpty() && password.isNotEmpty() ){

            val usuario = Usuario(4, nombre, nickname, email, password)
            Usuarios.agregar(usuario)
            Toast.makeText(this, getString(R.string.usuario_creado), Toast.LENGTH_LONG).show()

            Log.e("RegistroActivity", Usuarios.listar().toString())
        }

    }

}