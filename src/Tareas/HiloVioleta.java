package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloVioleta extends Thread {

    private int ventasP7;

    private Monitor monitor;

    private final int transicion = 3;

    private final int demora = 0;

    public HiloVioleta (Monitor monitor, RdP red) {
        this.monitor = monitor;
        this.setName("Hilo Violeta");
        this.ventasP7 = 0;
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

            ventasP7++;
        }
    }

    public int getVentasP7() {
        return ventasP7;
    }
}