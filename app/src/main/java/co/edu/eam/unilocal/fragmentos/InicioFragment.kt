package co.edu.eam.unilocal.fragmentos

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.actividades.DetalleLugarActivity
import co.edu.eam.unilocal.actividades.MainActivity
import co.edu.eam.unilocal.databinding.FragmentInicioBinding
import co.edu.eam.unilocal.modelo.EstadoLugar
import co.edu.eam.unilocal.modelo.Lugar
import co.edu.eam.unilocal.sqlite.UnilocalDbHelper
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InicioFragment : Fragment(), OnMapReadyCallback, OnInfoWindowClickListener {

    lateinit var bd:UnilocalDbHelper
    lateinit var binding: FragmentInicioBinding
    lateinit var gMap:GoogleMap
    private var tienePermiso = false
    private val defaultLocation = LatLng(4.550923, -75.6557201)
    private lateinit var permissionsResultCallback:ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //bd = UnilocalDbHelper(requireContext())

        permissionsResultCallback = registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
            when (it) {
                true -> {
                    tienePermiso = true
                    println("Permiso aceptado")
                }
                false -> { println("Permiso denegado") }
            }
        }

        getLocationPermission()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInicioBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById( R.id.mapa_principal ) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        gMap.uiSettings.isZoomControlsEnabled = true

        if(tienePermiso){
            gMap.isMyLocationEnabled = true
        }

        val estado = (requireActivity() as MainActivity).estadoConexion

        if(estado) {

            Firebase.firestore
                .collection("lugares")
                .whereEqualTo("estado", EstadoLugar.ACEPTADO)
                .get()
                .addOnSuccessListener {

                    for( doc in it ){
                        var lugar = doc.toObject(Lugar::class.java)
                        lugar.key = doc.id

                        gMap.addMarker(
                            MarkerOptions().position(LatLng(lugar.posicion.lat, lugar.posicion.lng))
                                .title(lugar.nombre)
                        )!!.tag = lugar.key

                        //bd.guardarLugar(lugar)

                    }

                }
                .addOnFailureListener {
                    Log.e("LISTA_LUGARES", it.message.toString())
                }

        }else{
            bd.listarLugares().forEach {
                gMap.addMarker(
                    MarkerOptions().position(LatLng(it.posicion.lat, it.posicion.lng))
                        .title(it.nombre)
                )!!.tag = it.key
            }
        }

        gMap.setOnInfoWindowClickListener(this)

        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12F))

    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            tienePermiso = true
        } else {
            permissionsResultCallback.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onInfoWindowClick(p0: Marker) {
        val intent = Intent(requireContext(), DetalleLugarActivity::class.java)
        intent.putExtra("codigo", p0.tag.toString())
        requireContext().startActivity(intent)
    }

    /*private fun obtenerUbicacion() {
        try {
            if (tienePermiso) {
                val ubicacionActual = LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation
                ubicacionActual.addOnCompleteListener(requireActivity()) {
                    if (it.isSuccessful) {
                        val ubicacion = it.result
                        if (ubicacion != null) {
                            val latlng = LatLng(ubicacion.latitude, ubicacion.longitude)
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15F))

                        }
                    } else {
                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15F))
                        gMap.uiSettings.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }*/




}