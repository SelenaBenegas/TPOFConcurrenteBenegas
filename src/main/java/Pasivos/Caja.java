package Pasivos;

import Thread.Pasajero;
import java.util.concurrent.Semaphore;

import Otros.Random;

/**
 *
 * @author Selena
 */
public class Caja {
    private int id;
    private Semaphore semCaja;
    private Semaphore semAtender;
    private Semaphore semEsAtendido;
    private Pasajero clienteActual;

    public Caja(int id) {
        this.id = id;
        this.semCaja = new Semaphore(1, true);
        this.semAtender = new Semaphore(0);
        this.semEsAtendido = new Semaphore(0);
        this.clienteActual = null;
    }

    public void entrarCaja(Pasajero pasajero) throws InterruptedException {

        semCaja.acquire();
        semAtender.release();
        this.clienteActual = pasajero;
        System.out.println(
                "\u001B[35m" + "El " + pasajero.getNombre() + " entra en la caja : " + this.id + "\u001B[0m");
        semEsAtendido.acquire();
    }

    public void salirCaja(Pasajero pasajero) {
        System.out.println(
                "\u001B[35m" + "El " + pasajero.getNombre() + " paga y sale de la caja: " + this.id + "\u001B[0m");
        this.clienteActual = null;
        semCaja.release();
    }

    public void atender() throws InterruptedException {
        Random random = new Random();
        semAtender.acquire();
        System.out.println("\u001B[35m" + Thread.currentThread().getName() + ": Hola! " + this.clienteActual.getNombre()
                + "\u001B[0m");
        Thread.sleep(random.generarIntRango(1000, 5000));
        System.out.println("\u001B[35m" + Thread.currentThread().getName() + ": El total es $" + random.generarIntRango(100, 50000)
        + "\u001B[0m");
        semEsAtendido.release();
    }

    /** Getter y Setters **/
    public int getId() {
        return id;
    }

    public Pasajero getClienteActual() {
        return clienteActual;
    }
}
