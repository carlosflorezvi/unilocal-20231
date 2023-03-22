package co.edu.eam.unilocal.actividades

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.bd.Personas
import co.edu.eam.unilocal.databinding.ActivityLoginBinding
import co.edu.eam.unilocal.modelo.Moderador
import co.edu.eam.unilocal.modelo.Usuario

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)

        val correo = sp.getString("correo_usuario", "")
        val tipo = sp.getString("tipo_usuario", "")

        if(correo!!.isNotEmpty() && tipo!!.isNotEmpty()){
            Log.e("LoginActivity", "entro $tipo")
            when(tipo){
                "usuario" -> startActivity(Intent(this, MainActivity::class.java))
                "moderador" -> startActivity( Intent(this, ModeradorActivity::class.java) )
            }

            finish()

        }else{

            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnLogin.setOnClickListener { login() }
            binding.btnRegistro.setOnClickListener { irRegistro() }
        }

    }

    fun irRegistro(){
        startActivity( Intent(this, RegistroActivity::class.java) )
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
                val persona = Personas.login(correo.toString(), password.toString())

                if(persona!=null){

                    val tipo = if( persona is Usuario ) "usuario" else if( persona is Moderador ) "moderador" else "Administrador"

                    val sharedPreferences = this.getSharedPreferences( "sesion", Context.MODE_PRIVATE ).edit()
                    sharedPreferences.putString("correo_usuario", persona.correo)
                    sharedPreferences.putString("tipo_usuario", tipo)

                    sharedPreferences.commit()

                    when(persona){
                        is Usuario -> startActivity( Intent(this, MainActivity::class.java) )
                        is Moderador -> startActivity( Intent(this, ModeradorActivity::class.java) )
                    }
                }else{
                        Toast.makeText(this, getString(R.string.datos_incorrectos), Toast.LENGTH_LONG).show()
                }

            }catch (e:Exception){
                Toast.makeText(this, getString(R.string.datos_incorrectos), Toast.LENGTH_LONG).show()
            }

        }

    }

}
