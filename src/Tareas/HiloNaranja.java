package Tareas;

import GestorMonitor.Monitor;

public class HiloNaranja extends Thread {

    private int confirmadas;

    private Monitor monitor;

    private final int[] transiciones = {6, 9, 10};

    public HiloNaranja (Monitor monitor) {
        this.monitor = monitor;
        this.setName("Hilo Naranja");
        this.confirmadas = 0;
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
            confirmadas++;
        }
    }

    public int getConfirmadas() {
        return confirmadas;
    }
}