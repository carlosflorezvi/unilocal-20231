package co.edu.eam.unilocal.fragmentos.moderador

import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.LugarAdapter
import co.edu.eam.unilocal.databinding.FragmentListaLugaresBinding
import co.edu.eam.unilocal.modelo.EstadoLugar
import co.edu.eam.unilocal.modelo.Lugar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class ListaLugaresFragment : Fragment() {

    private var estadoLugar: Int = 0
    lateinit var binding:FragmentListaLugaresBinding
    lateinit var listaLugares:ArrayList<Lugar>
    lateinit var adapterLista:LugarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            estadoLugar = it.getInt("estado")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListaLugaresBinding.inflate( inflater, container, false )

        listaLugares = ArrayList()
        adapterLista = LugarAdapter( listaLugares )
        binding.listaLugares.adapter = adapterLista
        binding.listaLugares.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        Firebase.firestore
            .collection("lugares")
            .whereEqualTo("estado", EstadoLugar.values()[estadoLugar])
            .get()
            .addOnSuccessListener {
                for(doc in it){
                    val lugar = doc.toObject(Lugar::class.java)
                    lugar.key = doc.id
                    listaLugares.add( lugar )
                    adapterLista.notifyItemInserted( listaLugares.size-1 )
                }
            }
            .addOnFailureListener {
                Log.e("LISTA", "${it.message}")
            }

        crearEventoSwipe()

        return binding.root
    }

    fun crearEventoSwipe(){

        val simpleCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var pos = viewHolder.adapterPosition
                val codigoLugar = listaLugares[pos].key
                val lugar = Lugar() //Lugares.obtener(codigoLugar)

                when(direction){

                    ItemTouchHelper.LEFT -> {

                        //Lugares.cambiarEstado( codigoLugar, EstadoLugar.ACEPTADO )
                        listaLugares.remove(lugar)
                        adapterLista.notifyItemRemoved(pos)

                        Snackbar.make(binding.listaLugares, getString(R.string.lugar_aceptado), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.deshacer), View.OnClickListener {
                                //Lugares.cambiarEstado( codigoLugar, EstadoLugar.SIN_REVISAR )
                                listaLugares.add(pos, lugar!!)
                                adapterLista.notifyItemInserted(pos)
                            }).show()
                    }
                    ItemTouchHelper.RIGHT -> {

                        //Lugares.cambiarEstado( codigoLugar, EstadoLugar.RECHAZADO )
                        listaLugares.remove(lugar)
                        adapterLista.notifyItemRemoved(pos)

                        Snackbar.make(binding.listaLugares, getString(R.string.lugar_rechazado), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.deshacer), View.OnClickListener {
                                //Lugares.cambiarEstado( codigoLugar, EstadoLugar.SIN_REVISAR )
                                listaLugares.add(pos, lugar!!)
                                adapterLista.notifyItemInserted(pos)
                            }).show()
                    }

                }

            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor( ContextCompat.getColor(requireContext(), R.color.green) )
                    .addSwipeRightBackgroundColor( ContextCompat.getColor(requireContext(), R.color.red) )
                    .addSwipeLeftLabel(getString(R.string.aceptar))
                    .addSwipeRightLabel(getString(R.string.rechazar))
                    .create()
                    .decorate()


            }

        }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.listaLugares)

    }

    companion object {

        @JvmStatic
        fun newInstance(estado: EstadoLugar) =
            ListaLugaresFragment().apply {
                arguments = Bundle().apply {
                    putInt("estado", estado.ordinal)
                }
            }
    }
}