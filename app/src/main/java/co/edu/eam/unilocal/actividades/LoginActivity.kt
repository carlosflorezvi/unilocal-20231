package co.edu.eam.unilocal.actividades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.ActivityLoginBinding
import co.edu.eam.unilocal.modelo.Rol
import co.edu.eam.unilocal.modelo.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser

        if( user!= null) {
            hacerRedireccion(user)
        } else{

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

        val correo = binding.emailUsuario.text.toString()
        var password = binding.passwordUsuario.text.toString()

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


            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword( correo, password )
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        val user = FirebaseAuth.getInstance().currentUser
                        if( user!= null) {
                            hacerRedireccion(user)
                        }
                    }else{
                        Snackbar.make(binding.root, "Login incorrecto", Snackbar.LENGTH_LONG).show()
                    }

                }
                .addOnFailureListener {
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG).show()
                }

        }

    }

    fun hacerRedireccion(user:FirebaseUser){
        Firebase.firestore
            .collection("usuarios")
            .document(user.uid)
            .get()
            .addOnSuccessListener { u ->

                var intent:Intent

                val rol = u.toObject(Usuario::class.java)?.rol

                if(rol == Rol.CLIENTE) {
                    intent = Intent(this, MainActivity::class.java)
                }else if(rol == Rol.MODERADOR) {
                    intent = Intent(this, ModeradorActivity::class.java)
                }else{
                    intent = Intent(this, AdministradorActivity::class.java)
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity( intent )
                finish()

            }
    }


}
