package co.edu.eam.unilocal.actividades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.edu.eam.unilocal.databinding.ActivityAdministradorBinding

class AdministradorActivity : AppCompatActivity() {

    lateinit var binding:ActivityAdministradorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdministradorBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}