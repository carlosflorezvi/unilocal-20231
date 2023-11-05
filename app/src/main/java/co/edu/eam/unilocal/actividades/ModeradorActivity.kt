package co.edu.eam.unilocal.actividades

import android.content.Context
import android.os.Bundle
import android.widget.RelativeLayout.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import co.edu.eam.unilocal.adapter.ListasAdapter
import co.edu.eam.unilocal.databinding.ActivityModeradorBinding
import co.edu.eam.unilocal.modelo.EstadoLugar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class ModeradorActivity : AppCompatActivity() {

    lateinit var binding:ActivityModeradorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityModeradorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val params = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, getStatusBarHeight(), 0, 0)
        binding.tabs.layoutParams = params

        var user = FirebaseAuth.getInstance().currentUser

        if(user!=null) {

            binding.viewPager.adapter = ListasAdapter(this)
            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, pos ->
                when (pos) {
                    0 -> tab.text = EstadoLugar.SIN_REVISAR.name
                    1 -> tab.text = EstadoLugar.RECHAZADO.name
                    2 -> tab.text = EstadoLugar.ACEPTADO.name
                }
            }.attach()

        }

    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }


}