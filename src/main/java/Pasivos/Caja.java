/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pasivos;

import Otros.Producto;
import Thread.Pasajero;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Selena
 */
public class Caja {
    private int cantActual, id;
    private Semaphore semCaja;//semaforo que permite el ingreso de a una persona a la caja
    private final Lock lock;
    private final Condition esperaCajera;
    private final Condition esperaPasajero;
    private final ArrayList<Producto> cintaTransportadora;
    private Pasajero clienteActual;

    
    /**Constructor
     * 
     * @param id 
     */
    public Caja(int id) {
        this.id = id;
        this.cantActual = 0;
        this.semCaja = new Semaphore(1, true);
        this.lock = new ReentrantLock();
        this.esperaCajera = this.lock.newCondition();
        this.esperaPasajero = this.lock.newCondition();
        this.cintaTransportadora = new ArrayList<>();
        clienteActual = null;
    }
}
