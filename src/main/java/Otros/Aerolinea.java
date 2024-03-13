/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Otros;

import Pasivos.PuestoAtencion;
import java.util.Objects;

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

    public void setPuestoAtencion(PuestoAtencion puestoAtencion) {
        this.puestoAtencion = puestoAtencion;
    }

    public String getNombreAerolinea() {
        return this.nombreAerolinea;
    }

    public void setNombreAerolinea(String nombreAerolinea) {
        this.nombreAerolinea = nombreAerolinea;
    }
    
    /*
    *   Metodos redefinidos, equals y toString.
    */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Aerolinea other = (Aerolinea) obj;
        if (!Objects.equals(this.nombreAerolinea, other.nombreAerolinea)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return this.nombreAerolinea;
    }
    
    
}
