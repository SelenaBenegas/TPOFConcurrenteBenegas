/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pasivos;

import Pasivos.FreeShop;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Selena
 */
public class Terminal {

    private int[] puestosEmbarque;
    // private FreeShop tienda;
    private char letraTerminal;
    private int numeroTerminal;
    // private final AtomicInteger hora;

    public Terminal(char letra, int numeroTerminal, int[] puestos) {
        this.letraTerminal = letra;
        this.puestosEmbarque = puestos;
        this.numeroTerminal = numeroTerminal;
    }

    public int[] getPuestos() {
        return this.puestosEmbarque;
    }

    public char getLetra() {
        return this.letraTerminal;
    }

    public int getNumeroTerminal() {
        return this.numeroTerminal;
    }
}
