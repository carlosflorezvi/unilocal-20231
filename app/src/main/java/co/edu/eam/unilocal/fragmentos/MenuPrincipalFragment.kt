package co.edu.eam.unilocal.fragmentos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.actividades.ResultadoBusquedaActivity
import co.edu.eam.unilocal.databinding.FragmentMenuPrincipalBinding

class MenuPrincipalFragment : Fragment() {

    lateinit var binding:FragmentMenuPrincipalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuPrincipalBinding.inflate(inflater, container, false)

        binding.textoBusqueda.setOnEditorActionListener { textView, i, keyEvent ->
            if( i == EditorInfo.IME_ACTION_SEARCH){

                val busqueda = binding.textoBusqueda.text.toString()

                if(busqueda.isNotEmpty()) {
                    val intent = Intent(activity, ResultadoBusquedaActivity::class.java)
                    intent.putExtra("texto", busqueda)
                    startActivity(intent)
                }

            }
            true
        }

        return binding.root
    }

}