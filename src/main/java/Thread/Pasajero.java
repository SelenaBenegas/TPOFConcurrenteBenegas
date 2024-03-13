/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Thread;

import Otros.Pasaje;
import Pasivos.Aeropuerto;
import Pasivos.PuestoAtencion;
import Pasivos.PuestoInforme;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Selena
 */
public class Pasajero implements Runnable {

    private Aeropuerto aeropuerto;
    private Pasaje pasaje;
    private String nombre;

    public Pasajero(String nombre, Aeropuerto aeropuerto, Pasaje pasaje) {
        this.nombre = nombre;
        this.aeropuerto = aeropuerto;
        this.pasaje = pasaje;
    }

    public void run() {
        try {
            //INGRESA AL AEROPUERTO SÓLO EN EL HORARIO DE ATENCIÓN
            this.aeropuerto.ingresarAeropuerto();
            
            // CUANDO INGRESA AL AEROPUERTO SE DIRIGE AL PUESTO DE INFORMES
            PuestoInforme puestoInformes = aeropuerto.getPuestoInforme();
            puestoInformes.consultar(this);
            // EN EL PUESTO DE INFORMES LE DAN EL PUESTO DE ATENCION
            PuestoAtencion puestoA = pasaje.getPuestoAtencion();
            // Espera en el puesto de atencion
            puestoA.hacerFila(this);
            
            
        } catch (InterruptedException ex) {
            Logger.getLogger(RecepcionistaInforme.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Pasaje getPasaje() {
        return this.pasaje;
    }
    public String getNombre() {
        return this.nombre;
    }

}
