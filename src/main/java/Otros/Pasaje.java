/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Otros;

import Pasivos.PuestoAtencion;
import Pasivos.Terminal;

/**
 *
 * @author Selena
 */
public class Pasaje {
    
    public Aerolinea aerolinea;
    public int horaVuelo;
    private Terminal terminal;
    private int puestoEmbarque;
    private PuestoAtencion puestoA;
    
   
    public Pasaje(Aerolinea aerolinea, int hora, Terminal terminal, int puesto){
        this.aerolinea = aerolinea;
        this.horaVuelo = hora;
        this.terminal = terminal;
        this.puestoEmbarque = puesto;
    }
    
    public void setPuestoAtencion(PuestoAtencion puestoA){
        this.puestoA = puestoA;
    }
    
    public PuestoAtencion getPuestoAtencion(){
        return this.puestoA;
    }
    
    public Aerolinea getAerolinea(){
        return this.aerolinea;
    }
    
    public void setAerolinea(Aerolinea aerolinea){
        this.aerolinea = aerolinea;
    }
    
    public Terminal getTerminal(){
        return this.terminal;
    }
    
    public void setTerminal(Terminal terminal){
        this.terminal = terminal;
    }
    
    public int getHoraVuelo(){
        return this.horaVuelo;
    }
    
    public void setHoraVuelo(int hora){
        this.horaVuelo = hora;
    }
    
    public void setPuestoEmbarque(int puesto){
        this.puestoEmbarque = puesto;
    }
    
    public int getPuestoEmbarque(){
        return this.puestoEmbarque;
}
    
}