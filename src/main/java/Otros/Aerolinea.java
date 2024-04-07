package Otros;

import Pasivos.PuestoAtencion;

/**
 *
 * @author Selena
 */
public class Aerolinea {
    private PuestoAtencion puestoAtencion;
    private String nombreAerolinea;
    
    /**Constructor
     * 
     * @param nombreAerolinea
     * @param puesto 
     */
    public Aerolinea(String nombreAerolinea, PuestoAtencion puesto){
        this.nombreAerolinea = nombreAerolinea;
        this.puestoAtencion = puesto;
    }

    /*
    * Getters y Setters
    */
    public PuestoAtencion getPuestoAtencion() {
        return this.puestoAtencion;
    }

    public String getNombre(){
        return this.nombreAerolinea;
    }
    
    
}
