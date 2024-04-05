
package Pasivos;

import Thread.Pasajero;

import java.util.concurrent.Semaphore;

import Otros.Random;

/**
 *
 * @author Selena
 */
public class FreeShop {
    //constante que permite definir el stock de productos
    public static final int CANT_STOCK_PRODUCTO = 20;

    private Caja[] cajas;
    private Semaphore entrarFree;
    private Random nroRam;
    private char nombre;
    
    public FreeShop(int capacidad, Caja[] cajas, char nombreTienda) {
        this.cajas = cajas;
        this.entrarFree = new Semaphore(capacidad, true);// existe orden de espera
        nroRam = new Random();
        this.nombre = nombreTienda;

    }

    public Caja obtenerCaja() {
        return cajas[nroRam.generarInt(cajas.length)];
    }

    public void entrarFreeShop(Pasajero cliente) throws InterruptedException {
        System.out.println(cliente.getNombre() + " esta esperando para entrar al freeshop" + this.nombre);
        entrarFree.acquire();
    }

    public void salirFreeShop(Pasajero cliente) {
        System.out.println(cliente.getNombre() + " esta saliendo del freeshop" + this.nombre);
        entrarFree.release();
    }

    public Caja obtenerCaja(Pasajero pasajero) {
        int cantCajas = this.cajas.length;
        int cajaRandom = this.nroRam.generarInt(cantCajas);
        Caja caja = this.cajas[cajaRandom];
        System.out.println(pasajero.getNombre() + " se dirige a la caja: " + caja.getId());
        return caja;
    }
}