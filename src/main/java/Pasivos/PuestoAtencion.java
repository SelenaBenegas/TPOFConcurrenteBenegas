
package Pasivos;

import Otros.Pasaje;
import Otros.Terminal;
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
    // Lock para bloquear a los pasajeros que quieren hacer fila
    private Lock hacerFila = new ReentrantLock();
    private Condition espacioEnFila = hacerFila.newCondition();
    // Semáforo para la comunicación con el guardia
    private Semaphore avisarPasajeros = new Semaphore(0);

    public PuestoAtencion(int id, int capMax) {
        this.id = id;
        this.colaPasajeros = new ArrayBlockingQueue<Pasajero>(capMax, true);
    }

    public void hacerFila(Pasajero pasajero) throws InterruptedException {
        this.hacerFila.lock();
        try {
            boolean lugarFila = colaPasajeros.offer(pasajero);
            if (!lugarFila) {
                System.out.println("La fila está llena, " + pasajero.getNombre() + " espera en el Hall Central.");
            }
            while (!lugarFila) {
                // Espera a que el guardia le diga que hay espacio
                this.espacioEnFila.await();
                lugarFila = colaPasajeros.offer(pasajero);
            }
            System.out.println(pasajero.getNombre() + " está haciendo fila en el puesto de atención " + this.id);
            // Bloquear al hilo del pasajero hasta que sea atendido
            synchronized (pasajero) {
                pasajero.wait();
            }
        } finally {
            this.hacerFila.unlock();
        }
       
    }

    public void atender() throws InterruptedException {
        Pasajero pasajero = this.colaPasajeros.take();
        System.out.println("\u001B[34m" + Thread.currentThread().getName() + ": Hola! " + pasajero.getNombre() + ", realizaremos el chek-in."+ "\u001B[0m");
        Thread.sleep(2000);
        Pasaje pasaje = pasajero.getPasaje();
        String aerolinea = pasaje.getAerolinea().getNombre();
        Terminal terminal = pasaje.getTerminal();
        int puestoE = pasaje.getPuestoEmbarque();
        System.out.println("\u001B[34m" + Thread.currentThread().getName() + ": Listo " + pasajero.getNombre() + ", dirigase al puerto " + puestoE + " de la terminal: " + terminal.getLetra() + ", gracias por elegir a " + aerolinea + "! \u001B[0m");
        // //Desbloquear el hilo cuando lo termina de atenderlo
        synchronized (pasajero) {
            pasajero.notify();
        }
        // Le avisa al guardia que avise a los pasajeros que hay lugar en la fila
        this.avisarPasajeros.release();
    }

    public void avisarPasajero() throws InterruptedException {
        this.avisarPasajeros.acquire();
        this.hacerFila.lock();
        try {
            System.out.println("\u001B[34m" + Thread.currentThread().getName() + ": Hay espacio en la fila!"+ "\u001B[0m");
            this.espacioEnFila.signalAll();
        } finally {
            this.hacerFila.unlock();
        }

    }

    public int getId() {
        return this.id;
    }

}
