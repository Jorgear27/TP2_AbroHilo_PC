package Tareas;

import GestorMonitor.Monitor;

public class HiloRojo extends Thread {

    private int canceladas;

    private Monitor monitor;

    private final int[] transiciones = {7, 8};

    public HiloRojo (Monitor monitor) {
        this.monitor = monitor;
        this.setName("Hilo Rojo");
        this.canceladas = 0;
    }

    @Override
    public void run() {

        while (true) {
            int i = 0;
            while (i < transiciones.length) {
                if (monitor.fireTransition(transiciones[i])) {
                    i++;
                }
            }
            canceladas++;
        }
    }

    public int getCanceladas() {
        return canceladas;
    }
}