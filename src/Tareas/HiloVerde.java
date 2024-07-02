package Tareas;

import GestorMonitor.Monitor;

public class HiloVerde extends Thread {

    private Monitor monitor;

    private final int transicion = 4;

    public HiloVerde (Monitor monitor) {
        this.monitor = monitor;
        this.setName("Hilo Verde");
    }

    @Override
    public void run() {

        while (true) {

            monitor.fireTransition(transicion);
        }
    }
}