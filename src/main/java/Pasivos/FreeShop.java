/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pasivos;

import Otros.Producto;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Selena
 */
class FreeShop {
    //constante que permite definir el stock de productos
    public static final int CANT_STOCK_PRODUCTO = 20;

    private int capacidad;
    private Caja[] cajas;
    private Producto[] productos;
    private Semaphore entrarFree;
    private Random nroRam;
    private String nombre;

    public FreeShop(int capacidad, Caja[] cajas, String nombreTienda) {
        this.capacidad = capacidad;
        this.cajas = cajas;
        this.entrarFree = new Semaphore(capacidad, true);// existe orden de espera
        this.productos = new Producto[CANT_STOCK_PRODUCTO];
        generarProductos();
        nroRam = new Random();
        this.nombre = nombreTienda;

    }
    
    private void generarProductos() {
        for (int i = 0; i < CANT_STOCK_PRODUCTO; i++) {
            productos[i] = new Producto(i);

        }
    }
}