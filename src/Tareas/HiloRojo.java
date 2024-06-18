package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloRojo extends Thread {

    private int canceladas;

    private Monitor monitor;

    private final int[] transiciones = {7, 8};

    private final int[] demoras = {0, 100};

    public HiloRojo (Monitor monitor, RdP red) {
        this.monitor = monitor;
        this.setName("Hilo Rojo");
        this.canceladas = 0;
    }

    public int getCanceladas() {
        return canceladas;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < transiciones.length; i++) {

                if (monitor.fireTransition(transiciones[i])) {

                    int[] vector_disparo = new int[12];
                    vector_disparo[transiciones[i]] = 1;

                    //System.out.println(Thread.currentThread().getName()+": T" + transiciones[i] + " disparada");
                    //red.actualizarRdP(vector_disparo);

                    try {
                        sleep(demoras[i]); // demora de la transicion
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            canceladas++;
        }
    }
}