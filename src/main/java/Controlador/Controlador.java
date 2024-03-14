/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Otros.Aerolinea;
import Otros.Pasaje;
import Pasivos.Aeropuerto;
import Thread.Pasajero;
import Thread.Reloj;
import Otros.Random;
import Pasivos.PuestoAtencion;
import Pasivos.PuestoInforme;
import Pasivos.Terminal;
import Thread.Guardia;
import Thread.RecepcionistaAtencion;
import Thread.RecepcionistaInforme;

/**
 *
 * @author Selena
 */
public class Controlador {

    //Cantidad de aerolineas
    private static final int CANT_AEROLINEAS = 3; //En el enunciado sólo dice n
    //Cantidad de terminales (NOTA: Son letras, por lo tanto, el máximo es 26)
    private static final int CANT_TERMINALES = 3; //En el enunciado son 3
    //Cantidad de puestos de embarque por terminal
    private static final int CANT_PUESTOS_EMBARQUE = 7;
    //En el tp no dice capacidad de cada puesto de atencion así que se hace con un random entre el maximo y minimo
    //Capacidad máxima de los puestos de atención
    private static final int CAP_PUESTOS_ATENCION = 4;
    //Capacidad mínima de los puestos de atención
    private static final int MIN_CAP_PUESTOS_ATENCION = 2;
    //Cantidad de recepcionistas en el puesto de informes
    private static final int CANT_RECEPCIONISTASINFORME = 3;
    //Capacidad máxima del freeshop
    private static final int CAP_FREESHOP = 5;
    //Capacidad minima del freeshop
    private static final int MIN_CAP_FREESHOP = 2;
    //Capacidad máxima del tren
    private static final int CAP_TREN = 5;
    //Cantidad de pasajeros que ingresan al aeropuerto
    private static final int CANT_PASAJEROS = 10;
    //Random

    public static void main(String[] args) {
        PuestoInforme puestoI = new PuestoInforme();
        Aeropuerto aeropuerto = new Aeropuerto(puestoI, CANT_AEROLINEAS, CAP_PUESTOS_ATENCION, CAP_FREESHOP, CAP_TREN);
        Random random = new Random();

        //RELOJ
        Reloj r = new Reloj(aeropuerto);
        Thread reloj = new Thread(r, "RELOJ");
        reloj.start();

        //TERMINALES
        Terminal[] terminales = new Terminal[CANT_TERMINALES];
        for (int i = 1; i <= CANT_TERMINALES; i++) {
            //Nombre de terminal
            char nombre = random.getChar(i-1);
            //Puestos de la terminal
            int puestoMin = ((i - 1) * CANT_PUESTOS_EMBARQUE) + 1;
            int puestoMax = i * CANT_PUESTOS_EMBARQUE;
            int[] puestos = new int[CANT_PUESTOS_EMBARQUE];
            int p = 0;
            while (puestoMin <= puestoMax) {
                puestos[p] = puestoMin;
                p++;
                puestoMin++;
            }
            // creo la terminal y la agrego al arreglo
            terminales[i-1] = new Terminal(nombre, puestos);
        }

        //RECEPCIONISTAS DEL PUESTO DE INFORME
        for (int i = 0; i < CANT_RECEPCIONISTASINFORME; i++) {
            RecepcionistaInforme ri = new RecepcionistaInforme(puestoI);
            Thread recepcionista = new Thread(ri, "RecepcionistaInformes" + i);
            recepcionista.start();
        }

        //AEROLINEAS, PUESTOS DE ATENCION, GUARDIAS Y RECEPCIONISTAS DE CADA PUESTO DE ATENCION
        Aerolinea[] aerolineas = new Aerolinea[CANT_AEROLINEAS];
        for (int i = 0; i < CANT_AEROLINEAS; i++) {
            //nombre
            String nombre = random.generarNombreAerolinea();
            // puesto de atencion
            PuestoAtencion puestoA = new PuestoAtencion(i, random.generarIntRango(MIN_CAP_PUESTOS_ATENCION, CAP_PUESTOS_ATENCION));
            // creo la aerolinea y la agrego al arreglo
            aerolineas[i] = new Aerolinea(nombre, puestoA);

            //GUARDIAS DEL PUESTO DE ATENCION
            Guardia g = new Guardia(puestoA);
            Thread guardia = new Thread(g, "Guardia" + i);
            guardia.start();

            //RECEPCIONISTA DEL PUESTO DE ATENCION
            RecepcionistaAtencion ra = new RecepcionistaAtencion(puestoA);
            Thread recepcionista = new Thread(ra, "RecepcionistaAtencion" + i);
            recepcionista.start();
        }

        //PASAJEROS CON PASAJE
        for (int i = 0; i < CANT_PASAJEROS; i++) {
            //PASAJE
            Aerolinea aerolinea = aerolineas[random.generarInt(CANT_AEROLINEAS - 1)];
            int hora = random.generarHora();
            Terminal terminal = terminales[random.generarInt(CANT_TERMINALES - 1)];
            int[] puestos = terminal.getPuestos();
            int puesto = puestos[random.generarInt(CANT_PUESTOS_EMBARQUE - 1)];
            Pasaje pasaje = new Pasaje(aerolinea, hora, terminal, puesto);
            //PASAJERO
            Pasajero p = new Pasajero("Pasajero" + i, aeropuerto, pasaje);
            Thread pasajero = new Thread(p, "Pasajero" + i);
            pasajero.start();
        }

    }

}
