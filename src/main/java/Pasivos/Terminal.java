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
    //private final AtomicInteger hora;
    
    public Terminal(char letra, int[] puestos){
        this.letraTerminal = letra;
        this.puestosEmbarque = puestos;
    }
    
    public int[] getPuestos(){
        return this.puestosEmbarque;
    }
    }