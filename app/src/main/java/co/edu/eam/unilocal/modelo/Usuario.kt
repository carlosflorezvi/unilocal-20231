package co.edu.eam.unilocal.modelo

class Usuario(var id:Int, var nombre:String, var nickname:String, var correo:String, var password:String) {


    override fun toString(): String {
        return "Usuario(id=$id, nombre='$nombre', nickname='$nickname', correo='$correo', password='$password')"
    }
}