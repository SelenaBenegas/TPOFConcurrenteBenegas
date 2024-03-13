/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Thread;

import Pasivos.PuestoAtencion;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Selena
 */
public class Guardia implements Runnable {
    private PuestoAtencion puestoA;

    public Guardia(PuestoAtencion puestoA) {
        this.puestoA = puestoA;
    }

    public void run() {
        while (true) {
            try {
                this.puestoA.avisarPasajero();
            } catch (InterruptedException ex) {
                Logger.getLogger(RecepcionistaAtencion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
