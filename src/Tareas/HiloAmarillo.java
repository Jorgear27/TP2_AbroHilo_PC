package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloAmarillo extends Thread {

    private int ventasP6;

    private Monitor monitor;

    private final int[] transiciones = {2};

    private final int[] demoras = {0};

    public HiloAmarillo (Monitor monitor, RdP red) {
        this.monitor = monitor;
        this.setName("Hilo Amarillo");
        this.ventasP6 = 0;
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < transiciones.length; i++) {
                if (monitor.fireTransition(transiciones[i])) {

                    int[] vector_disparo = new int[12];
                    vector_disparo[transiciones[i]] = 1;

                    try {
                        sleep(demoras[i]); // demora de la transicion
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            ventasP6++;
        }
    }

    public int getVentasP6() {
        return ventasP6;
    }

}