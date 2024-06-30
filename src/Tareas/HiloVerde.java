package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloVerde extends Thread {

    private Monitor monitor;

    private final int transicion = 4;

    private final int demora = 100;

    public HiloVerde (Monitor monitor) {
        this.monitor = monitor;
        this.setName("Hilo Verde");
    }

    @Override
    public void run() {

        while (true) {

            if (monitor.fireTransition(transicion)) {

                try {
                    sleep(demora);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}