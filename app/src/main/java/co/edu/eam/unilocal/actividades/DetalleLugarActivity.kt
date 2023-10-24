package co.edu.eam.unilocal.actividades

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.adapter.ViewPagerAdapter
import co.edu.eam.unilocal.databinding.ActivityDetalleLugarBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetalleLugarActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetalleLugarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleLugarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sh = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val codigoUsuario = sh.getInt("codigo_usuario", 0)

        val codigoLugar = intent.extras!!.getInt("codigo")

        if(codigoLugar != 0) {

            binding.viewPager.adapter = ViewPagerAdapter(this, codigoLugar, codigoUsuario)
            TabLayoutMediator(binding.tabs, binding.viewPager) { tab, pos ->
                when (pos) {
                    0 -> tab.text = getString(R.string.info_lugar)
                    1 -> tab.text = getString(R.string.comentarios)
                }
            }.attach()

        }

    }
}