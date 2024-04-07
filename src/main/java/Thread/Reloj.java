package Thread;

import Pasivos.Aeropuerto;

/**
 *
 * @author Selena
 */
public class Reloj implements Runnable {

    private Aeropuerto aeropuerto;
    private int pasoTiempo = 100; //100 = 1 hora
    private static final int frecuencia = 10; // Cada 10 segundos pasa una hora 

    public Reloj(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    public void run() {
        while (true) {
            try {
                aeropuerto.actualizarHorario(pasoTiempo);
                Thread.sleep(frecuencia * 1000);
            } catch (Exception e) {
            }
        }
    }
}
