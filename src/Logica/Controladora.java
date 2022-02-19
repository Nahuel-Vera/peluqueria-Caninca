package Logica;

import Persistencia.ControladoraPersistencia;

public class Controladora {
    ControladoraPersistencia controlPersis=new ControladoraPersistencia();
    
    public void altaProducto(int num_cliente, String nombre_perro, String raza, String color, String alergico, String atencion_especial, String nombre_duenio, int celular_duenio, String observaciones){
        MascotaCli mascota=new MascotaCli();
        mascota.setAlergico(alergico);
        mascota.setAtencion_especial(atencion_especial);
        mascota.setCelular_duenio(celular_duenio);
        mascota.setColor(color);
        mascota.setNombre_duenio(nombre_duenio);
        mascota.setNombre_perro(nombre_perro);
        mascota.setNum_cliente(num_cliente);
        mascota.setObservaciones(observaciones);
        mascota.setRaza(raza);
        
        controlPersis.altaMascota(mascota);
    }
    
}
