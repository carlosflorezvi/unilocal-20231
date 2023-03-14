package co.edu.eam.unilocal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Usuarios
import co.edu.eam.unilocal.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener { login() }

    }

    fun login(){

        val correo = binding.emailUsuario.text
        var password = binding.passwordUsuario.text

        if( correo.isEmpty() ){
            binding.emailLayout.isErrorEnabled = true
            binding.emailLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.emailLayout.error = null
        }

        if( password.isEmpty() ){
            binding.passLayout.error = getString(R.string.es_obligatorio)
        }else{
            binding.passLayout.error = null
        }

        if( correo.isNotEmpty() && password.isNotEmpty() ){

            try {
                val usuario = Usuarios.login(correo.toString(), password.toString())
                Toast.makeText(this, getString(R.string.datos_correctos), Toast.LENGTH_LONG).show()

            }catch (e:Exception){
                Toast.makeText(this, getString(R.string.datos_incorrectos), Toast.LENGTH_LONG).show()
            }

        }

    }

}
