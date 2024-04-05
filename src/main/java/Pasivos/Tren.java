package Pasivos;

import java.util.concurrent.Semaphore;

import Otros.Random;
import Thread.Pasajero;

public class Tren {
   private int capTren;
   private int cantTerminales;
   private int cantidadPasajerosEnTren = 0;
   private int[] cantidadPasajerosBajanTerminal;
   private Semaphore llevarPasajeros = new Semaphore(0);
   private Semaphore mutex = new Semaphore(1);
   private Semaphore subirseTren;
   private Semaphore[] bajarseTerminal;
   private Semaphore[] pasarTerminal;

   public Tren(int capTren, int cantTerminales) {
      this.capTren = capTren;
      this.subirseTren = new Semaphore(capTren);
      this.cantTerminales = cantTerminales;
      this.cantidadPasajerosBajanTerminal = new int[cantTerminales];
      this.bajarseTerminal = new Semaphore[cantTerminales];
      this.pasarTerminal = new Semaphore[cantTerminales];

      for (int i = 0; i < cantTerminales; i++) {
         this.bajarseTerminal[i] = new Semaphore(0);
         this.pasarTerminal[i] = new Semaphore(0);
         this.cantidadPasajerosBajanTerminal[i] = 0;
      }
   }

   public void viajarEnTren(Pasajero pasajero) throws Exception {
      subirseTren.acquire();
      int numTerminal = pasajero.getPasaje().getTerminal().getNumeroTerminal();
      mutex.acquire();
      System.out.println(Thread.currentThread().getName() + " se sube al tren.");
      Thread.sleep(1000);
      cantidadPasajerosBajanTerminal[numTerminal]++;
      cantidadPasajerosEnTren++;
      if (cantidadPasajerosEnTren == capTren) {
         llevarPasajeros.release();
      }
      mutex.release();
      bajarseTerminal[numTerminal].acquire();
      mutex.acquire();
      System.out.println(Thread.currentThread().getName() + " se baja del tren.");
      Thread.sleep(1000);
      cantidadPasajerosEnTren--;
      cantidadPasajerosBajanTerminal[numTerminal]--;
      if (cantidadPasajerosBajanTerminal[numTerminal] == 0) {
         pasarTerminal[numTerminal].release();
      }
      mutex.release();
   }

   public void llevarPasajeros() throws Exception {
      Random letra = new Random(); // en realidad no es un random, quiero la letra de la terminal segun el indice
      llevarPasajeros.acquire();
      System.out
            .println("\n \u001B[36m" + Thread.currentThread().getName() + " comienza el viaje en tren. " + "\u001B[0m \n");
      for (int i = 0; i < cantTerminales; i++) {
         char letraTerminal = letra.getChar(i);
         System.out.println("\u001B[36m" + "Tren se dirige a la terminal: " + letraTerminal + "\u001B[0m");
         Thread.sleep(10000);
         synchronized (this) {
            if (cantidadPasajerosBajanTerminal[i] > 0) {
               System.out.println("\u001B[36m" + "Bajan " + cantidadPasajerosBajanTerminal[i]
                     + " pasajeros, en la terminal: " + letraTerminal + "\u001B[0m");
               bajarseTerminal[i].release(cantidadPasajerosBajanTerminal[i]);
               pasarTerminal[i].acquire();
            }
         }

      }
      System.out.println(" \n \u001B[36m" + "Tren termina el recorrido y vuelve. \u001B[0m \n");
      Thread.sleep(10000 * cantTerminales);
      System.out.println("\u001B[36m" + "Tren permite a los pasajeros subirse. \u001B[0m");
      subirseTren.release(capTren);
   }

}
