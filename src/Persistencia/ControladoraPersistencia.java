package Persistencia;

import Logica.MascotaCli;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladoraPersistencia {
    MascotaCliJpaController jpaMascota=new MascotaCliJpaController();
    
    public void altaMascota(MascotaCli mascota){
        try {
            jpaMascota.create(mascota);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
