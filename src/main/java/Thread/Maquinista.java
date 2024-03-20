package Thread;

import java.util.logging.Level;
import java.util.logging.Logger;

import Pasivos.Tren;

public class Maquinista implements Runnable {
    private Tren tren;

    public Maquinista(Tren tren) {
        this.tren = tren;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.tren.llevarPasajeros();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
