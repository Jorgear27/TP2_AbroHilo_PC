package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloVerde extends Thread {

    private int ventasP7;

    private Monitor monitor;

    private RdP red;

    private final int[] transiciones = {4};

    private final int[] demoras = {1000};

    public HiloVerde (Monitor monitor, RdP red) {
        ventasP7 = 0;
        this.monitor = monitor;
        this.red = red;
        this.setName("Hilo Verde");
    }

    public int getVentasP7() {
        return ventasP7;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < transiciones.length; i++) {
                if (monitor.fireTransition(transiciones[i])) {
                    int[] vector_disparo = new int[12];
                    vector_disparo[transiciones[i]] = 1;

                    System.out.println(Thread.currentThread().getName()+": T" + transiciones[i] + " disparada");
                    //red.actualizarRdP(vector_disparo);

                    try {
                        sleep(demoras[i]); // demora de la transicion
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            ventasP7++;
        }
    }
}