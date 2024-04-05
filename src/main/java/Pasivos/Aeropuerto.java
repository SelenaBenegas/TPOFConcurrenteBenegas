
package Pasivos;

import Thread.RecepcionistaInforme;
import java.util.concurrent.Semaphore;
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

    private Tren tren;
    private PuestoInforme puestoI;
    private int horaActual = 400;
    private Semaphore mutexReloj = new Semaphore(1);
    private boolean atendiendo = false; // Bandera para indicar si el aeropuerto se encuentra o no en horario de
                                        // atencion
    private Lock ingresarAeropuerto = new ReentrantLock();
    private Lock tomarVuelo = new ReentrantLock();
    private Condition esperaIngresar = ingresarAeropuerto.newCondition();
    private Condition esperaVuelo = tomarVuelo.newCondition();

    public Aeropuerto(PuestoInforme puestoI, Tren tren) {
        this.puestoI = puestoI;
        this.tren = tren;
    }

    public synchronized void actualizarHorario(int pasoTiempo) {
        try {
            mutexReloj.acquire();
            if (this.horaActual == 2300) {
                this.horaActual = 0;
                System.out.println("\u001B[1m" + "Hora: 00:00 hs" + "\u001B[0m");
            } else {
                this.horaActual += pasoTiempo;
                System.out.println("\u001B[1m" + "Hora: " + this.horaActual / 100 + ":00 hs" + "\u001B[0m");
                switch (this.horaActual) {
                    // El aeropuerto tiene que abrir a las 6
                    case 600:
                        System.out.println(
                                "\u001B[32m" + "\"VIAJE BONITO\" COMIENZA SU HORARIO DE ATENCIÓN." + "\u001B[0m");
                        this.ingresarAeropuerto.lock();
                        atendiendo = true;
                        try {
                            this.esperaIngresar.signalAll();
                        } finally {
                            this.ingresarAeropuerto.unlock();
                        }
                        break;
                    // El aeropuerto cierra a las 22
                    case 2200:
                        System.out.println(
                                "\u001B[31m" + "\"VIAJE BONITO\" FINALIZA SU HORARIO DE ATENCIÓN." + "\u001B[0m");
                        atendiendo = false;
                        break;
                    default:
                        break;
                }
            }
            this.tomarVuelo.lock();
            try {
                this.esperaVuelo.signalAll();
            } finally {
                this.tomarVuelo.unlock();
            }
            mutexReloj.release();
            this.notifyAll();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void ingresarAeropuerto() {
        this.ingresarAeropuerto.lock();
        try {
            while (!atendiendo) {
                System.out.println(
                        Thread.currentThread().getName() + " no pudo ingresar al aeropuerto porque esta cerrado");
                try {
                    this.esperaIngresar.await();
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

    public Tren getTren() {
        return this.tren;
    }

    public int getHoraActual() {
        int hora = 0;
        try {
            mutexReloj.acquire();
            hora = this.horaActual;
            mutexReloj.release();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return hora;
    }

    public int calcularHora(int horaActual, int cant) {
        int hora = horaActual + cant * 100;
        hora = hora % 2400;
        if (hora == 0) {
            hora = 2400;
        }
        return hora;
    }

    public void tomarVuelo(int horaVuelo) throws InterruptedException {
        this.tomarVuelo.lock();

        try {
            if (horaActual != horaVuelo) {

                System.out.println(Thread.currentThread().getName() + " esperando su vuelo a las " + horaVuelo/100  + ":00 hs... y son las " + horaActual/100  + ":00 hs ...");
            }
            while (horaActual != horaVuelo) {
                this.esperaVuelo.await();
            }
        } finally {
            this.tomarVuelo.unlock();
        }
        System.out.println("\u001B[32m" + "\u001B[1m" + Thread.currentThread().getName() + " sale su vuelo de las " + horaVuelo/100  + ":00 hs... y son las " + horaActual/100  + ":00 hs... ARRIVEDERCI!" + "\u001B[0m"+ "\u001B[0m");

    }

}
