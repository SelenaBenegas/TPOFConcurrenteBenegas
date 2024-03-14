/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pasivos;

import Otros.Aerolinea;
import Otros.Pasaje;
import Thread.Pasajero;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Selena
 */
public class PuestoAtencion {

    private ArrayBlockingQueue<Pasajero> colaPasajeros;
    private int id;
    // Lock para bloquear a los pasajeros
    private Lock hacerFila = new ReentrantLock();
    private Condition espacioEnFila = hacerFila.newCondition();
    // Semáforo para la comunicación con el guardia
    private Semaphore avisarPasajeros = new Semaphore(0);

    public PuestoAtencion(int id, int capMax) {
        this.id = id;
        this.colaPasajeros = new ArrayBlockingQueue(capMax, true);
    }

    public void hacerFila(Pasajero pasajero) throws InterruptedException {
        this.hacerFila.lock();
        try {
            boolean success = colaPasajeros.offer(pasajero);
            if (!success) {
                System.out.println("La fila está llena, " + pasajero.getNombre() + " espera en el Hall Central.");
            }
            while (!success) {
                // Espera a que el guardia le diga que hay espacio
                this.espacioEnFila.await();
                success = colaPasajeros.offer(pasajero);
            }
        } finally {
            this.hacerFila.unlock();
        }
        System.out.println(pasajero.getNombre() + " está haciendo fila en el puesto de atención " + this.id);
    }

    public void atender() throws InterruptedException {
        Pasajero pasajero = this.colaPasajeros.take();
        System.out.println(Thread.currentThread().getName() + ": Hola! " + pasajero.getNombre() + ", realizaremos el chek-in.");
        Thread.sleep(500);
        Pasaje pasaje = pasajero.getPasaje();
        Terminal terminal = pasaje.getTerminal();
        int puestoE = pasaje.getPuestoEmbarque();
        System.out.println(Thread.currentThread().getName() + ": Listo " + pasajero.getNombre() + ", dirigase al puerto " + puestoE + " de la terminal: " + terminal.getLetra() + ", adios!");
        this.avisarPasajeros.release();
    }

    public void avisarPasajero() throws InterruptedException {
        this.avisarPasajeros.acquire();
        this.hacerFila.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ": Hay espacio en la fila!");
            this.espacioEnFila.signalAll();
        } finally {
            this.hacerFila.unlock();
        }

    }

    public int getId() {
        return this.id;
    }

}
