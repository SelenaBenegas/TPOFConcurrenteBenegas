/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Thread;

import Otros.Aerolinea;
import Otros.Pasaje;
import Pasivos.PuestoAtencion;
import Pasivos.PuestoInforme;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Selena
 */
public class RecepcionistaInforme implements Runnable {

    private PuestoInforme puestoI;

    public RecepcionistaInforme(PuestoInforme puestoI) {
        this.puestoI = puestoI;
    }

    public void run() {
        while (true) {
            try {
                puestoI.atender();
            } catch (InterruptedException ex) {
                Logger.getLogger(RecepcionistaInforme.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
