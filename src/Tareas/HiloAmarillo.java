package Tareas;

import GestorMonitor.Monitor;

public class HiloAmarillo extends Thread {

    private int ventasP6;

    private Monitor monitor;

    private final int transicion = 2;


    public HiloAmarillo (Monitor monitor) {
        this.monitor = monitor;
        this.setName("Hilo Amarillo");
        this.ventasP6 = 0;
    }

    @Override
    public void run() {
        while (true) {
            if (monitor.fireTransition(transicion)) {
                ventasP6++;
            }
        }
    }

    public int getVentasP6() {
        return ventasP6;
    }

}