package Tareas;

import GestorMonitor.Monitor;

public class HiloAzul extends Thread {

    private Monitor monitor;

    private final int transicion = 5;

    public HiloAzul (Monitor monitor) {
        this.monitor = monitor;
        this.setName("Hilo Azul");
    }

    @Override
    public void run() {
        while (true) {
            monitor.fireTransition(transicion);
        }
    }
}