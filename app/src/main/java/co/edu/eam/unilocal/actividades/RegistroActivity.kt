package co.edu.eam.unilocal.actividades

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.ActivityRegistroBinding
import co.edu.eam.unilocal.modelo.Rol
import co.edu.eam.unilocal.modelo.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {

    lateinit var binding:ActivityRegistroBinding
    //private lateinit var db:UnilocalDbHelper
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegistro.setOnClickListener { registrarUsuario() }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(R.layout.dialogo_progreso)
        dialog = builder.create()

        //db = UnilocalDbHelper(this)
    }

    private fun registrarUsuario(){

        setDialog(true)

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

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword( email, password )
                .addOnCompleteListener {

                    if(it.isSuccessful){

                        val usuario = FirebaseAuth.getInstance().currentUser

                        if(usuario!=null){

                            verificarEmail(usuario)

                            val usuarioRegistro = Usuario(nombre, nickname, Rol.CLIENTE)

                            Firebase.firestore
                                .collection("usuarios")
                                .document(usuario.uid)
                                .set(usuarioRegistro)
                                .addOnSuccessListener {
                                    Snackbar.make(binding.root, getString(R.string.usuario_creado), Snackbar.LENGTH_LONG).show()

                                    setDialog(false)

                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    startActivity( intent )
                                    finish()

                                }
                        }

                    }

                }
                .addOnFailureListener {
                    setDialog(false)
                    Snackbar.make(binding.root, it.message.toString(), Snackbar.LENGTH_LONG).show()
                }

        }else{
            setDialog(false)
        }

    }

    private fun verificarEmail(user: FirebaseUser){
        user.sendEmailVerification().addOnCompleteListener{
            if(it.isSuccessful){
                Snackbar.make(binding.root, "Email enviado", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setDialog(show: Boolean) {
        if (show) dialog.show() else dialog.dismiss()
    }


}