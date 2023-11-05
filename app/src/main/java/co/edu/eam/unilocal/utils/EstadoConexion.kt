package co.edu.eam.unilocal.utils

import android.net.ConnectivityManager
import android.net.Network

class EstadoConexion ( var result: (Boolean) -> Unit) : ConnectivityManager.NetworkCallback(){
    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        result(true)
    }
    override fun onLost(network: Network) {
        super.onLost(network)
        result(false)
    }

    override fun onUnavailable() {
        super.onUnavailable()
        result(false)
    }

}