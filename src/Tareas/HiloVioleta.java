package Tareas;

import GestorMonitor.Monitor;

public class HiloVioleta extends Thread {

    private int ventasP7;

    private Monitor monitor;

    private final int transicion = 3;

    public HiloVioleta (Monitor monitor) {
        this.monitor = monitor;
        this.setName("Hilo Violeta");
        this.ventasP7 = 0;
    }

    @Override
    public void run() {

        while (true) {

            if (monitor.fireTransition(transicion)) {
                ventasP7++;
            }

        }
    }

    public int getVentasP7() {
        return ventasP7;
    }
}