
package Controlador;

import Otros.Aerolinea;
import Otros.Pasaje;
import Otros.Random;
import Pasivos.Aeropuerto;
import Pasivos.Caja;
import Pasivos.FreeShop;
import Thread.Pasajero;
import Thread.Reloj;
import Pasivos.PuestoAtencion;
import Pasivos.PuestoInforme;
import Pasivos.Terminal;
import Pasivos.Tren;
import Thread.Cajera;
import Thread.Guardia;
import Thread.Maquinista;
import Thread.RecepcionistaAtencion;
import Thread.RecepcionistaInforme;

/**
 *
 * @author Selena
 */
public class Controlador {

    // Cantidad de aerolineas
    private static final int CANT_AEROLINEAS = 20; // En el enunciado sólo dice n
    // Cantidad de terminales (NOTA: Son letras, por lo tanto, el máximo es 26)
    private static final int CANT_TERMINALES = 3; // En el enunciado son 3
    // Cantidad de puestos de embarque por terminal
    private static final int CANT_PUESTOS_EMBARQUE = 7;
    // En el tp no dice capacidad de cada puesto de atencion así que se hace con un
    // random entre el maximo y minimo
    // Capacidad máxima de los puestos de atención
    private static final int CAP_PUESTOS_ATENCION = 15;
    // Capacidad mínima de los puestos de atención
    private static final int MIN_CAP_PUESTOS_ATENCION = 10;
    // Cantidad de recepcionistas en el puesto de informes
    private static final int CANT_RECEPCIONISTASINFORME = 3;
    // Capacidad máxima del freeshop
    private static final int MAX_CAP_FREESHOP = 6;
    // Capacidad minima del freeshop
    private static final int MIN_CAP_FREESHOP = 3;
    // Cantidad de cajas del freeshop
    private static final int CANT_CAJAS = 2;
    // Capacidad máxima del tren
    private static final int CAP_TREN = 5;
    // Cantidad de pasajeros que ingresan al aeropuerto
    private static final int CANT_PASAJEROS = 10;
    // Random
    private static final Random random = new Random();

    public static void main(String[] args) {
        PuestoInforme puestoI = new PuestoInforme();
        // TREN
        Tren tren = new Tren(CAP_TREN, CANT_TERMINALES);
        // MAQUINISTA (conductor del tren)
        Maquinista m = new Maquinista(tren);
        Thread maquinista = new Thread(m, "Maquinista");
        maquinista.start();
        // AEROPUERTO..
        Aeropuerto aeropuerto = new Aeropuerto(puestoI, tren);

        // RELOJ
        Reloj r = new Reloj(aeropuerto);
        Thread reloj = new Thread(r, "RELOJ");
        reloj.start();

        // TERMINALES
        Terminal[] terminales = new Terminal[CANT_TERMINALES];
        for (int i = 1; i <= CANT_TERMINALES; i++) {
            // Nombre de terminal
            char nombre = random.getChar(i - 1);
            // Puestos de la terminal
            int puestoMin = ((i - 1) * CANT_PUESTOS_EMBARQUE) + 1;
            int puestoMax = i * CANT_PUESTOS_EMBARQUE;
            int[] puestos = new int[CANT_PUESTOS_EMBARQUE];
            int p = 0;
            while (puestoMin <= puestoMax) {
                puestos[p] = puestoMin;
                p++;
                puestoMin++;
            }
            // cajas
            Caja[] cajas = new Caja[CANT_CAJAS];
            for (int j = 1; j <= CANT_CAJAS; j++) {
                Caja caja = new Caja(j);
                cajas[j - 1] = caja;
                Cajera c = new Cajera(caja);
                Thread cajera = new Thread(c, "Cajera " + i + nombre);
                cajera.start();
            }
            // freeshop
            FreeShop freeshop = new FreeShop(random.generarIntRango(MIN_CAP_FREESHOP, MAX_CAP_FREESHOP), cajas, nombre);
            // creo la terminal y la agrego al arreglo
            terminales[i - 1] = new Terminal(nombre, i - 1, puestos, freeshop);
        }

        // RECEPCIONISTAS DEL PUESTO DE INFORME
        for (int i = 0; i < CANT_RECEPCIONISTASINFORME; i++) {
            RecepcionistaInforme ri = new RecepcionistaInforme(puestoI);
            Thread recepcionista = new Thread(ri, "RecepcionistaInformes " + i);
            recepcionista.start();
        }

        // AEROLINEAS, PUESTOS DE ATENCION, GUARDIAS Y RECEPCIONISTAS DE CADA PUESTO DE
        // ATENCION
        Aerolinea[] aerolineas = new Aerolinea[CANT_AEROLINEAS];
        for (int i = 0; i < CANT_AEROLINEAS; i++) {
            // nombre
            String nombre = random.generarNombreAerolinea();
            // puesto de atencion
            PuestoAtencion puestoA = new PuestoAtencion(i,
                    random.generarIntRango(MIN_CAP_PUESTOS_ATENCION, CAP_PUESTOS_ATENCION));
            // creo la aerolinea y la agrego al arreglo
            aerolineas[i] = new Aerolinea(nombre, puestoA);

            // GUARDIAS DEL PUESTO DE ATENCION
            Guardia g = new Guardia(puestoA);
            Thread guardia = new Thread(g, "Guardia " + i);
            guardia.start();

            // RECEPCIONISTA DEL PUESTO DE ATENCION
            RecepcionistaAtencion ra = new RecepcionistaAtencion(puestoA);
            Thread recepcionista = new Thread(ra, "RecepcionistaAtencion " + i);
            recepcionista.start();
        }

        // PASAJEROS CON PASAJE
        for (int i = 0; i < CANT_PASAJEROS; i++) {
            // PASAJE
            Aerolinea aerolinea = aerolineas[random.generarInt(CANT_AEROLINEAS - 1)];
            int hora = random.generarHora();
            Terminal terminal = terminales[random.generarInt(CANT_TERMINALES - 1)];
            int[] puestos = terminal.getPuestos();
            int puesto = puestos[random.generarInt(CANT_PUESTOS_EMBARQUE - 1)];
            Pasaje pasaje = new Pasaje(aerolinea, hora, terminal, puesto);
            // PASAJERO
            Pasajero p = new Pasajero("Pasajero " + i, aeropuerto, pasaje);
            Thread pasajero = new Thread(p, "Pasajero " + i);
            pasajero.start();
        }

    }

}
