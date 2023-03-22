package co.edu.eam.unilocal.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.modelo.Lugar
import co.edu.eam.unilocal.actividades.DetalleLugarActivity
import co.edu.eam.unilocal.bd.Categorias
import co.edu.eam.unilocal.databinding.ItemLugarBinding

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

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(lugar: Lugar){
            view.nombreLugar.text = lugar.nombre
            view.direccionLugar.text = lugar.direccion
            view.estadoLugar.text = "Abierto"
            view.horarioLugar.text = "Cierra a las 2:00"
            view.listaCategorias.text = Categorias.obtener(lugar.idCategoria)!!.nombre
            codigoLugar = lugar.id
        }

        override fun onClick(p0: View?) {
            val intent = Intent( view.root.context, DetalleLugarActivity::class.java )
            intent.putExtra("codigo", codigoLugar)
            view.root.context.startActivity(intent)
        }

    }

}