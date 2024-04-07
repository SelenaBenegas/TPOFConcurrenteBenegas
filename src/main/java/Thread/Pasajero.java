
package Thread;

import Otros.Pasaje;
import Otros.Random;
import Otros.Terminal;
import Pasivos.Aeropuerto;
import Pasivos.Caja;
import Pasivos.PuestoAtencion;
import Pasivos.PuestoInforme;
import Pasivos.Tren;
import Pasivos.FreeShop;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Selena
 */
public class Pasajero implements Runnable {

    private Aeropuerto aeropuerto;
    private Pasaje pasaje;
    private String nombre;

    public Pasajero(String nombre, Aeropuerto aeropuerto, Pasaje pasaje) {
        this.nombre = nombre;
        this.aeropuerto = aeropuerto;
        this.pasaje = pasaje;
    }

    public void run() {
        try {
            // INGRESA AL AEROPUERTO SÓLO EN EL HORARIO DE ATENCIÓN
            this.aeropuerto.ingresarAeropuerto();
            // CUANDO INGRESA AL AEROPUERTO SE DIRIGE AL PUESTO DE INFORMES
            PuestoInforme puestoInformes = aeropuerto.getPuestoInforme();
            puestoInformes.consultar(this);
            // EN EL PUESTO DE INFORMES LE DAN EL PUESTO DE ATENCION
            PuestoAtencion puestoA = pasaje.getPuestoAtencion();
            // INTENTA HACER FILA EN EL PUESTO DE ATENCION O SE QUEDA EN EL HALL CENTRAL
            puestoA.hacerFila(this);
            // UNA VEZ QUE REALIZA EL CHECK-IN, SE DIRIGE AL TREN
            Tren tren = aeropuerto.getTren();
            tren.viajarEnTren(this);
            // VERIFICA SI PUEDE INGRESAR AL FREESHOP O NO
            Random random = new Random();
            int horaActual = aeropuerto.getHoraActual();
            int horaVuelo = this.pasaje.getHoraVuelo();
            int horaCalculada = aeropuerto.calcularHora(horaActual, 3);
            if (horaCalculada <= horaVuelo ) {  // && random.generarBoolean()
                Terminal terminal = pasaje.getTerminal();
                FreeShop freeshop = terminal.getFreeShop();
                freeshop.entrarFreeShop(this);
                // Simula tiempo que mira la tienda
                System.out.println(this.nombre + " recorre el freshop de la terminal: " + terminal.getLetra());
                Thread.sleep(random.generarIntRango(5000, 40000));
                horaActual = aeropuerto.getHoraActual();
                // VERIFICA SI TIENE TIEMPO PARA COMPRAR
                horaCalculada = aeropuerto.calcularHora(horaActual, 1); //hora actual + 1 hora
                if (horaCalculada <= horaVuelo ) { // && random.generarBoolean()
                    System.out.println(this.nombre + " decide comprar en el freeshop.");
                    Caja caja = freeshop.obtenerCaja();
                    caja.entrarCaja(this);
                    caja.salirCaja(this);
                    Thread.sleep(random.generarIntRango(1000, 5000));
                }
                freeshop.salirFreeShop(this);
            } else {
                System.out.println(
                        this.nombre + " NO va a comprar en el freshop de la terminal: "
                                + pasaje.getTerminal().getLetra());
            }
            // Se va a su vuelo
            this.aeropuerto.tomarVuelo(horaVuelo);

        } catch (Exception ex) {
            Logger.getLogger(Pasajero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Pasaje getPasaje() {
        return this.pasaje;
    }

    public String getNombre() {
        return this.nombre;
    }

}
