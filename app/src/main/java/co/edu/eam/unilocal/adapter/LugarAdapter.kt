package co.edu.eam.unilocal.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.actividades.DetalleLugarActivity
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.bd.Comentarios
import co.edu.eam.unilocal.databinding.ItemLugarBinding
import co.edu.eam.unilocal.modelo.EstadoLugar
import co.edu.eam.unilocal.modelo.Lugar


class LugarAdapter(private var lista:ArrayList<Lugar>): RecyclerView.Adapter<LugarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ItemLugarBinding.inflate( LayoutInflater.from(parent.context), parent, false )
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind( lista[position] )
    }

    override fun getItemCount() = lista.size

    inner class ViewHolder(private var view:ItemLugarBinding):RecyclerView.ViewHolder(view.root), View.OnClickListener{

        private var codigoLugar:Int = 0
        private lateinit var estadoLugar:EstadoLugar

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(lugar: Lugar){

            estadoLugar = lugar.estado

            if(estadoLugar != EstadoLugar.ACEPTADO){
                view.nombreLugar.text = "${lugar.nombre} - [$estadoLugar]"
            }else{
                view.nombreLugar.text = lugar.nombre
            }

            view.direccionLugar.text = lugar.direccion

            val estaAbierto = lugar.estaAbierto()

            if(estaAbierto){
                view.estadoLugar.setTextColor( ContextCompat.getColor(itemView.context, R.color.green ) )
                view.horarioLugar.text = "Cierra a las ${lugar.obtenerHoraCierre()}"
            }else{
                view.estadoLugar.setTextColor( ContextCompat.getColor(itemView.context, R.color.red ) )
                view.horarioLugar.text = "Abre el ${lugar.obtenerHoraApertura()}"
            }

            val calificacion = lugar.obtenerCalificacionPromedio( Comentarios.listar(lugar.id) )

            for( i in 0..calificacion ){
                (view.listaEstrellas[i] as TextView).setTextColor( ContextCompat.getColor(view.listaEstrellas.context, R.color.yellow) )
            }

            view.estadoLugar.text = if(estaAbierto){ view.estadoLugar.context.getString(R.string.abierto) }else{ view.estadoLugar.context.getString(R.string.cerrado) }
            view.iconoLugar.text = Categorias.obtener(lugar.idCategoria)!!.icono
            codigoLugar = lugar.id
        }

        override fun onClick(p0: View?) {
            if(estadoLugar == EstadoLugar.ACEPTADO) {
                val intent = Intent(view.root.context, DetalleLugarActivity::class.java)
                intent.putExtra("codigo", codigoLugar)
                view.root.context.startActivity(intent)
            }
        }

    }

}