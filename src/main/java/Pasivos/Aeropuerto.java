/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pasivos;

import Thread.RecepcionistaInforme;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Selena
 */
public class Aeropuerto {

    private int cantAerolineas;
    private int capPuestosAtencion;
    private int capFreeShop;
    private int capTren;
    private PuestoInforme puestoI;
    private int horaActual = 400;
    private int diaActual = 1;
    private boolean atendiendo = false; // Bandera para indicar si el aeropuerto se encuentra o no en horario de atencion
    private Lock ingresarAeropuerto = new ReentrantLock();
    private Condition esperarPorLaHora = ingresarAeropuerto.newCondition();

    public Aeropuerto(PuestoInforme puestoI, int cantAerolineas, int capPuestosAtencion, int capFreeShop, int capTren) {
        this.cantAerolineas = cantAerolineas;
        this.capPuestosAtencion = capPuestosAtencion;
        this.capFreeShop = capFreeShop;
        this.capTren = capTren;
        this.puestoI = puestoI;
        // this.arrayPuestos = new PuestoAtencion[cantAerolineas]; // Hay un puesto de atención por cada aerolínea
        //TODO crear las terminales
    }

    public synchronized void actualizarHorario(int pasoTiempo) {
        if (this.horaActual == 2300) {
            this.diaActual++;
            this.horaActual = 0;
            System.out.println("Dia: " + this.diaActual + " - Hora: 00:00 hs");
        } else {
            this.horaActual += pasoTiempo;
            System.out.println("Dia: " + this.diaActual + " - Hora: " + this.horaActual / 100 + ":00 hs");
            switch (this.horaActual) {
                // El aeropuerto tiene que abrir a las 6
                case 600:
                    System.out.println("\u001B[32m" +"\"VIAJE BONITO\" COMIENZA SU HORARIO DE ATENCIÓN." + "\u001B[0m");
                    this.ingresarAeropuerto.lock();
                    atendiendo = true;
                    try {
                        this.esperarPorLaHora.signalAll();
                    } finally {
                        this.ingresarAeropuerto.unlock();
                    }
                    break;
                // El aeropuerto cierra a las 22
                case 2200:
                    System.out.println("\u001B[31m" +"\"VIAJE BONITO\" FINALIZA SU HORARIO DE ATENCIÓN."+ "\u001B[0m");
                    atendiendo = false;
                    break;
                default:
                    break;
            }
        }
        this.notifyAll();
    }

    public void ingresarAeropuerto() {
        this.ingresarAeropuerto.lock();
        try {
            while (!atendiendo) {
                System.out.println(Thread.currentThread().getName() + " no pudo ingresar al aeropuerto porque esta cerrado");
                try {
                    this.esperarPorLaHora.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(RecepcionistaInforme.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            this.ingresarAeropuerto.unlock();
        }
        System.out.println(Thread.currentThread().getName() + " ingresa al aeropuerto.");
    }

    public PuestoInforme getPuestoInforme() {
        return this.puestoI;
    }
}
