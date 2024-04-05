package Thread;

import Pasivos.Caja;

public class Cajera implements Runnable {

    private Caja cajaAsignada;

    public Cajera(Caja cajaAsignada) {
        this.cajaAsignada = cajaAsignada;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.cajaAsignada.atender();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public Caja getCajaAsignada() {
        return cajaAsignada;
    }

}
