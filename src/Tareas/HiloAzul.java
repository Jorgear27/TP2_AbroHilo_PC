package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloAzul extends Thread {

    private Monitor monitor;

    private final int transicion = 5;

    private final int demora = 100;

    public HiloAzul (Monitor monitor, RdP red) {
        this.monitor = monitor;
        this.setName("Hilo Azul");
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
        }
    }
}