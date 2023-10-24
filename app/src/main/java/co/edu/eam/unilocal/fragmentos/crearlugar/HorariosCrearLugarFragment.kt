package co.edu.eam.unilocal.fragmentos.crearlugar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import co.edu.eam.unilocal.R
import co.edu.eam.unilocal.databinding.FragmentHorariosCrearLugarBinding
import co.edu.eam.unilocal.modelo.Horario

class HorariosCrearLugarFragment : Fragment(), HorariosDialogoFragment.OnHorarioCreadoListener {

    lateinit var binding:FragmentHorariosCrearLugarBinding
    var horarios:ArrayList<Horario> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHorariosCrearLugarBinding.inflate(inflater, container, false)
        binding.btnAsignarHorario.setOnClickListener { mostrarDialogo() }
        return binding.root
    }

    fun mostrarDialogo(){

        val dialog = HorariosDialogoFragment()
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogoTitulo)
        dialog.listener = this
        dialog.show(childFragmentManager, "Agregar")

    }

    override fun elegirHorario(horario: Horario) {

        horario.diaSemana.forEach{
            val textView = TextView(requireContext())
            textView.text = "${it.name.lowercase().replaceFirstChar { c -> c.uppercase() }}  ${horario.horaInicio}:00 - ${horario.horaCierre}:00 "
            binding.listaHorarios.addView(textView)
        }

        horarios.add(horario)
    }

    companion object{

        fun newInstance():HorariosCrearLugarFragment{
            val args = Bundle()
            //args.putInt("id_usuario", codigoUsuario)

            val fragmento = HorariosCrearLugarFragment()
            fragmento.arguments = args
            return fragmento
        }

    }

}