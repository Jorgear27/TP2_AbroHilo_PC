package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloAzul extends Thread {

    private Monitor monitor;

    private final int transicion = 5;

    private final int demora = 100;

    public HiloAzul (Monitor monitor) {
        this.monitor = monitor;
        this.setName("Hilo Azul");
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