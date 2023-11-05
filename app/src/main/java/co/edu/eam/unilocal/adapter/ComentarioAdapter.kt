package co.edu.eam.unilocal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.databinding.ItemComentarioBinding
import co.edu.eam.unilocal.modelo.Comentario
import co.edu.eam.unilocal.modelo.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class ComentarioAdapter(private var lista:ArrayList<Comentario>): RecyclerView.Adapter<ComentarioAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemComentarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun getItemCount() = lista.size

    inner class ViewHolder(private var view: ItemComentarioBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(comentario: Comentario) {

            Firebase.firestore
                .collection("usuarios")
                .document( comentario.idUsuario )
                .get()
                .addOnSuccessListener {
                    view.txtComentario.text = comentario.texto
                    view.txtUsuario.text = it.toObject(Usuario::class.java)?.nombre

                    val sdf = SimpleDateFormat("dd-MM-YYYY")
                    view.txtFecha.text = sdf.format( comentario.fecha )
                }

        }

    }

}