package co.edu.eam.unilocal.modelo

class Horario() {

    var id:Int = 0
    var diaSemana:ArrayList<DiaSemana> = ArrayList()
    var horaInicio:Int = 0
    var horaCierre:Int = 0

    constructor(diaSemana:ArrayList<DiaSemana>, horaInicio:Int, horaCierre:Int  ):this(){
        this.diaSemana = diaSemana
        this.horaCierre = horaCierre
        this.horaInicio = horaInicio
    }

    constructor(id:Int, diaSemana:ArrayList<DiaSemana>, horaInicio:Int, horaCierre:Int):this(diaSemana, horaInicio, horaCierre){
        this.id = id
    }

    override fun toString(): String {
        return "Horario(diaSemana=$diaSemana, horaInicio=$horaInicio, horaCierre=$horaCierre, id=$id)"
    }


}