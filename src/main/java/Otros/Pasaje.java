package Otros;

import Pasivos.PuestoAtencion;

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
    
    public Terminal getTerminal(){
        return this.terminal;
    }
 
    public int getHoraVuelo(){
        return this.horaVuelo;
    }
    
    public int getPuestoEmbarque(){
        return this.puestoEmbarque;
}
    
}