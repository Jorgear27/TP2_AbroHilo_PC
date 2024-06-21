package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloAmarillo extends Thread {

    private int ventasP6;

    private Monitor monitor;

    private final int transicion = 2;

    private final int demora = 0;

    public HiloAmarillo (Monitor monitor, RdP red) {
        this.monitor = monitor;
        this.setName("Hilo Amarillo");
        this.ventasP6 = 0;
    }

    @Override
    public void run() {
        while (true) {
            if (monitor.fireTransition(transicion)) {

                int[] vector_disparo = new int[12];
                vector_disparo[transicion] = 1;

                try {
                    sleep(demora); // demora de la transicion
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            ventasP6++;
        }
    }

    public int getVentasP6() {
        return ventasP6;
    }

}