/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pasivos;

import Otros.Aerolinea;
import Otros.Pasaje;
import Thread.Pasajero;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Selena
 */
public class PuestoInforme {

    private BlockingQueue<Pasajero> colaPasajeros;

    public PuestoInforme() {
        this.colaPasajeros = new LinkedBlockingQueue<>();
    }

    public void consultar(Pasajero pasajero) {
        try {
            System.out.println(pasajero.getNombre() + " ingresa al puesto de informes.");
            this.colaPasajeros.put(pasajero);
            // Bloquear al hilo del pasajero hasta que sea atendido
            synchronized (pasajero) {
                pasajero.wait();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(PuestoInforme.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void atender() throws InterruptedException {
        Pasajero pasajero = this.colaPasajeros.take();
        Pasaje pasaje = pasajero.getPasaje();
        Aerolinea aerolinea = pasaje.getAerolinea();
        PuestoAtencion puestoA = aerolinea.getPuestoAtencion();
        pasaje.setPuestoAtencion(puestoA);
        Thread.sleep(500);
        System.out.println("\u001B[33m" + Thread.currentThread().getName() + ": Hola! " + pasajero.getNombre()
                + " su puesto de atención es el " + puestoA.getId() + "\u001B[0m");
        // Desbloquear el hilo cuando lo tomo
        synchronized (pasajero) {
            pasajero.notify();
        }
    }
}
