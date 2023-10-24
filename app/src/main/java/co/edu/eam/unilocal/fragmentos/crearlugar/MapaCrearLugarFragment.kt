package co.edu.eam.unilocal.fragmentos.crearlugar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.FragmentMapaCrearLugarBinding
import co.edu.eam.unilocal.modelo.Posicion
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapaCrearLugarFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding:FragmentMapaCrearLugarBinding
    lateinit var gMap:GoogleMap
    private var tienePermiso = false
    private val defaultLocation = LatLng(4.550923, -75.6557201)
    var posicion:Posicion? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapaCrearLugarBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById( R.id.mapa_crear_lugar ) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        gMap.uiSettings.isZoomControlsEnabled = true

        gMap.moveCamera( CameraUpdateFactory.newLatLngZoom( defaultLocation, 12f ) )

        gMap.setOnMapClickListener {

            if(posicion==null){
                posicion = Posicion()
            }

            posicion!!.lat = it.latitude
            posicion!!.lng = it.longitude

            gMap.clear()
            gMap.addMarker( MarkerOptions().position(it) )
        }

    }

    companion object{
        fun newInstance():MapaCrearLugarFragment{
            val args = Bundle()
            //args.putInt("id_usuario", codigoUsuario)

            val fragmento = MapaCrearLugarFragment()
            fragmento.arguments = args
            return fragmento
        }
    }

}