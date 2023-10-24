package co.edu.eam.unilocal.modelo

class Horario(var diaSemana:ArrayList<DiaSemana>, var horaInicio:Int, var horaCierre:Int) {

    var id:Int = 0

    constructor(id:Int, diaSemana:ArrayList<DiaSemana>, horaInicio:Int, horaCierre:Int):this(diaSemana, horaInicio, horaCierre){
        this.id = id
    }

    override fun toString(): String {
        return "Horario(diaSemana=$diaSemana, horaInicio=$horaInicio, horaCierre=$horaCierre, id=$id)"
    }


}